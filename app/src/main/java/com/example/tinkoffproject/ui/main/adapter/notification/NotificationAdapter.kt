package com.example.tinkoffproject.ui.main.adapter.notification

import android.os.CountDownTimer
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.ui.main.NotificationType

class NotificationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = mutableListOf<NotificationType>()

    companion object {
        const val MS_BEFORE_DELETE = 5000L
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NotificationViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun addNotification(notification: NotificationType) {
        val i = data.size
        data.add(notification)
        notifyItemInserted(i)
        object : CountDownTimer(MS_BEFORE_DELETE, 100) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                deleteItem(notification, this)
            }
        }.start()
    }

    fun deleteItem(notification: NotificationType, timer: CountDownTimer? = null) {
        timer?.cancel()
        val i = data.indexOf(notification)
        data.remove(notification)
        notifyItemRemoved(i)
    }

    fun deleteItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}