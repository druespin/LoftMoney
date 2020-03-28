package com.example.loftmoney

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_item.*
import java.io.Serializable


class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val itemName = item_name.text.toString()
        val itemPrice = item_price.text.toString()

        btn_add_item.setOnClickListener{

            if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemPrice))   {
                return@setOnClickListener
            }

            val newItem = ChargeModel(itemName, itemPrice)
            val extraKey = "get item"
            val intent: Intent = Intent().apply {
                putExtra(extraKey, newItem as Serializable)
            }
            setResult(Activity.RESULT_OK, intent)

            Log.i("LOGS", "New Item created")
            finish()
        }
    }
}
