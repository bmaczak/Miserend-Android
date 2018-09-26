package com.frama.miserend.hu.database.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;

import com.frama.miserend.hu.R;

/**
 * Created by Balazs on 2018. 03. 11..
 */

public class DatabaseMissingDialogFragment extends DialogFragment {

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_MESSAGE = "message";


    private DatabaseDialogCallback callback;

    public static DatabaseMissingDialogFragment newInstance(@StringRes int title,@StringRes int message) {
        DatabaseMissingDialogFragment frag = new DatabaseMissingDialogFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_TITLE, title);
        args.putInt(EXTRA_MESSAGE, message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (DatabaseDialogCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DatabaseDialogCallback");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getInt(EXTRA_TITLE))
                .setMessage(getArguments().getInt(EXTRA_MESSAGE))
                .setPositiveButton(R.string.dialog_db_missing_btn_ok,
                        (dialog, whichButton) -> callback.onDownloadClicked()
                )
                .setNegativeButton(R.string.dialog_db_missing_btn_cancel,
                        (dialog, whichButton) -> callback.onDontDownloadClicked(true)
                )
                .create();
    }
}
