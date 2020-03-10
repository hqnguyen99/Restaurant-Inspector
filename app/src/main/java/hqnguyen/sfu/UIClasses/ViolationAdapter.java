package hqnguyen.sfu.UIClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hqnguyen.sfu.UI.R;
import model.Inspection;
import model.Violation;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {

    private List<Violation> violationList;

    private OnItemClickListener violationListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        violationListener = listener;
    }
    public static class ViolationViewHolder extends RecyclerView.ViewHolder{

        public ImageView natureIcon;
        public ImageView severityLevelIcon;
        public TextView briefDescription;
        public ViolationViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            natureIcon = (ImageView) itemView.findViewById(R.id.imageView_violation_activity_violation_nature_icon);
            severityLevelIcon = (ImageView) itemView.findViewById(R.id.imageView_violation_activity_violation_severity_icon);
            briefDescription = (TextView) itemView.findViewById(R.id.textView_violation_activity_brief_description);

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
    public ViolationAdapter(Inspection inspection){
        violationList = inspection.getViolations();
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.violation_item,parent,false);
        ViolationViewHolder violationViewHolder = new ViolationViewHolder(view, violationListener);
        return violationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {

        holder.briefDescription.setText(violationList.get(position).getfullDescription());
        holder.severityLevelIcon.setImageResource(R.drawable.violation_severity_critical);
        holder.natureIcon.setImageResource(R.drawable.violation_nature_equipment);
    }

    @Override
    public int getItemCount() {
        return violationList.size();
    }
}
