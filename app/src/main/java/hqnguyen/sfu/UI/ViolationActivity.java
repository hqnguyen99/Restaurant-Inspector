package hqnguyen.sfu.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class ViolationActivity extends AppCompatActivity {
    private static final String INSPECTION_POSITION = "inspection position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public static Intent makeLaunchIntent(Context c, int position){
        Intent intent = new Intent(c, InspectionActivity.class);
        intent.putExtra(INSPECTION_POSITION, position);
        return intent;
    }
}
