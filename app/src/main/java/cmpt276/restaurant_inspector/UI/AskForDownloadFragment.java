package cmpt276.restaurant_inspector.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    public interface OnSelectionListener {
        void sendStatus(boolean status);
    }

    public OnSelectionListener listener;
    private boolean status;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnSelectionListener) getActivity();
        } catch (ClassCastException e) {
            throw e;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.load_message_layout, null);

        MainActivity callbackActivity = new MainActivity();

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        status = false;
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        status = true;
                        break;
                }

                listener.sendStatus(status);
                getDialog().dismiss();
            }
        };
        return new AlertDialog.Builder(getActivity())
                .setTitle("New data is available, would you like to download?")
                .setView(v)
                .setPositiveButton(android.R.string.no, listener)
                .setNegativeButton(android.R.string.yes, listener)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
