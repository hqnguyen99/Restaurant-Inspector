package hqnguyen.sfu.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import hqnguyen.sfu.UIClasses.RestaurantAdapter;
import model.AppData;
import model.DataSingleton;

/**
 *  Show data of restaurants by recycler view
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class RestaurantActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createRestaurantList();
        buildRecyclerView();
    }

    private void createRestaurantList()
    {
        DataSingleton data = AppData.INSTANCE;
        InputStream restaurantIs = getResources().openRawResource(R.raw.restaurants_itr1);
        InputStream inspectionIs = getResources().openRawResource(R.raw.inspectionreports_itr1);
        data.init(
            new BufferedReader(new InputStreamReader(restaurantIs, StandardCharsets.UTF_8)),
            new BufferedReader(new InputStreamReader(inspectionIs, StandardCharsets.UTF_8))
        );
    }

    private void buildRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.restaurant_recyclerView);
        RestaurantAdapter adapter = new RestaurantAdapter();
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            Intent intent =
                InspectionActivity.makeLaunchIntent(RestaurantActivity.this, position);
            startActivity(intent);
            }
        });
    }
}
