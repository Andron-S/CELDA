package com.app.celda.Model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

class CourseModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("author")
    var author: String,
    @SerializedName("img_preview")
    var img: String
) {
}