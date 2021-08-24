package com.example.tinkoffproject.view.adapter.transaction

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.model.data.dto.Transaction


//TODO Подключить diffutils
class TransactionAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = mutableListOf<Transaction>()

    companion object {
        const val TYPE_TRANSACTION = 0
        const val TYPE_NO_TRANSACTION = 1
    }

    fun setData(new: List<Transaction>) {
        val oldSize = itemCount
        data.clear()
        data.addAll(new)
        notifyItemRangeRemoved(0, oldSize)
        notifyItemRangeInserted(0, new.size)
    }


    override fun getItemId(position: Int): Long {
        return data.getOrNull(0)?.id ?: -1L
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TRANSACTION -> TransactionViewHolder.from(parent, onClick)
            TYPE_NO_TRANSACTION -> NoTransactionsViewHolder.from(parent)
            else -> throw IllegalStateException("view type didn't added to onCreateViewHolder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> holder.bind(data[position])
            is NoTransactionsViewHolder -> {
            }
            else -> throw IllegalStateException("view type didn't added to onBindViewHolder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data.size) {
            0 -> TYPE_NO_TRANSACTION
            else -> TYPE_TRANSACTION
        }
    }

    override fun getItemCount() = if (data.size == 0) 1 else data.size
}