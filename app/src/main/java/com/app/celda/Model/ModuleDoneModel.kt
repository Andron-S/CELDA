package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class ModuleDoneModel(
    @SerializedName("module")
    var module_id: Int,
    @SerializedName("profile")
    var profile_id: Int,
    @SerializedName("is_module_done")
    var is_module_done: String
)
{
}