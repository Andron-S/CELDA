package com.app.celda.Model

import com.google.gson.annotations.SerializedName

class ProfileModel(
    @SerializedName("username")
    var name: String,
    @SerializedName("user")
    var user_id: Int,
    @SerializedName("id")
    var profile_id: String,
    @SerializedName("avatar")
    var avatar_profile: String,
    @SerializedName("about_me")
    var about: String,
    @SerializedName("is_verify")
    var verify: String
){
}