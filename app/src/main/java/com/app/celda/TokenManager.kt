package com.app.celda

import com.app.celda.Post.TokenObj

class TokenManager {

    fun getToken(): String? {
        return TokenObj.accessToken
    }

    fun setToken(token: String) {
        TokenObj.accessToken = token
    }

    fun setRefresh(refreshToken: String) {
        TokenObj.refreshToken = refreshToken
    }

    fun clearTokens() {
        TokenObj.accessToken = null
        TokenObj.refreshToken = null
    }


}