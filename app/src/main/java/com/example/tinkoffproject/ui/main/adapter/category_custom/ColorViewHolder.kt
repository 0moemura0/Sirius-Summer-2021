package com.example.tinkoffproject.ui.main.adapter.category_custom

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R

class ColorViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)

    fun bind(i: Int) {
        icon.backgroundTintList =
            ColorStateList.valueOf(i)
    }

    companion object {
        fun from(parent: ViewGroup): ColorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_category, parent, false)
            return ColorViewHolder(view)
        }
    }
}