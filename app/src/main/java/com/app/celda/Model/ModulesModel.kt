package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class ModulesModel(
    @SerializedName("id")
    var module_id: Int,
    @SerializedName("name")
    var module_name: String
)

{
}