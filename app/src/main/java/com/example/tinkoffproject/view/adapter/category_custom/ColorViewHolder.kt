package com.example.tinkoffproject.view.adapter.category_custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.mapper.COLOR

class ColorViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)

    fun bind(i: COLOR) {
        icon.backgroundTintList = ContextCompat.getColorStateList(icon.context, i.color)
    }

    companion object {
        fun from(parent: ViewGroup): ColorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_category, parent, false)
            return ColorViewHolder(view)
        }
    }
}