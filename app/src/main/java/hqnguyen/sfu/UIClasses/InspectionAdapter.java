package hqnguyen.sfu.UIClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hqnguyen.sfu.UI.R;
import model.AppData;
import model.DataSingleton;
import model.Inspection;
import model.RestaurantInspectionsPair;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.InspectionViewHolder> {
    private DataSingleton data;
    private int restaurantPosition;
    private List<Inspection> inspectionsList;

    private OnItemClickListener inspectionListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        inspectionListener = listener;
    }


    public static class InspectionViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewHazardLevelIcon;
        public TextView textViewDateOfInspection;
        public TextView textViewNumberOfCriticalIssues;
        public TextView textViewNumberOfNonCriticalIssues;

        public InspectionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageViewHazardLevelIcon = itemView.findViewById(R.id.imageView_inspection_activity_hazard_level_icon);
            textViewDateOfInspection = itemView.findViewById(R.id.textView_inspection_activity_date_title);
            textViewNumberOfCriticalIssues = itemView.findViewById(R.id.textView_inspection_activity_critical_issue_found);
            textViewNumberOfNonCriticalIssues = itemView.findViewById(R.id.textView_inspection_activity_noncritical_issue_found);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public InspectionAdapter(int restaurantPosition){
        data= AppData.INSTANCE;
        this.restaurantPosition = restaurantPosition;
        inspectionsList= data.getEntryAtIndex(this.restaurantPosition).getInspections();
    }

    @NonNull
    @Override
    public InspectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inspection_item,parent, false);
        InspectionViewHolder inspectionViewHolder = new InspectionViewHolder(view, inspectionListener);
        return inspectionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionViewHolder holder, int position) {

        holder.textViewNumberOfCriticalIssues.setText(String.valueOf(inspectionsList.get(position).getNumCrit()));
        holder.textViewNumberOfNonCriticalIssues.setText(String.valueOf(inspectionsList.get(position).getNumNonCrit()));
        holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_low);
    }

    @Override
    public int getItemCount() {
        return inspectionsList.size();
    }
}
