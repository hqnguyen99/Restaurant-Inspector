package hqnguyen.sfu.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import hqnguyen.sfu.UIClasses.RestaurantAdapter;
import hqnguyen.sfu.UIClasses.TestRestaurant;

public class RestaurantActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<TestRestaurant> testRestaurants = new ArrayList<>();
        addTestRestaurant(testRestaurants);
        recyclerView = findViewById(R.id.restaurant_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RestaurantAdapter(testRestaurants);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void addTestRestaurant(ArrayList<TestRestaurant> testRestaurant) {
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 12, "24 days", "Noodle"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 24, "May 12", "Rice"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 36, "May 2018", "Taco"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 12, "14 days", "Noodle"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 24, "Feb 12", "Rice"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 36, "Jan 2018", "Taco"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 12, "9 days", "Noodle"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 24, "Mar 12", "Rice"));
            testRestaurant.add(new TestRestaurant(randomGenerateRestaurantIcon(), randomGenerateHazardLevelIcon(), 36, "Oct 2018", "Taco"));
        }

    private int randomGenerateRestaurantIcon() {
        Random rand = new Random();
        int n = rand.nextInt(4);
        String restaurantIcon = "restaurant_" + n;
        return getResources().getIdentifier(restaurantIcon, "drawable", RestaurantActivity.this.getPackageName());
    }

    private int randomGenerateHazardLevelIcon() {
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

}
