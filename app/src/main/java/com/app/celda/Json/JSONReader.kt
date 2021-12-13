package com.app.celda.Json

import org.json.JSONObject

interface JSONReader<T> {

    fun download() : String?

    fun parse(data: String?) : MutableList<T>

    fun parse2(data: String?) : MutableList<MutableList<T>>

    fun parseObject(jsonObject: JSONObject) : T?

}