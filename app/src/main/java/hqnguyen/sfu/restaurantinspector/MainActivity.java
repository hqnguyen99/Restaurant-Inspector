package hqnguyen.sfu.restaurantinspector;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import hqnguyen.sfu.UI.R;
import model.AppData;
import model.DataSingleton;
import model.RestaurantInspectionsPair;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {
        InputStream restaurantIs = getResources().openRawResource(R.raw.restaurants_itr1);
        InputStream inspectionIs = getResources().openRawResource(R.raw.inspectionreports_itr1);
        DataSingleton data = AppData.INSTANCE;
        data.init(
                new BufferedReader(new InputStreamReader(restaurantIs, StandardCharsets.UTF_8)),
                new BufferedReader(new InputStreamReader(inspectionIs, StandardCharsets.UTF_8))
        );

        for (Map.Entry<String, RestaurantInspectionsPair> entry : data.entries().entrySet()) {
            Log.d("Entry " + entry.getKey(), entry.getValue().toString() + '\n');
        }
    }
}
