package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.utils.formatDate
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
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

        // TODO replace on formatMoney(viewModel._sum.value, currency)
        sum.text = "${viewModel.amount.value.toString()} ₽"

        type.text = viewModel.isIncome.value.toString()
        category.text = viewModel.category.value.toString()
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
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.operation_new), ToolbarType.ADD_OPERATION)
    }
}