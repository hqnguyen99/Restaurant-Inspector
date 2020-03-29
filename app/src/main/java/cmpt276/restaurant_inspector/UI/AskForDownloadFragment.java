package cmpt276.restaurant_inspector.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
