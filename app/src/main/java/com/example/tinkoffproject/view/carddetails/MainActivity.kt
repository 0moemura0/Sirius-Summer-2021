package com.example.tinkoffproject.view.carddetails

import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.tinkoffproject.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, CardDetailsFragment())
            .commit()

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnSetting: View = toolbar.findViewById(R.id.iv_settings)
        val btnBack: View = toolbar.findViewById(R.id.iv_back)
        btnSetting.setOnClickListener {
            Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
        }
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (!timer.isRunning()) {
            timer.start()
            Toast.makeText(this, getString(R.string.touch_again_for_exit), Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    class Timer : CountDownTimer(3000, 100) {
        private var isBackAvailable = false
        override fun onTick(millisUntilFinished: Long) {
            isBackAvailable = true
        }

        override fun onFinish() {
            isBackAvailable = false
        }

        fun isRunning() = isBackAvailable
    }

}