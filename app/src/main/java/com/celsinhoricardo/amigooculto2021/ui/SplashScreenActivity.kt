package com.celsinhoricardo.amigooculto2021.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.celsinhoricardo.amigooculto2021.R
import com.celsinhoricardo.amigooculto2021.ui.main.MainActivity


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val delayedHandler = Handler()
        delayedHandler.postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 3000)

    }
}