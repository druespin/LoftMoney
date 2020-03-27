package com.example.loftmoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loftmoney.adapter.ItemsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val items = ArrayList<ChargeModel>()
        items.add(ChargeModel("food","900"))

        val adapter = ItemsAdapter(items)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab_main)
            .setOnClickListener {
                val intent: Intent = (applicationContext, AddItemActivity.class)

        }

    }
}
