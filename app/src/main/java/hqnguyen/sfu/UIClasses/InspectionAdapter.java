package hqnguyen.sfu.UIClasses;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.InspectionViewHolder> {

    public static class InspectionViewHolder extends RecyclerView.ViewHolder{

        public InspectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public InspectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
