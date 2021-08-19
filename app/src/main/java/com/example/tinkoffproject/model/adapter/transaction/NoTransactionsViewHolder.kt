package com.example.tinkoffproject.model.adapter.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R

class NoTransactionsViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {

    companion object {
        fun from(parent: ViewGroup): NoTransactionsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_no_transaction, parent, false)
            return NoTransactionsViewHolder(view)
        }
    }
}