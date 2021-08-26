package com.example.tinkoffproject.ui.main.adapter.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.data.SelectableString


class CalendarViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val dayOfMonth: TextView = root.findViewById(R.id.cellDayText)

    fun bind(i: SelectableString) {
        dayOfMonth.text = i.str
        if (i.isChecked) {
            dayOfMonth.setBackgroundResource(R.color.blue_main)
            dayOfMonth.setTextColor(ContextCompat.getColor(dayOfMonth.context, R.color.white))
        } else {
            dayOfMonth.background = null
            dayOfMonth.setTextColor(ContextCompat.getColor(dayOfMonth.context, R.color.black))
        }
    }

    companion object {
        fun from(parent: ViewGroup): CalendarViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_calendar, parent, false)
            return CalendarViewHolder(view)
        }
    }
}
