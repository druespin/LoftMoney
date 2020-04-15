package com.example.loftmoney.web

data class ResponseItem(
    val status: String,
    val data: List<DataArray>
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