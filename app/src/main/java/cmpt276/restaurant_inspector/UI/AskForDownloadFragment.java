package cmpt276.restaurant_inspector.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AskForDownloadFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.load_message_layout, null);


        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //move on to map/restaurantlist
                        //dont do anything
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //start download
                        break;
                }
                //stop download on click
            }
        };
        return new AlertDialog.Builder(getActivity())
                .setTitle("Loading")
                .setView(v)
                .setPositiveButton(android.R.string.no, listener)
                .setNegativeButton(android.R.string.yes, listener)
                .create();
    }
}
