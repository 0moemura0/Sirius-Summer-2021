package com.example.tinkoffproject.view.adapter.category_custom

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R

class CustomCategoryViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)

    fun bind(i: Int, color: Int) {
        icon.setImageResource(i)
        icon.backgroundTintList = ColorStateList.valueOf(color)
    }

    companion object {
        fun from(parent: ViewGroup): CustomCategoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.category_icon, parent, false)
            return CustomCategoryViewHolder(view)
        }
    }
}