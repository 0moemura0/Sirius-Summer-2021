package com.example.tinkoffproject.ui.main.adapter.category_custom

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.ui.main.data.OnItemSelectListener
import com.example.tinkoffproject.ui.main.data.SelectableIconCustomCategory
import com.example.tinkoffproject.utils.COLOR

class CustomCategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemSelectListener

    private var currentColor = COLOR.BLUE_MAIN

    val data = mutableListOf<SelectableIconCustomCategory>()
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
            notifyItemChanged(currentSelectedPosition, data[currentSelectedPosition].isChecked)
        } else {
            if (currentSelectedPosition != DEFAULT_VALUE) {
                data[currentSelectedPosition].isChecked = false
                notifyItemChanged(currentSelectedPosition, data[currentSelectedPosition].isChecked)
            }

            currentSelectedPosition = position
            data[currentSelectedPosition].isChecked = true
            notifyItemChanged(currentSelectedPosition, data[currentSelectedPosition].isChecked)
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