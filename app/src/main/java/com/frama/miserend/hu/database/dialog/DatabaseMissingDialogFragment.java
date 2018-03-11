package com.frama.miserend.hu.database.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.home.HomeScreenActivity;

/**
 * Created by Balazs on 2018. 03. 11..
 */

public class DatabaseMissingDialogFragment extends DialogFragment {

    public static DatabaseMissingDialogFragment newInstance() {
        DatabaseMissingDialogFragment frag = new DatabaseMissingDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_db_missing_title)
                .setMessage(R.string.dialog_db_missing_message)
                .setPositiveButton(R.string.dialog_db_missing_btn_ok,
                        (dialog, whichButton) -> ((HomeScreenActivity) getActivity()).downloadDatabase()
                )
                .setNegativeButton(R.string.dialog_db_missing_btn_cancel,
                        (dialog, whichButton) -> getActivity().finish()
                )
                .create();
    }
}
