package hqnguyen.sfu.UIClasses;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {

    public static class ViolationViewHolder extends RecyclerView.ViewHolder{

        public ViolationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
