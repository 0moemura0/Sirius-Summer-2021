package com.example.tinkoffproject.ui.main.adapter.calendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.ui.main.data.OnItemSelectListener


class CalendarAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemSelectListener

    private val data = mutableListOf<SelectableString>()
    private var currentSelectedPosition: Int = 0

    fun setData(_data: List<SelectableString>) {
        data.clear()
        data.addAll(_data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = CalendarViewHolder.from(parent)

        holder.itemView.setOnClickListener {
            onItemSelect(holder.adapterPosition)
        }
        return holder
    }

    private fun onItemSelect(position: Int) {
        if (data[position].str.isNotEmpty()) {
            if (currentSelectedPosition == position) {
                data[currentSelectedPosition].isChecked = !data[currentSelectedPosition].isChecked
                notifyItemChanged(currentSelectedPosition)
                listener.onItemSelect(currentSelectedPosition)
            } else {
                data[currentSelectedPosition].isChecked = false
                notifyItemChanged(currentSelectedPosition)

                currentSelectedPosition = position
                data[currentSelectedPosition].isChecked = true
                notifyItemChanged(currentSelectedPosition)
                listener.onItemSelect(currentSelectedPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CalendarViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }
}