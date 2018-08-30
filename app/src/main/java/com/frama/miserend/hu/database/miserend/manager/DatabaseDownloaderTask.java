package com.frama.miserend.hu.database.miserend.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DatabaseDownloaderTask extends AsyncTask<String, Integer, Boolean> {

    public static interface OnDbDownloadedListener {
        void onDbDownloadStarted();

        void onDbDownloadFinished(boolean success);
    }

    OnDbDownloadedListener listener;
    Context context;

    public DatabaseDownloaderTask(Context context, OnDbDownloadedListener listener) {
        super();
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onDbDownloadStarted();
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String url = params[0];
        return downloadFromUrl(url);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (listener != null) {
            listener.onDbDownloadFinished(result);
        }
        super.onPostExecute(result);
    }

    private boolean downloadFromUrl(String DownloadUrl) {

        int count;
        try {
            URL url = new URL(DownloadUrl); // you can write here any link
            File file = context.getDatabasePath(DatabaseManager.DATABASE_FILE_NAME);
            file.getParentFile().mkdirs();

            URLConnection conection = url.openConnection();
            conection.connect();
            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];


            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
            return true;
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return false;
        }
    }

}
