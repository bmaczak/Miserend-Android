package com.frama.miserend.hu.database.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment

import com.frama.miserend.hu.R

/**
 * Created by Balazs on 2018. 03. 11..
 */

class DatabaseMissingDialogFragment : DialogFragment() {


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
                .setTitle(arguments!!.getInt(EXTRA_TITLE))
                .setMessage(arguments!!.getInt(EXTRA_MESSAGE))
                .setPositiveButton(R.string.dialog_db_missing_btn_ok) { dialog, whichButton -> callback!!.onDownloadClicked() }
                .setNegativeButton(R.string.dialog_db_missing_btn_cancel) { dialog, whichButton -> callback!!.onDontDownloadClicked(true) }
                .create()
    }

    companion object {

        private const val EXTRA_TITLE = "title"
        private const val EXTRA_MESSAGE = "message"

        @JvmStatic
        fun newInstance(@StringRes title: Int, @StringRes message: Int): DatabaseMissingDialogFragment {
            val frag = DatabaseMissingDialogFragment()
            val args = Bundle()
            args.putInt(EXTRA_TITLE, title)
            args.putInt(EXTRA_MESSAGE, message)
            frag.arguments = args
            return frag
        }
    }
}
