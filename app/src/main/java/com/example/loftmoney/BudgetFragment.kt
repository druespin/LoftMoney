package com.example.loftmoney

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loftmoney.adapter.ItemsAdapter
import com.example.loftmoney.web.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_budget.view.*

const val LOFT_TAG = "LOFT"
const val EXTRA_KEY = "extra key"
const val ADD_EXPENSE_ITEM = "expense"
const val ADD_INCOME_ITEM = "income"


class BudgetFragment : Fragment() {

    private val adapter = ItemsAdapter()
    private val disposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        )
        recyclerView.adapter = adapter

        /**
         *  Get data from server based on the current fragment
         */
        arguments?.takeIf { it.containsKey(FRAGMENT_KEY) }?.apply {

            loadItems()
        }

        /**
         *  Click Add New Item
         */
        view.btn_fab_main.setOnClickListener {
            val intent = Intent(activity, AddItemActivity::class.java)

            when (arguments?.get(FRAGMENT_KEY)) {
                0 -> intent.putExtra(EXTRA_KEY, ADD_EXPENSE_ITEM)
                1 -> intent.putExtra(EXTRA_KEY, ADD_INCOME_ITEM)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadItems()
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }


    private fun loadItems() {
        when (arguments?.get(FRAGMENT_KEY)) {
            0 -> loadItemsFromServer("expense")
            1 -> loadItemsFromServer("income")
        }
    }

    private fun loadItemsFromServer(type: String) {
        val chargeList = ArrayList<ChargeModel>()
        val responseFromApi = ApiService.createApiService.getItems(type)

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

