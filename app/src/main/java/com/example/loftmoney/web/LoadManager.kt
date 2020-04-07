package com.example.loftmoney.web

import android.util.Log
import com.example.loftmoney.ChargeModel
import com.example.loftmoney.adapter.ItemsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoadManager(val disposable: CompositeDisposable, val adapter: ItemsAdapter) {

    fun loadItemsFromServer(type: String) {

        val chargeList = ArrayList<ChargeModel>()
        val responseFromApi = ApiService.getRequest.getItems(type)

        disposable.add(responseFromApi
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.status == "success") {
                        val dataList = response.data
                        for (dataItem in dataList) {
                            chargeList.add(ChargeModel(dataItem))
                        }

                        adapter.setNewData(chargeList)

                    } else {
                        Log.e("ERROR: ", response.status)
                    }
                },
                { error("NO RESPONSE") }
            )
        )
    }
}