package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.view.data.CategoryType
import com.example.tinkoffproject.viewmodel.AddOperationViewModel

class ChooseTypeFragment : Fragment(R.layout.operation_choose_type) {
    private val viewModel: AddOperationViewModel by activityViewModels()
    private lateinit var income: TextView
    private lateinit var cons: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.isIncome.observe(viewLifecycleOwner, {
            when (it) {
                CategoryType.INCOME -> select(income, cons)
                CategoryType.EXPENSE -> select(cons, income)
            }
        })

        setupSelectors()
        setupNextButton()
        setupToolBar()
    }

    private fun initViews() {
        income = requireView().findViewById(R.id.tv_income)
        cons = requireView().findViewById(R.id.tv_consumption)
    }

    private fun setupSelectors() {
        income.setOnClickListener {
            viewModel.isIncome.value = CategoryType.INCOME
            viewModel.isNextAvailable.value = true
        }
        cons.setOnClickListener {
            viewModel.isIncome.value = CategoryType.EXPENSE
            viewModel.isNextAvailable.value = true
        }
    }

    private fun setupToolBar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.operation_choose_type), ToolbarType.ADD_OPERATION)
    }

    private fun setupNextButton() {
        requireView().findViewById<TextView>(R.id.btn).setOnClickListener {
            if (viewModel.isNextAvailable.value == true) {
                viewModel.prepareNext()
                findNavController().navigate(R.id.action_chooseTypeFragment_to_chooseCategoryFragment)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun select(selected: View, unselected: View) {
        selected.setBackgroundResource(R.color.gray_light)
        unselected.background = null
    }
}