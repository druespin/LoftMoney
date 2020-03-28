package com.example.loftmoney

import kotlinx.serialization.Serializable


@Serializable
class ChargeModel (
    var chargeName: String,
    var chargePrice: String
)
