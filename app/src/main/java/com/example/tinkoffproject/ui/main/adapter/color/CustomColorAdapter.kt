package com.example.tinkoffproject.ui.main.adapter.color

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.ui.main.data.OnItemSelectListener

class CustomColorAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemSelectListener

    private val data = mutableListOf<COLOR>()

    fun setData(_data: List<COLOR>) {
        data.clear()
        data.addAll(_data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ColorViewHolder.from(parent)

        holder.itemView.setOnClickListener {
            onItemSelect(holder.adapterPosition)
        }

        return holder
    }

    private fun onItemSelect(position: Int) {
        listener.onItemSelect(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ColorViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size


    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }
}