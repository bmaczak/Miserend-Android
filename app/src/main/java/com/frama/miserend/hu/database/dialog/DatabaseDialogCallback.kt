package com.frama.miserend.hu.database.dialog

/**
 * Created by Balazs on 2018. 03. 11..
 */

interface DatabaseDialogCallback {
    fun onDownloadClicked()

    fun onDontDownloadClicked(dbMissing: Boolean)
}
