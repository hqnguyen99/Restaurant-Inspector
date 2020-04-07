package cmpt276.restaurant_inspector.UIClasses;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cmpt276.restaurant_inspector.UI.R;
import cmpt276.restaurant_inspector.model.Inspection;
import cmpt276.restaurant_inspector.model.Violation;

/**
 *  Adapter for violation recycler view
 */
public class ViolationAdapter
    extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder>
{
    private List<Violation> violationList;
    private OnItemClickListener violationListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        violationListener = listener;
    }

    static class ViolationViewHolder extends RecyclerView.ViewHolder
    {
        ImageView natureIcon;
        ImageView severityLevelIcon;
        TextView briefDescription;

        ViolationViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            natureIcon =
                itemView.findViewById(R.id.imageView_violation_activity_violation_nature_icon);
            severityLevelIcon =
                itemView.findViewById(R.id.imageView_violation_activity_violation_severity_icon);
            briefDescription =
                itemView.findViewById(R.id.textView_violation_activity_brief_description);

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

    public ViolationAdapter(Inspection inspection) {
        violationList = inspection.getViolations();
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(
            parent.getContext()).inflate(R.layout.violation_item,parent,false);

        return new ViolationViewHolder(view, violationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position)
    {
        Violation violation = violationList.get(position);
        if (violation.isCrit()){
            holder.briefDescription.setText(violation.getBriefDescription() +
                    holder.itemView.getResources().getString(R.string.violation_adapter_critical));
        }else{
            holder.briefDescription.setText(violation.getBriefDescription() +
                    holder.itemView.getResources()
                            .getString(R.string.violation_adapter_not_critical));
        }

        switch(violation.getType()) {
            case FOOD:
                holder.natureIcon.setImageResource(R.drawable.violation_nature_food);
                break;
            case PEST:
                holder.natureIcon.setImageResource(R.drawable.violation_nature_pest);
                break;
            case EMPLOYEE:
                holder.natureIcon.setImageResource(R.drawable.violation_nature_emloyee);
                break;
            case LOCATION:
                holder.natureIcon.setImageResource(R.drawable.violation_nature_location);
                break;
            case EQUIPMENT:
                holder.natureIcon.setImageResource(R.drawable.violation_nature_equipment);
                break;
            case CERTIFICATION:
                holder.natureIcon.setImageResource(R.drawable.violation_nature_certification);
                break;
        }

        if(violation.isCrit()) {
            holder.severityLevelIcon.setImageResource(R.drawable.violation_severity_critical);
        } else {
            holder.severityLevelIcon.setImageResource(R.drawable.violation_severity_noncritical);
        }
    }

    @Override
    public int getItemCount() {
        return violationList.size();
    }
}
