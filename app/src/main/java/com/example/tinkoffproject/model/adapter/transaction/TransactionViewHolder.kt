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

class TransactionViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
    private val amount: TextView = root.findViewById(R.id.tv_transaction_amount)
    private val category: TextView = root.findViewById(R.id.tv_transaction_category)
    private val type: TextView = root.findViewById(R.id.tv_transaction_type)
    private val icon: ImageView = root.findViewById(R.id.iv_transaction_icon)
    private val date: TextView = root.findViewById(R.id.tv_transaction_date)

    fun bind(data: Transaction) {
        val categoryTextId = when (data.category) {
            TransactionCategory.INCOME_GIFT -> R.string.gift
            TransactionCategory.INCOME_PERCENTS -> R.string.percents
            TransactionCategory.INCOME_SALARY -> R.string.salary
            TransactionCategory.INCOME_PART_TIME_JOB -> R.string.part_time_job
            TransactionCategory.EXPENSES_SUPERMARKET -> R.string.tools_groceries
        }
        category.text = root.context.getString(categoryTextId)

        val backgroundId: Int
        val typeTextId: Int
        when (data.type) {
            TransactionType.EXPENSES -> {
                backgroundId = R.drawable.indicator_dot_green
                typeTextId = R.string.expenses
            }
            TransactionType.INCOME -> {
                backgroundId = R.drawable.indicator_dot_red
                typeTextId = R.string.expenses
            }
        }
        type.text = root.context.getString(typeTextId)
        icon.setBackgroundResource(backgroundId)

        amount.text = data.amount.toString()
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