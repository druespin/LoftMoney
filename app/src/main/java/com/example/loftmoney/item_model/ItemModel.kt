package com.example.loftmoney.item_model

import android.os.Parcelable
import com.example.loftmoney.web.DataArray
import kotlinx.android.parcel.Parcelize


@Parcelize
class ItemModel
    (val dataId: Int,
     val itemName: String,
     val itemPrice: String) : Parcelable
{
    constructor(dataItem: DataArray): this(
                dataItem.itemId,
                dataItem.name,
                dataItem.price.toString()
    )
}

