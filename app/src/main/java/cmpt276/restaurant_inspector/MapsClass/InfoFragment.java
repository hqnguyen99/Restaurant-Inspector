package cmpt276.restaurant_inspector.MapsClass;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import cmpt276.restaurant_inspector.UI.InspectionActivity;
import cmpt276.restaurant_inspector.UI.MapsActivity;
import cmpt276.restaurant_inspector.UI.R;

public class InfoFragment extends AppCompatDialogFragment {
    private final int position;
    public InfoFragment(int position) {
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Create the View to show
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.info_dialog, null);
        // Create button Listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        /*Intent intent = InspectionActivity
                                .makeLaunchIntent(this,
                                        position);
                        startActivity(intent);*/
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
