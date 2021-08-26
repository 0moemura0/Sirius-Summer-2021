package com.example.tinkoffproject.ui.main.adapter.notification

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.NotificationType

class NotificationViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val icon: ImageView = root.findViewById(R.id.iv_icon)
    private val description: TextView = root.findViewById(R.id.tv_description)

    fun bind(i: NotificationType) {
        Log.d("kek", "bind")
        icon.setImageResource(i.iconResId)
        description.setText(i.textResId)
    }

    companion object {
        fun from(parent: ViewGroup): NotificationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.error_notification, parent, false)
            return NotificationViewHolder(view)
        }
    }
}