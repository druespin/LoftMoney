package com.example.loftmoney


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loftmoney.web.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_auth.*


const val AUTH_TOKEN_KEY = "auth-token-key"

class AuthActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btn_auth.setOnClickListener {

            if (userId.isNotEmpty()) {
                doSignIn(userId)
            }
        }

        login_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                userId = s.toString()

                btn_auth.isEnabled = login_input.text.isNotEmpty()

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


    private fun doSignIn(userId: String) {
        btn_auth.visibility = INVISIBLE
        val getAuthRequest = ApiService.createApiService.getTokenForUser(userId)

        disposable.add(getAuthRequest
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                response ->
                if (response.status == "success") {

                    getSharedPreferences(getString(R.string.appl_name), Context.MODE_PRIVATE)
                        .edit()
                        .putString(AUTH_TOKEN_KEY, response.authToken)
                        .apply()

                    Log.e("TOKEN: ", response.authToken)

                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, R.anim.fade_out)
                }
                else {
                    Log.e("STATUS: ", response.status)
                    btn_auth.visibility = VISIBLE
                }
            },
                {
                    btn_auth.visibility = VISIBLE
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            ))
    }
}