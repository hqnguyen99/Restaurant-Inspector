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
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AskForDownloadFragment extends AppCompatDialogFragment {
    private OnSelectionListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnSelectionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                "must implement onSelectionListener.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity())
            .inflate(R.layout.load_message_layout, null);

        builder.setView(v)
            .setTitle("New data is available, would you like to download?" +
                ", data will not be downloaded if no response in 2s")
            .setView(v)
            .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.sendStatus(false);
                    getDialog().dismiss();
                }
            })
            .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.sendStatus(true);
                    getDialog().dismiss();
                }
            });


        return builder.create();
    }

    public interface OnSelectionListener {
        void sendStatus(boolean status);
    }
}
