package com.frama.miserend.hu.picker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import org.threeten.bp.LocalTime;

/**
 * Created by Balazs on 2018. 03. 29..
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static String EXTRA_ID = "id";
    private static String EXTRA_INIT_TIME = "time";

    private TimeSelectedListener listener;

    public static TimePickerFragment newInstance(int id, LocalTime initTime) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_ID, id);
        args.putSerializable(EXTRA_INIT_TIME, initTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TimeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TimeSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalTime initTime = (LocalTime) getArguments().getSerializable(EXTRA_INIT_TIME);

        return new TimePickerDialog(getActivity(), this, initTime.getHour(), initTime.getMinute(),
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listener.onTimeSelected(getArguments().getInt(EXTRA_ID), LocalTime.of(hourOfDay, minute));
    }

    public interface TimeSelectedListener {
        void onTimeSelected(int id, LocalTime selectedTime);
    }
}
