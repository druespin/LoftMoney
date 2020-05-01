package com.example.loftmoney


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class WelcomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val sharedPrefs = getSharedPreferences(
            getString(R.string.appl_name), Context.MODE_PRIVATE)

        val authToken = sharedPrefs.getString(AUTH_TOKEN_KEY, "")

        if (authToken!!.isEmpty()) {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        else {
            startActivity(Intent(this, MainActivity::class.java))
        }

        finish()
    }

}