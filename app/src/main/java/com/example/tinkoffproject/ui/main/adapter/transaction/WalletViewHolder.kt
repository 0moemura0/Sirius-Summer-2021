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

class WalletViewHolder(root: View) :
    RecyclerView.ViewHolder(root) {
    private val amount: TextView = root.findViewById(R.id.tv_transaction_amount)
    private val category: TextView = root.findViewById(R.id.tv_transaction_category)
    private val icon: ImageView = root.findViewById(R.id.iv_category_icon)

    fun bind(data: Transaction) {
        category.text = data.category.name

        icon.setImageResource(data.category.resIconId)
        icon.backgroundTintList = ColorStateList.valueOf(data.category.color)

        amount.text = data.amountFormatted

    }

    companion object {
        fun from(parent: ViewGroup, onClick: (Int) -> Unit): WalletViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_wallet, parent, false)
            val viewHolder = WalletViewHolder(view)
            view.setOnClickListener {
                onClick(viewHolder.adapterPosition)
            }
            return viewHolder
        }
    }
}
