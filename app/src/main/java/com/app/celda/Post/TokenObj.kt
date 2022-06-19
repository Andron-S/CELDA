package com.app.celda.Post

import com.chibatching.kotpref.KotprefModel

object TokenObj: KotprefModel() {
    var accessToken by nullableStringPref()
    var refreshToken by nullableStringPref()
}