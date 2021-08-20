package com.example.tinkoffproject.view.adapter.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.data.SelectableCategory

class CategoryViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)
    private val textView: TextView = root.findViewById(R.id.tv_category_name)
    private val checked: ImageView = root.findViewById(R.id.iv_checked)

    fun bind(i: SelectableCategory) {
        icon.setImageResource(i.category.resIconId)
        textView.text = i.category.name
        checked.visibility = if (i.isChecked) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        fun from(parent: ViewGroup): CategoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_category, parent, false)
            return CategoryViewHolder(view)
        }
    }
}
