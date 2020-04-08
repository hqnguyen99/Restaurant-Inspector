package cmpt276.restaurant_inspector.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import cmpt276.restaurant_inspector.Filter.FilterData;
import cmpt276.restaurant_inspector.Filter.FilterFragment;
import cmpt276.restaurant_inspector.UIClasses.RestaurantAdapter;
import cmpt276.restaurant_inspector.model.AppData;
import cmpt276.restaurant_inspector.model.DataSingleton;
import cmpt276.restaurant_inspector.model.HazardRating;
import cmpt276.restaurant_inspector.model.Restaurant;

/**
 *  Show data of restaurants by recycler view
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class RestaurantActivity extends AppCompatActivity
        implements FilterFragment.FilterDialogListener
{
    //input for search and filter box
     private FilterData filterData = FilterData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildRecyclerView();
        setupSearchBox();
    }

    private void buildRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.restaurant_recyclerView);
        RestaurantAdapter adapter = new RestaurantAdapter();
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
        Intent intent =
            InspectionActivity.makeLaunchIntent(RestaurantActivity.this,filterData.getRestaurantPositionInFilterBox().get(position));
        startActivity(intent);
        });
    }

    private void setupSearchBox() {
        // Filter box
        ImageButton filterBtn = (ImageButton) findViewById(R.id.restaurant_filter_btn);
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
        SearchView searchBox = (SearchView) findViewById(R.id.restaurant_search_box);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterData.setSearchRestaurantByName(s);
                filterData.clearRestaurantPosition();
                setupRestaurantFilter();
                buildRecyclerView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (searchBox.getQuery().length() == 0) {
                    filterData.setSearchRestaurantByName(s);
                    filterData.clearRestaurantPosition();
                    setupRestaurantFilter();
                    buildRecyclerView();
                }
                return false;
            }
        });



    }

    private void setupRestaurantFilter() {
        filterData.clearRestaurantPosition();
        DataSingleton data = AppData.INSTANCE;
        //Log.i("haha", String.valueOf(data.size()));
        for (int position = 0; position < data.size(); position++) {
            Restaurant restaurant = data.getEntryAtIndex(position).getRestaurant();
            String restaurantName = restaurant.getName().toLowerCase();
            int numberOfViolations = data.getEntryAtIndex(position).getNumViolations();
            String hazardLevel = null;
            if(data.getEntryAtIndex(position).getInspections().size() > 0) {
                switch (data.getEntryAtIndex(position).getInspections().get(0).getHazardRating()) {
                    case LOW:
                        hazardLevel = "LOW";
                        break;
                    case MODERATE:
                        hazardLevel = "MODERATE";
                        break;
                    case HIGH:
                        hazardLevel = "HIGH";
                        break;
                    case NONE:
                        hazardLevel = "NONE";
                        break;

                }
            }
            else if (data.getEntryAtIndex(position).getInspections().size() == 0) {
                Log.i("kkkkk", String.valueOf(data.getEntryAtIndex(position).getInspections().size()));
                hazardLevel = "NONE";
            }

            if(restaurantName.contains(filterData.getSearchRestaurantByName())
                    && (filterData.getHazardLevel().equals("Select one") || filterData.getHazardLevel().equals(hazardLevel) )
                    &&  numberOfViolations > filterData.getNumberOfViolationsMoreThan()){
                filterData.addRestaurantPosition(position);
                //Log.i("huhu", String.valueOf(data.getEntryAtIndex(position).getInspections().size()));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_restaurant_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.switch_to_map_activity) {
            Intent intent = new Intent(RestaurantActivity.this, MapsActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public void getInput(Boolean isFavorite, String hazardLevel, int numberOfViolations) {
        filterData.setFavorite(isFavorite);
        filterData.setHazardLevel(hazardLevel);
        filterData.setNumberOfViolationsMoreThan(numberOfViolations);
        Log.i("nooo", hazardLevel);
        setupRestaurantFilter();
        buildRecyclerView();

    }
}
