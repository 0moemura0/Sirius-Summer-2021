package com.example.tinkoffproject.view.dialog

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.utils.formatDate
import com.example.tinkoffproject.view.adapter.calendar.CalendarAdapter
import com.example.tinkoffproject.view.adapter.calendar.SelectableString
import com.example.tinkoffproject.view.data.OnItemSelectListener
import java.util.*
import kotlin.collections.ArrayList


class ChooseDatePickerFragment : DialogFragment(R.layout.dialog_choose_date) {
    private var listener: OnItemSelectListener? = null

    companion object {
        const val TAG = "ChooseDatePickerFragment"
    }


    //var selectedDate = LocalDate.now()!!
    var selectedDate = GregorianCalendar.getInstance()
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var monthYearText: TextView
    private lateinit var next: ImageView
    private lateinit var back: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText = view.findViewById(R.id.monthYearTV)
        next = view.findViewById(R.id.iv_next)
        back = view.findViewById(R.id.iv_back)

        next.setOnClickListener {
            nextMonthAction()
        }

        back.setOnClickListener {
            previousMonthAction()
        }
        monthYearText.text = monthYearFromDate(selectedDate)
        setMonthView()

    }

    private fun daysInMonthArray(date: Calendar): ArrayList<String> {
        //TODO
        val daysInMonthArray: ArrayList<String> = ArrayList()
        // val yearMonth: YearMonth = YearMonth.from(date)

        //val daysInMonth: Int = yearMonth.lengthOfMonth()
        val daysInMonth: Int = date.getActualMaximum(Calendar.DAY_OF_MONTH)
        //val firstOfMonth: LocalDate = selectedDate.withDayOfMonth(1)

        val dayOfWeek = date.firstDayOfWeek
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        if (daysInMonthArray.takeWhile { it.isEmpty() }.size >= 7)
            daysInMonthArray.subList(0, 7).clear()
        if (daysInMonthArray.takeLastWhile { it.isEmpty() }.size >= 7)
            daysInMonthArray.subList(daysInMonthArray.size - 7, daysInMonthArray.size).clear()
        return daysInMonthArray
    }


    private fun monthYearFromDate(date: Calendar): String {
        return formatDate(requireContext(), date.time, R.string.date_format_calendar)
    }


    private fun previousMonthAction() {
        selectedDate.add(Calendar.MONTH, -1)
        setMonthView()
    }


    private fun nextMonthAction() {
        selectedDate.add(Calendar.MONTH, 1)
        setMonthView()
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter()
        calendarAdapter.setData(daysInMonth.map { SelectableString(it) })
        calendarAdapter.setOnItemClickListener { position ->
            if (daysInMonth[position] != "") {
                val message =
                    "Selected Date " + daysInMonth[position] + " " + monthYearFromDate(
                        selectedDate
                    )
                listener?.onItemSelect(position)
            }
        }

        val layoutManager =
            GridLayoutManager(view?.context, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }
}