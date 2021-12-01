package com.app.celda.Json

import com.app.celda.Model.Course
import org.json.JSONObject

class ImageJSONReader : JSONReader<Course> {
    val URL = "https://api.npoint.io/50aa76918a8ebde24c71"
    val TAG = "courses"

    fun getList() : MutableList<Course> {
        return parse(download())
    }

    override fun download(): String? {
        return JSONGet().execute(URL).get()
    }

    override fun parse(data: String?): MutableList<Course> {
        val courseList = mutableListOf<Course>()
        if (data?.isNotEmpty() == true) {
            val json = JSONObject(data)
            val jsonArray = json.getJSONArray(TAG)
            for (i in 0 until jsonArray.length()) {
                parseObject(jsonArray.getJSONObject(i))?.let {
                    courseList.add(it)
                }
            }
        }
        return courseList
    }

    override fun parseObject(jsonObject: JSONObject): Course {
        return Course(
            jsonObject.getString("id").toInt(),
            GetBitmapFromURL().execute(jsonObject.getString("img")).get(),
            jsonObject.getString("name"),
            jsonObject.getString("description"),
            jsonObject.getString("author")
        )
    }


}