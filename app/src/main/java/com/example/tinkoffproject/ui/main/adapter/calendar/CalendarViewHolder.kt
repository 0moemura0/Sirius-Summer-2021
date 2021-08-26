package com.example.tinkoffproject.ui.main.adapter.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R


class CalendarViewHolder(root: View) :
    RecyclerView.ViewHolder(root) {

    private val dayOfMonth: TextView = root.findViewById(R.id.cellDayText)

    fun bind(i: SelectableString) {
        dayOfMonth.text = i.str
        if (i.isChecked)
            dayOfMonth.setBackgroundResource(R.color.blue_main)
        else
            dayOfMonth.background = null
    }

    companion object {
        fun from(parent: ViewGroup): CalendarViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_calendar, parent, false)
            return CalendarViewHolder(view)
        }
    }

}