package hqnguyen.sfu.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import hqnguyen.sfu.UIClasses.RestaurantAdapter;
import hqnguyen.sfu.UIClasses.TestRestaurant;
import model.AppData;
import model.DataSingleton;

public class RestaurantActivity extends AppCompatActivity {
    private DataSingleton data;

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

//    private ImageView imageViewRestaurantIcon;
    //private ImageView imageViewHazardLevelIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        createRestaurantList();
        buildRecyclerView();
//        imageViewRestaurantIcon = findViewById(R.id.imageView_restaurant_activity_restaurant_icon);
//        imageViewRestaurantIcon.setImageResource(randomGenerateRestaurantIcon());
//        imageViewHazardLevelIcon = findViewById(R.id.imageView_restaurant_activity_warning_level);
//        imageViewHazardLevelIcon.setImageResource(generateHazardLevelIcon());
    }

    private void createRestaurantList() {
        data = AppData.INSTANCE;
        InputStream restaurantIs = getResources().openRawResource(R.raw.restaurants_itr1);
        InputStream inspectionIs = getResources().openRawResource(R.raw.inspectionreports_itr1);
        data.init(
                new BufferedReader(new InputStreamReader(restaurantIs, StandardCharsets.UTF_8)),
                new BufferedReader(new InputStreamReader(inspectionIs, StandardCharsets.UTF_8))
        );
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.restaurant_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RestaurantAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = InspectionActivity.makeLaunchIntent(RestaurantActivity.this, position);
                startActivity(intent);
            }
        });
    }

//    private int randomGenerateRestaurantIcon() {
//        Random rand = new Random();
//        int n = rand.nextInt(4);
//        String restaurantIcon = "restaurant_" + n;
//        return getResources().getIdentifier(restaurantIcon, "drawable", RestaurantActivity.this.getPackageName());
//    }

    private int generateHazardLevelIcon() {
        Random rand = new Random();
        int n = rand.nextInt(101);
        String hazardLevelIcon;
        if (n < 30) {
            hazardLevelIcon = "hazard_low";
        } else if (n < 70) {
            hazardLevelIcon = "hazard_medium";
        } else {
            hazardLevelIcon = "hazard_high";
        }
        return getResources().getIdentifier(hazardLevelIcon, "drawable", RestaurantActivity.this.getPackageName());
    }

    private void dateIntelFormat(LocalDate localDate){

    }
}
