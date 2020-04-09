package cmpt276.restaurant_inspector.UIClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cmpt276.restaurant_inspector.Filter.FilterData;
import cmpt276.restaurant_inspector.UI.R;
import cmpt276.restaurant_inspector.model.DateTimeUtil;
import cmpt276.restaurant_inspector.model.Inspection;
import cmpt276.restaurant_inspector.model.RestaurantInspectionsPair;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private List<RestaurantInspectionsPair> favourites;

    static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRestaurantName;
        TextView textViewDateFromNow;
        ImageView imageViewRestaurantIcon;
        ImageView imageViewHazardLevelIcon;

        FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRestaurantName =
                itemView.findViewById(R.id.textView_restaurant_activity_restaurant_name);
            textViewDateFromNow =
                itemView.findViewById(R.id.textView_restaurant_activity_date);
            imageViewRestaurantIcon =
                itemView.findViewById(R.id.imageView_restaurant_activity_restaurant_icon);
            imageViewHazardLevelIcon =
                itemView.findViewById(R.id.imageView_restaurant_activity_warning_level);
        }
    }

    public FavouriteAdapter(List<RestaurantInspectionsPair> favourites) {
        this.favourites = favourites;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
            parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new FavouriteAdapter.FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position)
    {
        FilterData newFilterData = FilterData.getInstance();
        if (newFilterData.getRestaurantPositionInFilterBox().size() > 0) {
            RestaurantInspectionsPair current = favourites.get(position);
            holder.textViewRestaurantName.setText(current.getRestaurant().getName());

            if (!current.getInspections().isEmpty()) {
                Inspection newestInspection = current.getInspections().get(0);
                setViewHazardLevelIcon(holder, newestInspection);
                setViewDateFromNow(holder, newestInspection);
            } else {
                holder.textViewDateFromNow.setText("None");
                holder.imageViewHazardLevelIcon.setImageResource(R.drawable.hazard_none);
            }
        }
    }

    private void setViewDateFromNow(FavouriteAdapter.FavouriteViewHolder holder, Inspection inspection)
    {
        long daysFromNewestInspection = DateTimeUtil.daysFromNow(inspection.getDate());

        if (daysFromNewestInspection <= 30) {
            holder.textViewDateFromNow.setText(
                String.format("%d" + holder.itemView.getResources()
                        .getString(R.string.restaurant_adapter_days_before)
                    , daysFromNewestInspection)
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

    private void setViewHazardLevelIcon(FavouriteAdapter.FavouriteViewHolder holder, Inspection inspection)
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
        return favourites.size();
    }
}
