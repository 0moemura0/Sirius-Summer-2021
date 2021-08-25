package com.example.tinkoffproject.view.adapter.category_custom

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.model.data.mapper.COLOR
import com.example.tinkoffproject.view.data.OnItemSelectListener
import com.example.tinkoffproject.view.data.SelectableIconCustomCategory

class CustomCategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemSelectListener

    private var currentColor = COLOR.BLUE_MAIN

    private val data = mutableListOf<SelectableIconCustomCategory>()
    private var currentSelectedPosition: Int = DEFAULT_VALUE

    companion object {
        const val DEFAULT_VALUE = -1
    }

    fun setData(_data: List<Int>) {

        data.clear()
        data.addAll(_data.map { SelectableIconCustomCategory(it) })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = CustomCategoryViewHolder.from(parent)

        holder.itemView.setOnClickListener {
            onItemSelect(holder.adapterPosition)
        }

        return holder
    }

    fun onItemSelect(position: Int, doNotify: Boolean = true) {
        if (currentSelectedPosition == position) {
            data[currentSelectedPosition].isChecked = !data[currentSelectedPosition].isChecked
            notifyItemChanged(currentSelectedPosition)
        } else {
            if (currentSelectedPosition != DEFAULT_VALUE) {
                data[currentSelectedPosition].isChecked = false
                notifyItemChanged(currentSelectedPosition)
            }

            currentSelectedPosition = position
            data[currentSelectedPosition].isChecked = true
            notifyItemChanged(currentSelectedPosition)
        }
        if (doNotify)
            listener.onItemSelect(currentSelectedPosition)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomCategoryViewHolder).bind(data[position], currentColor)
    }

    override fun getItemCount(): Int = data.size

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }

    fun setCurrentColor(color: COLOR) {
        currentColor = color
        notifyDataSetChanged()
    }
}