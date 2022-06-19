package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class LessonModel(
    @SerializedName("id")
    var id_lesson: Int,
    @SerializedName("module")
    var id_module: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var discript: String
)

{

}