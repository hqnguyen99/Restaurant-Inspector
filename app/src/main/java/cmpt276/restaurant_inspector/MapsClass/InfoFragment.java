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
import cmpt276.restaurant_inspector.UI.MapsActivity;
import cmpt276.restaurant_inspector.UI.R;

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
        address.setText(marker.getSnippet());
        hazardLevel.setText("Hazard Level: " + marker.getHarzardLevel());
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
                .setTitle("Restaurant Information")
                .setView(view)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }
}
