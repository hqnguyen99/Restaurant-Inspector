package cmpt276.restaurant_inspector.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cmpt276.restaurant_inspector.Filter.FilterData;
import cmpt276.restaurant_inspector.Filter.FilterFragment;
import cmpt276.restaurant_inspector.MapsClass.CustomClusterRenderer;
import cmpt276.restaurant_inspector.MapsClass.InfoFragment;
import cmpt276.restaurant_inspector.MapsClass.MyItem;
import cmpt276.restaurant_inspector.model.AppData;
import cmpt276.restaurant_inspector.model.DataSingleton;
import cmpt276.restaurant_inspector.model.HazardRating;
import cmpt276.restaurant_inspector.model.Inspection;
import cmpt276.restaurant_inspector.model.RestaurantInspectionsPair;

/**
 * An activity that displays a map showing the available restaurants with inspection reports.
 */
public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, FilterFragment.FilterDialogListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final String RESTAURANT_POSITION = "123" ;
    private int restaurantPositionInList;
    private GoogleMap map;
    private CameraPosition cameraPosition;
    private ClusterManager<MyItem> clusterManager;
    private CustomClusterRenderer customClusterRenderer;
    private List<MyItem> myItemList = new ArrayList<>();

    //input for search and filter box
    FilterData filterData = FilterData.getInstance();

    // The entry point to the Places API.
    private PlacesClient placesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int RESTAURANT_ZOOM = 19;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Set up search box and filter
        setupSearchBox();
    }

    private void setupSearchBox() {
        // Filter box
        ImageButton filterBtn = (ImageButton) findViewById(R.id.maps_filter_btn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilterDialog();
            }

            private void openFilterDialog() {
                FilterFragment filterFragment = new FilterFragment();
                filterFragment.show(getSupportFragmentManager(),"example dialog");
            }
        });

        // Search box
        SearchView searchBox = (SearchView) findViewById(R.id.maps_search_box);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterData.setSearchRestaurantByName(s);
                filterData.clearRestaurantPosition();
                setupRestaurantCluster();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (searchBox.getQuery().length() == 0) {
                    filterData.setSearchRestaurantByName(s);
                    filterData.clearRestaurantPosition();
                    setupRestaurantCluster();
                }
                return false;
            }
        });

    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Sets up the options menu.
     * @param menu The options menu.
     * @return Boolean.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    /**
     * Handles a click on the menu option to get a place.
     * @param item The menu item to handle.
     * @return Boolean.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.switch_to_restaurant_list) {
            Intent intent = new Intent(MapsActivity.this, RestaurantActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;
        extractDataFromIntent();

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.

        if( restaurantPositionInList == -1 ) {
            getDeviceLocation();
        }

        setupRestaurantCluster();
    }


    public void setupRestaurantCluster(){
        if(map != null){
            if (clusterManager == null){
                clusterManager = new ClusterManager<MyItem>(this, map);
            }

            if(customClusterRenderer == null){
                customClusterRenderer = new CustomClusterRenderer(getApplicationContext(), map, clusterManager);
            }


            customClusterRenderer.setMinClusterSize(3);
            clusterManager.setRenderer(customClusterRenderer);

            clusterManager.setOnClusterItemInfoWindowClickListener(
                    item -> {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        InfoFragment dialog = new InfoFragment(item);
                        dialog.show(fragmentManager, "InfoDialog");
                    });

            map.setOnMarkerClickListener(clusterManager);
        }

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);
        // Add cluster items (markers) to the cluster manager.
        addItems();
        getAndPointCameraToChosenRestaurant();
    }

    private void getAndPointCameraToChosenRestaurant() {
        if( restaurantPositionInList != -1){
            for (MyItem item : myItemList){
                if(item.getPositionInRestaurantList() == restaurantPositionInList){
                    Toast.makeText(MapsActivity.this, item.getTitle(),Toast.LENGTH_LONG).show();
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(item.getPosition(),RESTAURANT_ZOOM));
                }
            }
        }
    }

    private void addItems() {
        clusterManager.clearItems();

        Inspection inspection = null;
        DataSingleton data = AppData.INSTANCE;
        String searchRestaurantByName = filterData.getSearchRestaurantByName();
        int numberOfViolationsMoreThan = filterData.getNumberOfViolationsMoreThan();
        String hazardLevelFilter = filterData.getHazardLevel();
        for (int position = 0; position < data.size(); position++) {
            RestaurantInspectionsPair current = data.getEntryAtIndex(position);
            double latitude = current.getRestaurant().getLatitude();
            double longitude = current.getRestaurant().getLongitude();
            String name = current.getRestaurant().getName();
            String checkName = name.toLowerCase();
            if (searchRestaurantByName.equals("") || checkName.contains(searchRestaurantByName)) {
                String address = current.getRestaurant().getAddress();
                String hazardLevel = "None";
                String title = name;
                String snippet = address;
                List<Inspection> inspections = current.getInspections();

                BitmapDescriptor icon = null;
                if (inspections.size() > numberOfViolationsMoreThan && !hazardLevelFilter.equals("NONE")) {
                    inspection = inspections.get(0);
                    if (inspection.getHazardRating() == HazardRating.LOW && ( hazardLevelFilter.equals("LOW") || hazardLevelFilter.equals("Select one"))){
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.hazard_low);
                        hazardLevel = "Low";
                    }
                     else if(inspection.getHazardRating() == HazardRating.MODERATE && (hazardLevelFilter.equals("MODERATE") || hazardLevelFilter.equals("Select one"))) {
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.hazard_medium);
                        hazardLevel = "Moderate";
                    }
                    else if(inspection.getHazardRating() == HazardRating.HIGH && (hazardLevelFilter.equals("HIGH") || hazardLevelFilter.equals("Select one"))) {
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.hazard_high);
                        hazardLevel = "High";
                    }
                    else if(inspection.getHazardRating() == HazardRating.NONE && (hazardLevelFilter.equals("NONE") || hazardLevelFilter.equals("Select one"))) {
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.hazard_none);
                    }
                }
                else if(inspections.size() == numberOfViolationsMoreThan && (hazardLevelFilter.equals("NONE") || hazardLevelFilter.equals("Select one"))){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.hazard_none);
                }
                if(icon != null) {
                    MyItem clusterItem = new MyItem(latitude, longitude, title, snippet, icon, position, hazardLevel);
                    clusterManager.addItem(clusterItem);
                    myItemList.add(clusterItem);
                    filterData.addRestaurantPosition(position);
                }
            }
        }
        clusterManager.cluster();
        //

    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (map == null) {
            return;
        }

        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public static Intent makeLaunchIntent(Context c, int restaurantPosition)
    {
        Intent intent = new Intent(c, MapsActivity.class);
        intent.putExtra(RESTAURANT_POSITION, restaurantPosition);

        return intent;
    }

    private void extractDataFromIntent()
    {
        Intent intent = getIntent();
        restaurantPositionInList = intent.getIntExtra(RESTAURANT_POSITION, -1);
    }

    @Override
    public void getInput(Boolean isFavorite, String hazardLevel, int numberOfViolations) {
        filterData.setFavorite(isFavorite);
        filterData.setHazardLevel(hazardLevel);
        filterData.setNumberOfViolationsMoreThan(numberOfViolations);
        filterData.clearRestaurantPosition();
        setupRestaurantCluster();
    }
}
