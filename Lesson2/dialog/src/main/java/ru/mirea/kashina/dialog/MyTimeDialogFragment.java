package ru.mirea.kashina.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.app.TimePickerDialog;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
public class MyTimeDialogFragment extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), (timePicker, hour, minute) -> {
        }, 0, 0, true);
    }
}
