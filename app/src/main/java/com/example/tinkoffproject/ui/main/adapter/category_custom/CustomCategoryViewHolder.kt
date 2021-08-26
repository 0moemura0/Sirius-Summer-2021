package com.example.tinkoffproject.ui.main.adapter.category_custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.data.SelectableIconCustomCategory
import com.example.tinkoffproject.utils.COLOR

class CustomCategoryViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)

    fun bind(i: SelectableIconCustomCategory, color: COLOR) {
        icon.setImageResource(i.resId)

        val myColorResId =
            if (i.isChecked) color.colorSelected else color.color
        icon.backgroundTintList = ContextCompat.getColorStateList(icon.context, myColorResId)
    }


    companion object {
        fun from(parent: ViewGroup): CustomCategoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.category_icon, parent, false)
            return CustomCategoryViewHolder(view)
        }
    }
}