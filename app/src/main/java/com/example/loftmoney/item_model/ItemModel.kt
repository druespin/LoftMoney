package com.example.loftmoney.item_model

import android.os.Parcelable
import com.example.loftmoney.web.DataArray
import kotlinx.android.parcel.Parcelize


@Parcelize
class ItemModel
    (val chargeName: String,
     val chargePrice: String) : Parcelable
{
    constructor(dataItem: DataArray): this(
                dataItem.name,
                dataItem.price.toString()
    )
}
