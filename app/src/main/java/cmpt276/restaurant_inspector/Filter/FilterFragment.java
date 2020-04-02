package cmpt276.restaurant_inspector.Filter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import cmpt276.restaurant_inspector.UI.R;

public class FilterFragment extends AppCompatDialogFragment {
    private Switch isFavourite;
    private Spinner hazardLevel;
    private EditText numberOfViolationsMoreThanEditText;
    private FilterDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Boolean[] isFavouriteList = new Boolean[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.filter_dialog, null);
        isFavourite = (Switch) view.findViewById(R.id.switch_filter_box_isFavourite);
        hazardLevel = (Spinner) view.findViewById(R.id.spinner_filter_box_hazard_level);
        numberOfViolationsMoreThanEditText = (EditText) view.findViewById(R.id.editText_filter_box_number_of_violations);

        isFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isFavouriteList[0] = isChecked;
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.hazard_level, R.layout.support_simple_spinner_dropdown_item);

        //adapter.setDropDownViewResource(R.layout.filter_dialog);
        hazardLevel.setAdapter(adapter);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("isChecked",String.valueOf(isFavouriteList[0]));
                        Log.i("hazard level",hazardLevel.getSelectedItem().toString());
                        String numberOfViolationsMoreThanString = numberOfViolationsMoreThanEditText.getText().toString();
                        int numberOfViolationsLessThan;
                                if("".equals(numberOfViolationsMoreThanString)){
                                    numberOfViolationsLessThan = 0;
                                }
                                else {
                                    numberOfViolationsLessThan = Integer.parseInt(numberOfViolationsMoreThanString);
                                }
                        listener.getInput(isFavouriteList[0],hazardLevel.getSelectedItem().toString(),numberOfViolationsLessThan);
                    }
                });
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FilterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement FilterDialogListener");
        }
    }

    public interface FilterDialogListener{
            void getInput(Boolean isFavorite, String hazardLevel, int numberOfViolations);
    }
}
