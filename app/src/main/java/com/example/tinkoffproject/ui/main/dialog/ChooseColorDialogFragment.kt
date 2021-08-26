package com.example.tinkoffproject.ui.main.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.adapter.color.CustomColorAdapter
import com.example.tinkoffproject.ui.main.data.OnColorSelectInterface
import com.example.tinkoffproject.utils.COLOR

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

    fun setOnItemClickListener(_listener: OnColorSelectInterface) {
        listener = _listener
    }

    companion object {
        const val TAG = "CHOOSE_COLOR_DIALOG"
    }

}