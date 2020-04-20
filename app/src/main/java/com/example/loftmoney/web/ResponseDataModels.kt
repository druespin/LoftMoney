package com.example.loftmoney.web

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val status: String,
    @SerializedName("id") val userId: Int,
    @SerializedName("auth_token") val authToken: String
)

data class DataArray (
    @SerializedName("id") val itemId: Int,
    val name: String,
    val price: Int,
    val type: String,
    val date: String
)

data class Status(val status: String)  // for auth request

data class StatusAndItemId(val status: String, val itemId: Int)  // for remove request

data class Balance(
    val status: String,
    val balance: Int,
    @SerializedName("total_income") val totalIncome: String,
    @SerializedName("total_expenses") val totalExpense: String
)