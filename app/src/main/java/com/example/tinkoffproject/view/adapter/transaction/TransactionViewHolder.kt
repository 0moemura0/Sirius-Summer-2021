package com.example.tinkoffproject.view.adapter.transaction

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.utils.formatDate
import com.example.tinkoffproject.model.utils.formatMoney

class TransactionViewHolder(private val root: View, private val currency: Currency) :
    RecyclerView.ViewHolder(root) {
    private val amount: TextView = root.findViewById(R.id.tv_transaction_amount)
    private val category: TextView = root.findViewById(R.id.tv_transaction_category)
    private val type: TextView = root.findViewById(R.id.tv_transaction_type)
    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)
    private val date: TextView = root.findViewById(R.id.tv_transaction_date)

    fun bind(data: Transaction) {
        category.text = data.category.name

        val typeTextId = if (data.isIncome) R.string.income else R.string.expenses
        type.text = root.context.getString(typeTextId)

        icon.setImageResource(data.category.resIconId)
        icon.backgroundTintList = ColorStateList.valueOf(data.category.color)

        amount.text = data.amountFormatted

        date.text = formatDate(root.context, data.date, R.string.date_format_only_time)
    }

    companion object {
        fun from(parent: ViewGroup, currency: Currency): TransactionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_transaction, parent, false)
            return TransactionViewHolder(view, currency)
        }
    }
}