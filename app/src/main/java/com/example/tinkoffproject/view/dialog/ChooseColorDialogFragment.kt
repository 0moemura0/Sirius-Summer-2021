package com.example.tinkoffproject.view.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.mapper.COLOR
import com.example.tinkoffproject.view.adapter.category_custom.CustomColorAdapter
import com.example.tinkoffproject.view.data.OnColorSelectInterface

class ChooseColorDialogFragment : DialogFragment(R.layout.dialog_choose_color) {
    private var listener: OnColorSelectInterface? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colors = COLOR.values().toList()

        val recycler: RecyclerView = view.findViewById(R.id.rv_color)

        val adapter = CustomColorAdapter()
        adapter.setData(colors)
        adapter.setOnItemClickListener { position -> listener?.onItemSelect(colors[position]) }
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(view.context, 3)
    }

    companion object {
        const val TAG = "CHOOSE_COLOR_DIALOG"
    }

    fun setOnItemClickListener(_listener: OnColorSelectInterface) {
        listener = _listener
    }
}