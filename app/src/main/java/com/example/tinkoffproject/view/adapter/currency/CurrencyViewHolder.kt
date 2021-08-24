package com.example.tinkoffproject.view.adapter.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.data.SelectableCategory
import com.example.tinkoffproject.view.data.SelectableCurrency
import com.google.android.material.switchmaterial.SwitchMaterial

class CurrencyViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {

    val textView: TextView = root.findViewById(R.id.tv_name)
    val checked: SwitchMaterial = root.findViewById(R.id.swth)

    fun bind(i: SelectableCurrency) {
        textView.text = "${i.currency.longName} (${i.currency.shortName})"
        checked.isChecked = i.isChecked

    }

    companion object {
        fun from(parent: ViewGroup): CurrencyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_text_toggle, parent, false)
            return CurrencyViewHolder(view)
        }
    }
}