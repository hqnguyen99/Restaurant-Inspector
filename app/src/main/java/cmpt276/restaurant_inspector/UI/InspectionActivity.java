package cmpt276.restaurant_inspector.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import cmpt276.restaurant_inspector.UIClasses.InspectionAdapter;
import cmpt276.restaurant_inspector.model.AppData;
import cmpt276.restaurant_inspector.model.DataSingleton;
import cmpt276.restaurant_inspector.model.Restaurant;


/**
 *  Show data of inspections for a single restaurant by recycler view
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class InspectionActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String RESTAURANT_POSITION = "restaurant position";
    private DataSingleton data;
    private Restaurant restaurant;
    private int restaurantPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = AppData.INSTANCE;

        extractDataFromIntent();
        setupRestaurantInfo();
        buildRecyclerView();
    }

    private void setupRestaurantInfo()
    {
        restaurant = data.getEntryAtIndex(restaurantPosition).getRestaurant();
        TextView textViewRestaurantName =
            findViewById(R.id.textView_inspection_activity_restaurant_name);
        TextView textViewRestaurantAddress =
            findViewById(R.id.textView_inspection_activity_restaurant_address);
        TextView textViewRestaurantLatitude =
            findViewById(R.id.textView_inspection_activity_restaurant_GPS_latitude);
        TextView textViewRestaurantLongitude =
            findViewById(R.id.textView_inspection_activity_restaurant_GPS_longitude);

        textViewRestaurantName.setText(restaurant.getName());
        textViewRestaurantAddress.setText(
                String.format("%s, %s", restaurant.getAddress(), restaurant.getCity())
        );
        textViewRestaurantLatitude.setText("" + restaurant.getLatitude());
        textViewRestaurantLongitude.setText("" + restaurant.getLongitude());

        textViewRestaurantLatitude.setOnClickListener(this);
        textViewRestaurantLongitude.setOnClickListener(this);

    }

    private void extractDataFromIntent()
    {
        Intent intent = getIntent();
        restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION, 0);
    }

    private void buildRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.inspection_recyclerView);
        InspectionAdapter adapter = new InspectionAdapter(restaurantPosition);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new InspectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int inspectionPosition) {
            Intent intent = ViolationActivity.makeLaunchIntent(
                InspectionActivity.this, restaurantPosition, inspectionPosition
            );
            startActivity(intent);
            }
        });
    }

    public static Intent makeLaunchIntent(Context c, int restaurantPosition)
    {
        Intent intent = new Intent(c, InspectionActivity.class);
        intent.putExtra(RESTAURANT_POSITION, restaurantPosition);
        return intent;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_inspection_activity_restaurant_GPS_latitude:
            case R.id.textView_inspection_activity_restaurant_GPS_longitude:
                Intent intent = MapsActivity.makeLaunchIntent(InspectionActivity.this,restaurant.getLatitude(), restaurant.getLongitude());
                startActivity(intent);
                finish();
                break;
        }
    }
}
