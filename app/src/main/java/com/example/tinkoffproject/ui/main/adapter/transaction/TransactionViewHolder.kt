package com.example.tinkoffproject.ui.main.adapter.transaction

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.data.dto.to_view.Transaction
import com.example.tinkoffproject.formatDate

class TransactionViewHolder(private val root: View) :
    RecyclerView.ViewHolder(root) {
    private val amount: TextView = root.findViewById(R.id.tv_transaction_amount)
    private val category: TextView = root.findViewById(R.id.tv_transaction_category)
    private val type: TextView = root.findViewById(R.id.tv_transaction_type)
    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)
    private val date: TextView = root.findViewById(R.id.tv_transaction_date)

    fun bind(data: Transaction) {
        category.text = data.category.name

        val typeTextId = if (data.isIncome) R.string.income else R.string.expenses
        type.visibility = View.VISIBLE
        type.text = root.context.getString(typeTextId)

        date.text = formatDate(root.context, data.ts, R.string.date_format_only_time)
        date.visibility = View.VISIBLE


        icon.setImageResource(data.category.resIconId)
        icon.backgroundTintList = ColorStateList.valueOf(data.category.color)

        amount.text = data.amountFormatted

    }

    companion object {
        fun from(parent: ViewGroup, onClick: (Int) -> Unit): TransactionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_transaction, parent, false)
            val viewHolder = TransactionViewHolder(view)
            view.setOnClickListener {
                onClick(viewHolder.adapterPosition)
            }
            return viewHolder
        }
    }
}