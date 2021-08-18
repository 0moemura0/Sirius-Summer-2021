package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.formatDate
import com.example.tinkoffproject.viewmodel.AddOperationViewModel
import java.util.*

class NewOperationFragment : Fragment(R.layout.operation_new_operation) {
    private val viewModel: AddOperationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val sum: TextView = view.findViewById(R.id.tv_sum)
        val type: TextView = view.findViewById(R.id.tv_type)
        val category: TextView = view.findViewById(R.id.tv_category)
        val date: TextView = view.findViewById(R.id.tv_date)

        val curDate = Date()

        sum.text = "${viewModel._sum.value.toString()} ₽"
        type.text = viewModel._type.value.toString()
        category.text = viewModel._category.value.toString()
        date.text = formatDate(view.context, curDate, R.string.date_format_default)

        val isNextAvailable = true

        val btn: TextView = view.findViewById(R.id.btn)
        btn.setOnClickListener {
            if (isNextAvailable) {
                findNavController().popBackStack(R.id.cardDetailsFragment, false)
            } else {
                Toast.makeText(view.context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateToolbar()
    }

    private fun updateToolbar() {
        activity?.findViewById<TextView>(R.id.title)?.text = getString(R.string.operation_new)
        activity?.findViewById<ImageView>(R.id.iv_settings)?.visibility = View.INVISIBLE
    }
}