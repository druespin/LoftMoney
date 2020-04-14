package com.example.loftmoney.web

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val status: String,
    @SerializedName("id") val userId: Int,
    @SerializedName("auth_token") val authToken: String
)
