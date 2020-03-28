package com.example.loftmoney

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_item.*


class AddItemActivity : AppCompatActivity() {

    private val logTag = "LOFT"
    private val extraKey = "get item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val name = item_name.text
        val price = item_price.text

        btn_add_item.setOnClickListener{

            if (name.isNullOrEmpty() || price.isNullOrEmpty()) {
                Toast.makeText(this, "Both fields should be filled out",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newItem = ChargeModel(name.toString(), price.toString())

            Intent().apply {
                putExtra(extraKey, newItem)
                setResult(Activity.RESULT_OK, this)
            }

            Log.i(logTag,"New Item created")
            finish()
            }
        }
    }
