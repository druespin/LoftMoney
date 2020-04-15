package com.example.loftmoney

import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.loftmoney.web.ApiService.Companion.createApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_item.*


class AddItemActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    private var mName: String? = null
    private var mPrice: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        item_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mName = s.toString()
                checkIfHasText()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        item_price.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mPrice = s.toString()
                checkIfHasText()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        val reqType = intent.getStringExtra(EXTRA_KEY)

        when (reqType) {
            ADD_EXPENSE_ITEM -> {
                item_name.setTextColor(resources.getColor(R.color.expense_text_color, theme))
                item_price.setTextColor(resources.getColor(R.color.expense_text_color, theme))
            }
            ADD_INCOME_ITEM -> {
                item_name.setTextColor(resources.getColor(R.color.income_text_color, theme))
                item_price.setTextColor(resources.getColor(R.color.income_text_color, theme))
            }
        }

        // ADD item button listener
        btn_add_submit.setOnClickListener{

            onLoadingData(true)

            if (mName.isNullOrEmpty() || mPrice.isNullOrEmpty()) {
                Toast.makeText(this, "Both fields should be populated", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send new item to server
            postAddedItemToServer(mName.toString(),
                Integer.parseInt(mPrice.toString()),
                reqType)

            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun postAddedItemToServer(name: String, price: Int, type: String) {

        val authToken = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
            .getString(AUTH_TOKEN_KEY, "no-token-received")

        val responseFromApi = createApiService.postItem(price, name, type, authToken)

            disposable.add(responseFromApi
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Toast.makeText(this, "Item added successfully",
                        Toast.LENGTH_SHORT)
                        .show()
                },
                    {
                        Toast.makeText(this, "Transaction failed",
                            Toast.LENGTH_SHORT)
                            .show()

                        onLoadingData(false)
                    })
            )
    }

    private fun checkIfHasText() {
        btn_add_submit.isEnabled = !mName.isNullOrEmpty() && !mPrice.isNullOrEmpty()
    }

    private fun onLoadingData(state: Boolean) {
        item_name.isEnabled = !state
        item_price.isEnabled = !state
        btn_add_submit.isEnabled = !state
    }
}

