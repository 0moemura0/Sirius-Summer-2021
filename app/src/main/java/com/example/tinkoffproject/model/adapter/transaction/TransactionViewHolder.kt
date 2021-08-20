package com.example.tinkoffproject.model.adapter.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.utils.getDate

class TransactionViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
    private val amount: TextView = root.findViewById(R.id.tv_transaction_amount)
    private val category: TextView = root.findViewById(R.id.tv_transaction_category)
    private val type: TextView = root.findViewById(R.id.tv_transaction_type)
    private val icon: ImageView = root.findViewById(R.id.iv_transaction_icon)
    private val date: TextView = root.findViewById(R.id.tv_transaction_date)

    fun bind(data: Transaction) {
        category.text = data.category.name

        val typeTextId = if (data.isIncome) R.string.income else R.string.expenses
        type.text = root.context.getString(typeTextId)
        icon.setBackgroundResource(data.category.resIconId)

        amount.text = data.amount
        date.text = getDate(data.date, "hh:mm")
    }

    companion object {
        fun from(parent: ViewGroup): TransactionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_transaction, parent, false)
            return TransactionViewHolder(view)
        }
    }
}