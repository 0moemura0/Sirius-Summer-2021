package com.example.tinkoffproject.ui.main.adapter.category_custom

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.ui.main.data.OnItemSelectListener

class CustomCategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemSelectListener

    private var currentColor = Color.parseColor("#5833EE")

    private val data = mutableListOf<Int>()

    fun setData(_data: List<Int>) {
        data.clear()
        data.addAll(_data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = CustomCategoryViewHolder.from(parent)

        holder.itemView.setOnClickListener {
            onItemSelect(holder.adapterPosition)
        }

        return holder
    }

    private fun onItemSelect(position: Int) {
        listener.onItemSelect(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomCategoryViewHolder).bind(data[position], currentColor)
    }

    override fun getItemCount(): Int = data.size

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }

    fun setCurrentColor(color: Int) {
        currentColor = color
        notifyDataSetChanged()
    }
}