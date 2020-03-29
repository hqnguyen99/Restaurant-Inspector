package cmpt276.restaurant_inspector.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.restaurant_inspector.UIClasses.ViolationAdapter;
import cmpt276.restaurant_inspector.model.AppData;
import cmpt276.restaurant_inspector.model.DataSingleton;
import cmpt276.restaurant_inspector.model.DateTimeUtil;
import cmpt276.restaurant_inspector.model.Inspection;

/**
 *  Show data of violations for a single inspection by recycler view
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class ViolationActivity extends AppCompatActivity
{
    private static final String INSPECTION_POSITION = "inspection position";
    private static final String RESTAURANT_POSITION = "restaurant position";
    private DataSingleton data;
    private int restaurantPosition;
    private int inspectionPosition;
    private Inspection inspection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = AppData.INSTANCE;
        extractDataFromIntent();
        setupInspectionDetail();
        buildRecyclerView();
    }

    private void extractDataFromIntent()
    {
        Intent intent = getIntent();
        restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION, 0);
        inspectionPosition = intent.getIntExtra(INSPECTION_POSITION,0);
    }

    private void setupInspectionDetail()
    {
        TextView textViewInspectionDate =
            findViewById(R.id.textView_violation_activity_full_date);
        TextView textViewInspectionType =
            findViewById(R.id.textView_violation_activity_inspection_type);
        TextView textViewNumberOfCriticalIssues =
            findViewById(R.id.textView_violation_activity_critical_issues_found);
        TextView textViewNumberOfNonCriticalIssues =
            findViewById(R.id.textView_violation_activity_noncritical_issues_found);
        TextView textViewHazardLevel =
            findViewById(R.id.textView_violation_activity_hazard_level_words);
        ImageView imageViewHazardLevel =
            findViewById(R.id.imageView_violation_activity_hazard_level_icon);

        inspection =
            data.getEntryAtIndex(restaurantPosition).getInspections().get(inspectionPosition);

        textViewInspectionDate.setText(
            DateTimeUtil.MONTH_DAY_YEAR.getDateString(inspection.getDate()));
        textViewInspectionType.setText(inspection.getInspType());
        textViewNumberOfCriticalIssues.setText(String.valueOf(inspection.getNumCrit()));
        textViewNumberOfNonCriticalIssues.setText(String.valueOf(inspection.getNumNonCrit()));
        textViewHazardLevel.setText(String.valueOf(inspection.getHazardRating()));

        switch (inspection.getHazardRating()) {
            case LOW:
                imageViewHazardLevel.setImageResource(R.drawable.hazard_low);
                break;
            case MODERATE:
                imageViewHazardLevel.setImageResource(R.drawable.hazard_medium);
                break;
            case HIGH:
                imageViewHazardLevel.setImageResource(R.drawable.hazard_high);
                break;
            case NONE:
                imageViewHazardLevel.setImageResource(R.drawable.hazard_none);
                break;
        }
    }

    private void buildRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.violation_recyclerView);
        ViolationAdapter adapter = new ViolationAdapter(inspection);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ViolationAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
            Toast.makeText(
                getApplicationContext(),
                inspection.getViolations().get(position).getFullDescription(),
                Toast.LENGTH_LONG).show();
            }
        });
    }


    public static Intent makeLaunchIntent(Context c, int restaurantPosition,
                                          int inspectionPosition)
    {
        Intent intent = new Intent(c, ViolationActivity.class);
        intent.putExtra(RESTAURANT_POSITION, restaurantPosition);
        intent.putExtra(INSPECTION_POSITION, inspectionPosition);

        return intent;
    }
}
