package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class CourseProgress(
    @SerializedName("course")
    var id: Int,
    @SerializedName("profile")
    var profile_id: Int,
    @SerializedName("is_course_done")
    var is_course_done: String,

    )
{
}