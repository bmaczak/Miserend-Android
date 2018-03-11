package com.frama.miserend.hu.database.dialog;

/**
 * Created by Balazs on 2018. 03. 11..
 */

public interface DatabaseDialogCallback {
    void onDownloadClicked();

    void onDontDownloadClicked(boolean dbMissing);
}
