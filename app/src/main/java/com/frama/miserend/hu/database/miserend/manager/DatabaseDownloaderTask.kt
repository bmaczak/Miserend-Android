package com.frama.miserend.hu.database.miserend.manager

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.URL

class DatabaseDownloaderTask(internal var context: Context, private var listener: OnDbDownloadedListener?) : AsyncTask<String, Int, Boolean>() {

    interface OnDbDownloadedListener {
        fun onDbDownloadStarted()

        fun onDbDownloadFinished(success: Boolean)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        if (listener != null) {
            listener!!.onDbDownloadStarted()
        }
    }

    override fun doInBackground(vararg params: String): Boolean? {
        val url = params[0]
        return downloadFromUrl(url)
    }

    override fun onPostExecute(result: Boolean?) {
        if (listener != null) {
            listener!!.onDbDownloadFinished(result!!)
        }
        super.onPostExecute(result)
    }

    private fun downloadFromUrl(DownloadUrl: String): Boolean {
        try {
            val url = URL(DownloadUrl)
            val file = context.getDatabasePath(DatabaseManager.DATABASE_FILE_NAME)
            file.parentFile.mkdirs()
            val connection = url.openConnection()
            connection.connect()
            val input = BufferedInputStream(url.openStream(),
                    8192)
            val output = FileOutputStream(file)
            val data = ByteArray(1024)
            while (true) {
                var count = input.read(data)
                if (count == -1) {
                    break
                }
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            return true
        } catch (e: Exception) {
            Log.e("Error: ", e.message)
            return false
        }
    }
}
