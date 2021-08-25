package com.example.tinkoffproject.view.dialog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.data.OnItemSelectListener
import android.view.ViewGroup
import android.view.WindowManager


class ConfirmRemoveDialog(private val strResId: Int) : DialogFragment(R.layout.dialog_confirm) {
    private var listener: OnItemSelectListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            listener?.onItemSelect(0)
        }
        view.findViewById<TextView>(R.id.tv_confirm).setOnClickListener {
            listener?.onItemSelect(1)
        }

        view.findViewById<TextView>(R.id.tv_title).setText(strResId)
    }

    fun setOnItemClickListener(_listener: OnItemSelectListener) {
        listener = _listener
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}