package com.example.tinkoffproject.view.carddetails

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R

class MainActivity : AppCompatActivity(R.layout.activity_main), UpdatableToolBar {
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.main_fragment_container)
    }
    private val navController by lazy {
        navHostFragment?.findNavController()
    }

    private val timer: Timer by lazy { Timer() }

    private val btnSetting by lazy {
        findViewById<View>(R.id.iv_settings)
    }
    private val btnBack by lazy {
        findViewById<View>(R.id.iv_back)
    }
    private val btnClose by lazy {
        findViewById<View>(R.id.iv_close)
    }
    private val toolbarTitle by lazy {
        findViewById<TextView>(R.id.title)
    }

    override fun onStart() {
        super.onStart()
        btnSetting.setOnClickListener {
            Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
        }
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        val isMainPage = navController?.currentDestination?.id == R.id.cardDetails
        if (!timer.isRunning() && isMainPage) {
            timer.start()
            Toast.makeText(this, getString(R.string.touch_again_for_exit), Toast.LENGTH_SHORT)
                .show()

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

    override fun updateToolbar(title: String, type: ToolbarType) {
        toolbarTitle.text = title
        toolbarTitle.visibility = if (type.isTitleVisible) View.VISIBLE else View.INVISIBLE
        btnSetting.visibility = if (type.isSettingsVisible) View.VISIBLE else View.INVISIBLE
        btnBack.visibility = if (type.isBackVisible) View.VISIBLE else View.INVISIBLE
        btnClose.visibility = if (type.isCloseVisible) View.VISIBLE else View.INVISIBLE
    }

}