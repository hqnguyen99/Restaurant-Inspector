package cmpt276.restaurant_inspector.MapsClass;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import cmpt276.restaurant_inspector.UI.InspectionActivity;
import cmpt276.restaurant_inspector.UI.R;
import cmpt276.restaurant_inspector.model.HazardRating;

// This class generate infor window when tap to the restaurant icon

public class InfoFragment extends AppCompatDialogFragment {
    private final MyItem marker;
    public InfoFragment(MyItem marker) {
        this.marker = marker;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create the View to show
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.info_dialog, null);
        TextView name = (TextView) view.findViewById(R.id.info_dialog_rest_name);
        TextView address =  (TextView) view.findViewById(R.id.info_dialog_rest_address);
        TextView hazardLevel =  (TextView) view.findViewById(R.id.info_dialog_hazard_level);
        name.setText(marker.getTitle());
        address.setText(getResources().getString(R.string.dialog_information_restaurant_address) + marker.getSnippet());
        if (marker.getHarzardLevel().equals("Low")){
            hazardLevel.setText(getResources().getString(R.string.dialog_information_restaurant_level)
                    + getResources().getString(R.string.dialog_information_hazard_level_low));
        }else if(marker.getHarzardLevel().equals("Moderate")){
            hazardLevel.setText(getResources().getString(R.string.dialog_information_restaurant_level)
                    + getResources().getString(R.string.dialog_information_hazard_level_moderate));
        }else if (marker.getHarzardLevel().equals("High")){
            hazardLevel.setText(getResources().getString(R.string.dialog_information_restaurant_level)
                    + getResources().getString(R.string.dialog_information_hazard_level_high));
        }else if (marker.getHarzardLevel().equals("None")){
            hazardLevel.setText(getResources().getString(R.string.dialog_information_restaurant_level)
                    + getResources().getString(R.string.dialog_information_hazard_level_none));
        }
        // Create button Listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = InspectionActivity
                                .makeLaunchIntent(getActivity(),
                                        marker.getPositionInRestaurantList());
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        // Build the dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_information_title))
                .setView(view)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }
}
