package com.example.tinkoffproject.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.adapter.category_custom.CustomColorAdapter
import com.example.tinkoffproject.view.data.OnItemSelectListener

class ChooseColorDialogFragment() : DialogFragment(R.layout.dialog_choose_color) {

    private var listener: OnItemSelectListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colors = mutableListOf(
            ResourcesCompat.getColor(view.resources, R.color.purple, null),
            ResourcesCompat.getColor(view.resources, R.color.blue, null),
            ResourcesCompat.getColor(view.resources, R.color.brown, null),
            ResourcesCompat.getColor(view.resources, R.color.pink, null),
            ResourcesCompat.getColor(view.resources, R.color.green, null),
            ResourcesCompat.getColor(view.resources, R.color.orange, null),
            ResourcesCompat.getColor(view.resources, R.color.purple_dark, null),
            ResourcesCompat.getColor(view.resources, R.color.yellow, null),
            ResourcesCompat.getColor(view.resources, R.color.blue_main, null),
            ResourcesCompat.getColor(view.resources, R.color.green_main, null)
        )
        val recycler: RecyclerView = view.findViewById(R.id.rv_color)

        val adapter = CustomColorAdapter()
        adapter.setData(colors)
        adapter.setOnItemClickListener(
            object : OnItemSelectListener {
                override fun onItemSelect(position: Int) {
                    listener?.onItemSelect(colors[position])
                }
            }
        )
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(view.context, 3)
    }

    companion object {
        const val TAG = "CHOOSE_COLOR_DIALOG"
    }

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }
}