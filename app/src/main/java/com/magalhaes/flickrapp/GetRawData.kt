package com.magalhaes.flickrapp

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

private const val TAG = "GetRawData"

class GetRawData(private val listener: OnDownloadComplete) : AsyncTask<String, Void, String>() {
    private var dowloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete {
        fun onDownloadCompleted(data: String, status: DownloadStatus)
    }

//    private var listener: MainActivity? = null
//
//    fun setDownloadCompleteListener(callbackObject: MainActivity) {
//        listener = callbackObject
//    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute called")
        listener.onDownloadCompleted(result, dowloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            dowloadStatus = DownloadStatus.NOT_INITIALISED
            return "No URL specified"
        }

        try {
            dowloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    dowloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInbackground: Invalid URL ${e.message}"
                }
                is IOException -> {
                    dowloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInbackground: Security exception: Needs permission? ${e.message}"
                }
                else -> {
                    dowloadStatus = DownloadStatus.ERROR
                    "Unknown error: ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)

            return errorMessage
        }
    }
}