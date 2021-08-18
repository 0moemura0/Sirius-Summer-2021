package com.example.tinkoffproject.view.carddetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.main_fragment_container)
    }
    private val navController by lazy { navHostFragment?.findNavController() }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)

        val btnSetting: ImageView = findViewById(R.id.iv_settings)
        val btnBack: ImageView = findViewById(R.id.iv_back)
        btnSetting.setOnClickListener {
            Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
        }
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        val isMainPage = navController?.backStack?.size ?: 2 == 2
        if (!Timer.isRunning() && isMainPage) {
            Timer.start()
            Toast.makeText(this, getString(R.string.touch_again_for_exit), Toast.LENGTH_SHORT)
                .show()
        } else {
            super.onBackPressed()
        }
    }

    object Timer : CountDownTimer(3000, 100) {
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