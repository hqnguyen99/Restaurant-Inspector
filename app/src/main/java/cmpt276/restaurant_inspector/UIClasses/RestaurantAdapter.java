package cmpt276.restaurant_inspector.UIClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;
import java.util.Random;

import cmpt276.restaurant_inspector.UI.R;
import cmpt276.restaurant_inspector.model.AppData;
import cmpt276.restaurant_inspector.model.DataSingleton;
import cmpt276.restaurant_inspector.model.DateTimeUtil;
import cmpt276.restaurant_inspector.model.Inspection;
import cmpt276.restaurant_inspector.model.RestaurantInspectionsPair;

import static android.os.Build.VERSION_CODES.O;


/**
 *  Adapter for restaurant recycler view
 */

@RequiresApi(O)
public class RestaurantAdapter
    extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>
{
    private DataSingleton data;
    private OnItemClickListener restaurantListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        restaurantListener = listener;
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewRestaurantName;
        TextView textViewNumberFound;
        TextView textViewDateFromNow;
        ImageView imageViewRestaurantIcon;
        ImageView imageViewHazardLevelIcon;

        RestaurantViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewRestaurantName =
                    itemView.findViewById(R.id.textView_restaurant_activity_restaurant_name);
            textViewNumberFound =
                    itemView.findViewById(R.id.textView_restaurant_activity_issue);
            textViewDateFromNow =
                    itemView.findViewById(R.id.textView_restaurant_activity_date);
            imageViewRestaurantIcon =
                    itemView.findViewById(R.id.imageView_restaurant_activity_restaurant_icon);
            imageViewHazardLevelIcon =
                    itemView.findViewById(R.id.imageView_restaurant_activity_warning_level);

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

    public RestaurantAdapter() {
        data = AppData.INSTANCE;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(
            parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view, restaurantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position)
    {
        RestaurantInspectionsPair current = data.getEntryAtIndex(position);
        holder.textViewRestaurantName.setText(current.getRestaurant().getName());
        holder.textViewNumberFound.setText(String.valueOf(current.getNumViolations()));

        // Random generate restaurant icon
        Random rand = new Random();
        int n = rand.nextInt(4);

        if(current.getRestaurant().getName().contains("McDonald")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.mcdonald_logo);
        }
        else if (current.getRestaurant().getName().contains("7-Eleven")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.seveneleven_logo);
        }
        else if (current.getRestaurant().getName().contains("Panago")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.panago_logo);
        }
        else if (current.getRestaurant().getName().contains("Pizza Hut")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.pizzahut_logo);
        }
        else if (current.getRestaurant().getName().contains("Save On Foods")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.saveonfoods_logo);
        }
        else if (current.getRestaurant().getName().contains("A&W") || current.getRestaurant().getName().contains("A & W")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.a_w_logo);
        }
        else if (current.getRestaurant().getName().contains("Starbucks")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.starbucks_logo);
        }
        else if (current.getRestaurant().getName().contains("Subway")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.subway_logo);
        }
        else if (current.getRestaurant().getName().contains("Tim Hortons")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.timhortons_logo);
        }
        else if (current.getRestaurant().getName().contains("Wendy's")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.wendys_logo);
        }
        else if (current.getRestaurant().getName().contains("Quizno")){
            holder.imageViewRestaurantIcon.setImageResource(R.drawable.quiznos_logo);
        }
        else {
            if (n == 0){
                holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_0);
            } else if (n == 1){
                holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_1);
            } else if (n == 2){
                holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_2);
            } else if(n == 3) {
                holder.imageViewRestaurantIcon.setImageResource(R.drawable.restaurant_3);
            }
        }

        if (!current.getInspections().isEmpty()) {
            Inspection newestInspection = current.getInspections().get(0);
            setViewHazardLevelIcon(holder, newestInspection);
            setViewDateFromNow(holder, newestInspection);
        } else {
            holder.textViewDateFromNow.setText("None");
        }
    }

    private void setViewDateFromNow(RestaurantViewHolder holder, Inspection inspection)
    {
        long daysFromNewestInspection = DateTimeUtil.daysFromNow(inspection.getDate());

        if (daysFromNewestInspection <= 30) {
            holder.textViewDateFromNow.setText(
                String.format(Locale.ENGLISH,"%d days ago", daysFromNewestInspection)
            );
        } else if (daysFromNewestInspection < 365) {
            holder.textViewDateFromNow.setText(
                DateTimeUtil.MONTH_DAY.getDateString(inspection.getDate())
            );
        } else {
            holder.textViewDateFromNow.setText(
                DateTimeUtil.MONTH_DAY_YEAR.getDateString(inspection.getDate())
            );
        }
    }

    private void setViewHazardLevelIcon(RestaurantViewHolder holder, Inspection inspection)
    {
        switch (inspection.getHazardRating()) {
            case LOW:
                holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_low);
                break;
            case MODERATE:
                holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_medium);
                break;
            case HIGH:
                holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_high);
                break;
            case NONE:
                holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_none);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
