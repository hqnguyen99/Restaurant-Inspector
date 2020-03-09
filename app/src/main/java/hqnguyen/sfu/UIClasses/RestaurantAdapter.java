package hqnguyen.sfu.UIClasses;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import hqnguyen.sfu.UI.R;
import model.AppData;
import model.DataSingleton;
import model.Restaurant;
import model.RestaurantInspectionsPair;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private DataSingleton data;

    private OnItemClickListener restaurantListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        restaurantListener = listener;
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewRestaurantName;
        public TextView textViewNumberFound;
        public TextView textViewDateFromNow;
        public ImageView imageViewRestaurantIcon;

        public RestaurantViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewRestaurantName = itemView.findViewById(R.id.textView_restaurant_activity_restaurant_name);
            textViewNumberFound = itemView.findViewById(R.id.textView_restaurant_activity_issue);
            textViewDateFromNow = itemView.findViewById(R.id.textView_restaurant_activity_date);
            imageViewRestaurantIcon = itemView.findViewById(R.id.imageView_restaurant_activity_restaurant_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RestaurantAdapter(){
        data = AppData.INSTANCE;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(view, restaurantListener);
        return restaurantViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantInspectionsPair current = data.getEntryAtIndex(position);

        holder.textViewRestaurantName.setText(current.getRestaurant().getName());
        holder.textViewNumberFound.setText(String.valueOf(current.getNumViolations()));
        holder.textViewDateFromNow.setText(Long.toString(current.daysFromNewestInspection()));
        holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_0);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
