package com.example.loftmoney

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.loftmoney.web.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_auth.*


const val AUTH_TOKEN_KEY = "auth-token-key"

class AuthActivity : Activity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btn_auth.setOnClickListener {

            doSignIn()
        }
    }

    private fun doSignIn() {
        btn_auth.visibility = INVISIBLE
        val getAuthRequest = ApiService.createApiService.getUserToken("drue")

        disposable.add(getAuthRequest
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                response ->
                if (response.status == "success") {

                    getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
                        .edit()
                        .putString(AUTH_TOKEN_KEY, response.authToken)
                        .apply()

                    Log.e("TOKEN: ", response.authToken)

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else {
                    Log.e("STATUS: ", response.status)
                }
            },
                {
//                    btn_auth.visibility = VISIBLE
                    it.localizedMessage
                }
            ))
    }
}