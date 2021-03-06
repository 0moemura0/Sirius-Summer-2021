package com.example.tinkoffproject.ui.main.adapter.currency

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.ui.main.data.OnItemSelectListener
import com.example.tinkoffproject.ui.main.data.SelectableCurrency

class CurrencyAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemSelectListener

    private val data = mutableListOf<SelectableCurrency>()
    private var currentSelected: Int = 0

    fun setData(_data: List<Currency>) {
        data.clear()
        data.addAll(_data.map { SelectableCurrency(it) })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = CurrencyViewHolder.from(parent)

        holder.checked.setOnClickListener {
            onItemSelect(holder.adapterPosition)
        }
        return holder
    }

    fun onItemSelect(position: Int) {
        if (position == currentSelected) {
            data[currentSelected].isChecked = !data[currentSelected].isChecked
            notifyItemChanged(currentSelected)
            listener.onItemSelect(currentSelected)
        } else {
            if (currentSelected < data.size) {
                data[currentSelected].isChecked = false
                notifyItemChanged(currentSelected)
            }
            currentSelected = position
            data[currentSelected].isChecked = true
            notifyItemChanged(currentSelected)
            listener.onItemSelect(currentSelected)
        }
    }

    fun isItemSelected(position: Int) = data[position].isChecked

    fun updateData(_data: List<Currency>) {
        val oldSize = data.size
        data.addAll(_data.map { SelectableCurrency(it) })
        notifyItemRangeInserted(oldSize, data.size)
    }

    fun removeData(count: Int) {
        val newSize = data.size - count
        data.removeAll(data.subList(newSize, data.size))
        notifyItemRangeRemoved(newSize, count)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CurrencyViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }

}