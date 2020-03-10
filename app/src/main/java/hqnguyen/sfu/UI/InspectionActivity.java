package hqnguyen.sfu.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import hqnguyen.sfu.UIClasses.InspectionAdapter;
import model.AppData;
import model.DataSingleton;
import model.Restaurant;

public class InspectionActivity extends AppCompatActivity {
    private static final String RESTAURANT_POSITION = "restaurant position";

    private DataSingleton data;
    private RecyclerView recyclerView;
    private InspectionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int restaurantPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = AppData.INSTANCE;

        extractDataFromIntent();
        setupRestaurantInfor();
        buildRecyclerView();

    }

    private void setupRestaurantInfor() {
        Restaurant restaurant = data.getEntryAtIndex(restaurantPosition).getRestaurant();
        TextView textViewRestaurantName = (TextView) findViewById(R.id.textView_inspection_activity_restaurant_name);
        TextView textViewRestaurantAddress = (TextView) findViewById(R.id.textView_inspection_activity_restaurant_address);
        TextView textViewRestaurantCoords = (TextView) findViewById(R.id.textView_inspection_activity_restaurant_GPS_coords);
        textViewRestaurantName.setText(restaurant.getName());
        textViewRestaurantAddress.setText(restaurant.getAddress());
        textViewRestaurantCoords.setText(restaurant.getLatitude() + " " + restaurant.getLongitude());
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION, 0);
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.inspection_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new InspectionAdapter(restaurantPosition);
        //Toast.makeText(getApplicationContext(), adapter.getItemCount(), Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new InspectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int inspectionPosition) {
                Intent intent = ViolationActivity.makeLaunchIntent(InspectionActivity.this, restaurantPosition, inspectionPosition );
                startActivity(intent);
            }
        });
    }

    public static Intent makeLaunchIntent(Context c, int restaurantPosition){
        Intent intent = new Intent(c, InspectionActivity.class);
        intent.putExtra(RESTAURANT_POSITION, restaurantPosition);
        return intent;
    }
}
