package com.example.loftmoney

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.BundleCompat
import com.example.loftmoney.web.ApiService
import com.example.loftmoney.web.ApiService.Companion.createApiService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_item.*


class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val name = item_name.text
        val price = item_price.text

        // Add Item button handler
        btn_add_item.setOnClickListener{

            if (name.isNullOrEmpty() || price.isNullOrEmpty()) {
                Toast.makeText(this, "Both fields should be populated", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val reqType = intent?.getStringExtra(EXTRA_KEY)
            Log.e(LOFT_TAG, reqType)

            /**
             *  Send new item to server
             */
            postNewItemToServer(name.toString(),
                                Integer.parseInt(price.toString()),
                                reqType)

            finish()
            }
        }

    private fun postNewItemToServer(name: String?, price: Int?, type: String?) {
        val disposable = CompositeDisposable()
        val responseFromApi = createApiService.postItem(price, name, type)

            disposable.add(responseFromApi
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                },
                    {
                        Toast.makeText(this, "Transaction failed", Toast.LENGTH_SHORT).show()
                    })
            )
    }
}

