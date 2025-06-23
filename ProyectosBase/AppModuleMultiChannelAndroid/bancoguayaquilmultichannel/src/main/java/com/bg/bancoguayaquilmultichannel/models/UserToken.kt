package com.bg.bancoguayaquilmultichannel.models

import com.google.gson.annotations.SerializedName

data class UserToken(
    @SerializedName("AccessToken")
    val accessToken: String,

    @SerializedName("RefreshToken")
    val refreshToken: String,

    @SerializedName("ExpiresIn")
    val expiresIn: Int,

    @SerializedName("ExpiresAt")
    var expiresAt: Double
)
