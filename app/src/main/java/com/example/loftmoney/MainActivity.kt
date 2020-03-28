package com.example.loftmoney

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loftmoney.adapter.ItemsAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val adapter = ItemsAdapter()
    private val ADD_ITEM_REQUEST: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // items.add(ChargeModel("food","900"))

        recyclerView.adapter = adapter

        // Invoke Add new item activity
        fab_main.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, ADD_ITEM_REQUEST)

            Log.i("LOGS: ", "add item request")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val extraKey = "get item"
        if (requestCode == ADD_ITEM_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null) {

            val getNewItem = data.getSerializableExtra(extraKey)
            adapter.addItem(getNewItem as ChargeModel)
        }
    }
}
