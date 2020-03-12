package hqnguyen.sfu.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import hqnguyen.sfu.UIClasses.InspectionAdapter;
import model.AppData;
import model.DataSingleton;
import model.Restaurant;


/**
 *  Show data of inspections for a single restaurant by recycler view
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class InspectionActivity extends AppCompatActivity
{
    private static final String RESTAURANT_POSITION = "restaurant position";
    private DataSingleton data;
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
        Restaurant restaurant = data.getEntryAtIndex(restaurantPosition).getRestaurant();
        TextView textViewRestaurantName =
            findViewById(R.id.textView_inspection_activity_restaurant_name);
        TextView textViewRestaurantAddress =
            findViewById(R.id.textView_inspection_activity_restaurant_address);
        TextView textViewRestaurantLatitude =
            findViewById(R.id.textView_inspection_activity_restaurant_GPS_latitude);
        TextView textViewRestaurantLongitude =
            findViewById(R.id.textView_inspection_activity_restaurant_GPS_longitude);

        textViewRestaurantName.setText(restaurant.getName());
        textViewRestaurantAddress.setText(restaurant.getAddress());
        textViewRestaurantLatitude.setText("" + restaurant.getLatitude());
        textViewRestaurantLongitude.setText("" + restaurant.getLongitude());
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
}
