package com.example.loftmoney

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class ChargeModel
    (val chargeName: String,
     val chargePrice: String) : Parcelable
