package com.example.loftmoney.web

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val status: String,
    @SerializedName("id") val userId: Int,
    @SerializedName("auth_token") val authToken: String
)

data class DataArray (
    val id: String,
    val name: String,
    val price: Int,
    val type: String,
    val date: String
)

data class Status(val status: String)

data class Balance(
    val status: String,
    val totalExpense: String,
    val totalIncome: String

)