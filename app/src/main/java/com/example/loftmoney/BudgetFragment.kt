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
import com.example.loftmoney.web.ApiService.Companion.getRequest
import com.example.loftmoney.web.LoadManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_budget.view.*

const val LOG_TAG = "LOFT"
const val ADD_ITEM_REQUEST = 11
const val EXTRA_KEY = "extrakey"

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

        val loadManager = LoadManager(disposable, adapter)

        /**
         *  Get data from server based on the current fragment
         */
        arguments?.takeIf { it.containsKey(FRAGMENT_KEY) }?.apply {

            when (arguments?.get(FRAGMENT_KEY)) {
                0 -> loadManager.loadItemsFromServer("expense")
                1 -> loadManager.loadItemsFromServer("income")
            }
        }

        /**
         *  Click Add New Item
         */
        view.btn_fab_main.setOnClickListener {
            val intent = Intent(activity, AddItemActivity::class.java)
            startActivityForResult(intent, ADD_ITEM_REQUEST)
        }
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_ITEM_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null) {

            val getNewItem = data.getParcelableExtra<ChargeModel>(EXTRA_KEY)
            adapter.addItem(getNewItem)
        }
    }
}