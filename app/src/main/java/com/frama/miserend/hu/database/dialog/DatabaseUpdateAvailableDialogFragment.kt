package com.frama.miserend.hu.database.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment

import com.frama.miserend.hu.R

/**
 * Created by Balazs on 2018. 03. 11..
 */

class DatabaseUpdateAvailableDialogFragment : DialogFragment() {

    private var callback: DatabaseDialogCallback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DatabaseDialogCallback) {
            callback = context
        } else {
            throw ClassCastException(context!!.toString() + " must implement DatabaseDialogCallback")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(R.string.dialog_db_update_title)
                .setMessage(R.string.dialog_db_update_message)
                .setPositiveButton(R.string.dialog_db_update_btn_ok) { dialog, whichButton -> callback!!.onDownloadClicked() }
                .setNegativeButton(R.string.dialog_db_update_btn_cancel) { dialog, whichButton -> callback!!.onDontDownloadClicked(false) }
                .create()
    }

    companion object {

        @JvmStatic
        fun newInstance(): DatabaseUpdateAvailableDialogFragment {
            return DatabaseUpdateAvailableDialogFragment()
        }
    }
}
