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

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hqnguyen.sfu.UIClasses.InspectionAdapter;
import hqnguyen.sfu.UIClasses.ViolationAdapter;
import model.AppData;
import model.DataSingleton;
import model.Inspection;

public class ViolationActivity extends AppCompatActivity {
    private static final String INSPECTION_POSITION = "inspection position";
    private static final String RESTAURANT_POSITION = "restaurant position";

    private DataSingleton data;
    private RecyclerView recyclerView;
    private ViolationAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int restaurantPosition;
    private int inspectionPosition;
    private Inspection inspection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = AppData.INSTANCE;
        extractDataFromIntent();
        setupInspectionDetail();
        buildRecyclerView();

    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION, 0);
        inspectionPosition = intent.getIntExtra(RESTAURANT_POSITION,0);
    }

    private void setupInspectionDetail() {
        TextView textViewInspectionDate = (TextView) findViewById(R.id.textView_violation_activity_full_date);
        TextView textViewInspectionType = (TextView) findViewById(R.id.textView_violation_activity_inspection_type);
        TextView textViewNumberOfCriticalIssues = (TextView) findViewById(R.id.textView_violation_activity_critical_issues_found);
        TextView textViewNumberOfNonCriticalIssues = (TextView) findViewById(R.id.textView_violation_activity_noncritical_issues_found);
        TextView textViewHarzardLevel = (TextView) findViewById(R.id.textView_violation_activity_hazard_level_words);
        ImageView imageViewHazardLevel = (ImageView) findViewById(R.id.imageView_violation_activity_hazard_level_icon);

        inspection = data.getEntryAtIndex(restaurantPosition).getInspections().get(inspectionPosition);

        textViewInspectionDate.setText(String.valueOf(inspection.getDate()));
        textViewInspectionType.setText(inspection.getInspType());
        textViewNumberOfCriticalIssues.setText(String.valueOf(inspection.getNumCrit()));
        textViewNumberOfNonCriticalIssues.setText(String.valueOf(inspection.getNumNonCrit()));
        textViewHarzardLevel.setText(String.valueOf(inspection.getHazardRating()));
        switch (inspection.getHazardRating()){
            case "LOW" :
                imageViewHazardLevel.setImageResource(R.drawable.hazard_low);
            case "MEDIUM" :
                imageViewHazardLevel.setImageResource(R.drawable.hazard_medium);
            case "HIGH" :
                imageViewHazardLevel.setImageResource(R.drawable.hazard_high);
        }
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.violation_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ViolationAdapter(inspection);
        //Toast.makeText(getApplicationContext(), adapter.getItemCount(), Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    public static Intent makeLaunchIntent(Context c, int restaurantPosition, int inspectionPosition){
        Intent intent = new Intent(c, ViolationActivity.class);
        intent.putExtra(RESTAURANT_POSITION, restaurantPosition);
        intent.putExtra(INSPECTION_POSITION, inspectionPosition);
        return intent;
    }
}
