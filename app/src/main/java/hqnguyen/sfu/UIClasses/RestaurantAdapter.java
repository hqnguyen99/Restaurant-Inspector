package hqnguyen.sfu.UIClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import hqnguyen.sfu.UI.R;
import model.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<TestRestaurant> restaurantList;

    private OnItemClickListener restaurantListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        restaurantListener = listener;
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewRestaurantIcon;
        public ImageView imageViewHazardLevelIcon;
        public TextView textViewRestaurantName;
        public TextView textViewNumberFound;
        public TextView textViewDateFromNow;

        public RestaurantViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageViewRestaurantIcon = itemView.findViewById(R.id.imageView_restaurant_activity_restaurant_icon);
            textViewRestaurantName = itemView.findViewById(R.id.textView_restaurant_activity_restaurant_name);
            imageViewHazardLevelIcon = itemView.findViewById(R.id.imageView_restaurant_activity_warning_level);
            textViewNumberFound = itemView.findViewById(R.id.textView_restaurant_activity_issue);
            textViewDateFromNow = itemView.findViewById(R.id.textView_restaurant_activity_date);

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

    public RestaurantAdapter(ArrayList<TestRestaurant> exampleList){
        restaurantList = exampleList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(view, restaurantListener);
        return restaurantViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        TestRestaurant current = restaurantList.get(position);

        holder.imageViewRestaurantIcon.setImageResource(current.getImageViewRestaurantIcon());
        holder.imageViewHazardLevelIcon.setImageResource(current.getImageViewHazardLevel());
        holder.textViewRestaurantName.setText(current.getRestaurantName());
        holder.textViewNumberFound.setText(String.valueOf(current.getNumberIssueFound()));
        holder.textViewDateFromNow.setText(current.getDateFromNow());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
