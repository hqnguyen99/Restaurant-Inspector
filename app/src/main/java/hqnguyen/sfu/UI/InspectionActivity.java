package hqnguyen.sfu.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class InspectionActivity extends AppCompatActivity {
    private static final String RESTAURANT_POSITION = "restaurant position";

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        position = intent.getIntExtra(RESTAURANT_POSITION,0);
    }

    public static Intent makeLaunchIntent(Context c, int position){
        Intent intent = new Intent(c, InspectionActivity.class);
        intent.putExtra(RESTAURANT_POSITION, position);
        return intent;
    }
}
