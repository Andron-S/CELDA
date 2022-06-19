package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class LessonDoneModel(
    @SerializedName("lesson")
    var lesson_id: Int,
    @SerializedName("profile")
    var profile_id: Int,
    @SerializedName("is_lesson_done")
    var is_lesson_done: String
) {
}