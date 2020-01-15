package com.royce.sololeveling;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TimePickedListener listener; // callback

    public interface TimePickedListener{
        void pickedTime(String time);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        listener = (TimePickedListener)getActivity();
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public TimePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // pass the hour and minute to the add mission fragment
        String time;
        if(minute < 10){
            time = hourOfDay + ":0" + minute;
        }
        else{
            time = hourOfDay + ":" + minute;
        }
        listener.pickedTime(time);
        // TODO: I can send the values to the database and then read them into the textview

    }

    @Override
    public void onDetach() {
        listener = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }
}
