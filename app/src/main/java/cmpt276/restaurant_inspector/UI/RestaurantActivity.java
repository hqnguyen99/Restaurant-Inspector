package cmpt276.restaurant_inspector.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cmpt276.restaurant_inspector.UIClasses.RestaurantAdapter;

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
        buildRecyclerView();
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
}
