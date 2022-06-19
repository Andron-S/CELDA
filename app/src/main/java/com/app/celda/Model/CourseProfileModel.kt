package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class CourseProfileModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name_course: String
) {
}