package com.app.celda.Json

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.net.URL

class GetBitmapFromURL : AsyncTask<String, Void, Bitmap?>() {
    override fun doInBackground(vararg urls: String?): Bitmap? {
        var bitmap : Bitmap? = null
        val url = URL(urls[0])
        val inputStream = url.openStream()
        bitmap = BitmapFactory.decodeStream(inputStream)
        return bitmap
    }

}