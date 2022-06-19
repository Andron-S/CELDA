package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class LectionModel(
    @SerializedName("id")
    var id_lesson: Int,
    @SerializedName("module")
    var id_module: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var discript: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("video_src")
    var video: String

) {
}