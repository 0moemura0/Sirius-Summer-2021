package com.example.tinkoffproject.ui.main

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.adapter.notification.NotificationAdapter
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), UpdatableToolBar,
    UpdatableNotifications {
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
    private val toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val notification by lazy {
        findViewById<RecyclerView>(R.id.rv_notification)
    }

    private val notificationAdapter by lazy {
        NotificationAdapter()
    }

    override fun onStart() {
        super.onStart()
        btnSetting.setOnClickListener {
            Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
        }
        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnClose.setOnClickListener {
            navController?.popBackStack()
        }
        setupNotificationRecycler()

        //showNotification(NotificationType.INTERNET_PROBLEM_ERROR)
    }

    private fun setupNotificationRecycler() {
        val manager = LinearLayoutManager(this)

        notification.adapter = notificationAdapter
        notification.layoutManager = manager

        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                notificationAdapter.deleteItem(position)
            }
        })
        helper.attachToRecyclerView(notification)
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        val isMainPage = navController?.currentDestination?.id == R.id.walletsList
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

        if (type != ToolbarType.INVISIBLE) {
            toolbar.visibility = View.VISIBLE
            toolbarTitle.text = title
            toolbarTitle.visibility = if (type.isTitleVisible) View.VISIBLE else View.INVISIBLE
            btnSetting.visibility = if (type.isSettingsVisible) View.VISIBLE else View.INVISIBLE
            btnBack.visibility = if (type.isBackVisible) View.VISIBLE else View.INVISIBLE
            btnClose.visibility = if (type.isCloseVisible) View.VISIBLE else View.INVISIBLE
        } else {
            toolbar.visibility = View.GONE
        }
    }

    override fun showNotification(type: NotificationType) {
        notificationAdapter.addNotification(type)
    }
}