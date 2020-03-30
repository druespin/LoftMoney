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
import kotlinx.android.synthetic.main.fragment_budget.view.*

const val LOG_TAG = "LOFT"
const val ADD_ITEM_REQUEST = 11
const val EXTRA_KEY = "extrakey"

class BudgetFragment : Fragment() {

    private val adapter = ItemsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view: View = inflater.inflate(R.layout.fragment_budget, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        val recyclerView : RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        recyclerView.addItemDecoration(
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        )

        recyclerView.adapter = adapter

        // Click Add new item activity
        view.btn_fab_main.setOnClickListener {
            val intent = Intent(activity, AddItemActivity::class.java)
            startActivityForResult(intent, ADD_ITEM_REQUEST)

            Log.i(LOG_TAG, "Add Item Request")
        }
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