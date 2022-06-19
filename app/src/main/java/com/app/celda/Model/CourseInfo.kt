package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class CourseInfo (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("author")
    var author: String,
    @SerializedName("description")
    var discript: String,
    @SerializedName("full_img")
    var img: String
) {

}