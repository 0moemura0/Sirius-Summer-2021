package com.example.tinkoffproject.view.adapter.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.data.SelectableCategory
import com.google.android.material.switchmaterial.SwitchMaterial

class CurrencyViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {

    private val textView: TextView = root.findViewById(R.id.tv_name)
    private val checked: SwitchMaterial = root.findViewById(R.id.iv_checked)

    fun bind(i: SelectableCategory) {
        //TODO bind
//        textView.setImageResource(i.category.resIconId)
//        checked.backgroundTintList = ColorStateList.valueOf(i.category.color)
//
//        textView.text = i.category.name
//        checked.visibility = if (i.isChecked) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        fun from(parent: ViewGroup): CurrencyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_text_toggle, parent, false)
            view.setOnClickListener {
                check()
            }
            return CurrencyViewHolder(view)
        }

        private fun check() {

        }
    }
}