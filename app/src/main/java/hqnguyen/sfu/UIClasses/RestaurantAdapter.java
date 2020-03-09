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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import hqnguyen.sfu.UI.R;
import hqnguyen.sfu.UI.RestaurantActivity;
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
        public ImageView imageViewHazardLevelIcon;

        public RestaurantViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewRestaurantName = itemView.findViewById(R.id.textView_restaurant_activity_restaurant_name);
            textViewNumberFound = itemView.findViewById(R.id.textView_restaurant_activity_issue);
            textViewDateFromNow = itemView.findViewById(R.id.textView_restaurant_activity_date);
            imageViewRestaurantIcon = itemView.findViewById(R.id.imageView_restaurant_activity_restaurant_icon);
            imageViewHazardLevelIcon = itemView.findViewById(R.id.imageView_restaurant_activity_warning_level);

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

        // Random generate restaurant icon
        Random rand = new Random();
        int n = rand.nextInt(4);
        if (n == 0){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_0);
        }else if (n == 1){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_1);
        }else if (n == 2){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_2);
        }else if(n == 3){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_3);
        }

        // Generate hazard level icon based on hazard level
//        if () {
//            holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_low);
//        } else if () {
//            holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_medium);
//        } else if{
//            holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_high);
//        }

        // Get right date
        //holder.textViewDateFromNow.setText(Long.toString(current.daysFromNewestInspection()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
