package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.utils.formatDate
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddOperationViewModel

class NewOperationFragment : Fragment(R.layout.operation_new_operation) {
    private val viewModel: AddOperationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setupNextButton()
        setupToolbar()
        setupNavigation()
        viewModel.isNextAvailable.value = true
    }

    private fun setData() {
        val sum: TextView = requireView().findViewById(R.id.tv_sum)
        val type: TextView = requireView().findViewById(R.id.tv_type)
        val category: TextView = requireView().findViewById(R.id.tv_category)
        val date: TextView = requireView().findViewById(R.id.tv_date)

        // TODO replace on formatMoney(viewModel._sum.value, currency)
        viewModel.amount.observe(viewLifecycleOwner, {
            sum.text = "$it ₽"
        })
        viewModel.type.observe(viewLifecycleOwner, {
            type.text = requireContext().getString(it.nameResId)
        })
        viewModel.category.observe(viewLifecycleOwner, {
            category.text = it.name
        })
        viewModel.date.observe(viewLifecycleOwner, {
            date.text = formatDate(requireView().context, it, R.string.date_format_default)
        })
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (viewModel.isNextAvailable.value == true) {
                findNavController().popBackStack(R.id.cardDetailsFragment, false)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupNavigation() {
        val amountView: LinearLayout = requireView().findViewById(R.id.ll_amount_container)
        val typeView: LinearLayout = requireView().findViewById(R.id.ll_type_container)
        val categoryView: LinearLayout = requireView().findViewById(R.id.ll_category_container)
        val dateView: LinearLayout = requireView().findViewById(R.id.ll_date_container)

        amountView.setOnClickListener {
            findNavController().navigate(R.id.action_newOperationFragment_to_setCashFragment)
        }

        typeView.setOnClickListener {
            findNavController().navigate(R.id.action_newOperationFragment_to_chooseTypeFragment)
        }
        categoryView.setOnClickListener {
            findNavController().navigate(R.id.action_newOperationFragment_to_chooseCategoryFragment)
        }

        dateView.setOnClickListener {
            //TODO
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.operation_new), ToolbarType.ADD_OPERATION)
    }
}