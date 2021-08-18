package com.example.tinkoffproject.model.adapter.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.dto.Transaction
import com.example.tinkoffproject.model.dto.TransactionCategory
import com.example.tinkoffproject.model.dto.TransactionType
import com.example.tinkoffproject.model.utils.getDate

class NoTransactionsViewHolder (private val root: View) : RecyclerView.ViewHolder(root) {

    companion object {
        fun from(parent: ViewGroup): NoTransactionsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_no_transaction, parent, false)
            return NoTransactionsViewHolder(view)
        }
    }
}