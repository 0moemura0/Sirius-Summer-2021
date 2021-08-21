package com.example.tinkoffproject.view.adapter.category

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.view.data.OnItemSelectListener
import com.example.tinkoffproject.view.data.SelectableCategory

class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemSelectListener

    private val data = mutableListOf<SelectableCategory>()
    private var currentSelected: Int = 0

    fun setData(_data: List<SelectableCategory>) {
        data.clear()
        data.addAll(_data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = CategoryViewHolder.from(parent)

        holder.itemView.setOnClickListener {
            onItemSelect(holder.adapterPosition)
        }
        return holder
    }

    private fun onItemSelect(position: Int) {
        data[currentSelected].isChecked = false
        notifyItemChanged(currentSelected)

        currentSelected = position
        data[currentSelected].isChecked = true
        notifyItemChanged(currentSelected)
        listener.onItemSelect(currentSelected)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }
}
