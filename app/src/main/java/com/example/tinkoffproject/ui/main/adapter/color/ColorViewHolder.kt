package com.example.tinkoffproject.ui.main.adapter.color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.utils.COLOR

class ColorViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)

    fun bind(i: COLOR) {
        icon.backgroundTintList = ContextCompat.getColorStateList(icon.context, i.color)
    }

    companion object {
        fun from(parent: ViewGroup): ColorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.category_icon, parent, false)
            return ColorViewHolder(view)
        }
    }
}