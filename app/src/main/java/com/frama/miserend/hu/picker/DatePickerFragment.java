package com.frama.miserend.hu.picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.threeten.bp.LocalDate;

/**
 * Created by Balazs on 2018. 03. 29..
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static String EXTRA_ID = "id";
    private static String EXTRA_INIT_DATE = "date";

    private DateSelectedListener listener;

    public static DatePickerFragment newInstance(int id, LocalDate initDate) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_ID, id);
        args.putSerializable(EXTRA_INIT_DATE, initDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DateSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DateSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalDate initDate = (LocalDate) getArguments().getSerializable(EXTRA_INIT_DATE);

        return new DatePickerDialog(getActivity(), this, initDate.getYear(), initDate.getMonthValue(),
                initDate.getDayOfMonth());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        listener.onDateSelected(getArguments().getInt(EXTRA_ID), LocalDate.of(year, month, day));
    }


    public interface DateSelectedListener {
        void onDateSelected(int id, LocalDate selectedDate);
    }
}
