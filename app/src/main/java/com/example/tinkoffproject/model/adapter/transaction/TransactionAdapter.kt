package com.example.tinkoffproject.model.adapter.transaction

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.model.dto.Transaction

class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    TransactionAdapterCallback {
    val data = mutableListOf<Transaction>()

    companion object {
        const val TYPE_TRANSACTION = 0
        const val TYPE_NO_TRANSACTION = 1
    }

    fun setData(new: List<Transaction>) {
        val oldSize = itemCount
        data.clear()
        data.addAll(new)
        Log.d("kek", "$data")
        notifyItemRangeRemoved(0, oldSize)
        notifyItemRangeInserted(0, new.size)
    }

    override fun getItemId(position: Int): Long {
        return data.getOrNull(0)?.id ?: -1L
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TRANSACTION -> TransactionViewHolder.from(parent)
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

    override fun removed(from: Int) {
        data.removeAt(from)
        notifyItemRemoved(from)
    }

    override fun added(to: Int, new: Transaction) {
        TODO("Not yet implemented")
    }

    override fun rangeRemoved(from: Int, to: Int) {
        TODO("Not yet implemented")
    }

    override fun rangeAdded(to: Int, new: List<Transaction>) {
        data.addAll(to, new)
        notifyItemRangeChanged(to, new.size)
    }

}