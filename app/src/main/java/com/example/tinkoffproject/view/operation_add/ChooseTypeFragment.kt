package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.view.data.CategoryType
import com.example.tinkoffproject.viewmodel.AddOperationViewModel
import java.lang.IllegalStateException

class ChooseTypeFragment : Fragment(R.layout.operation_choose_type) {
    private val viewModel: AddOperationViewModel by activityViewModels()
    private lateinit var income: LinearLayout
    private lateinit var cons: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupData()
        setupSelectors()
        setupNextButton()
        setupToolBar()
    }

    private fun setupData() {
        viewModel.type.observe(viewLifecycleOwner, {
            when (it) {
                CategoryType.INCOME -> switchSelection(income, cons)
                CategoryType.EXPENSE -> switchSelection(cons, income)
                else -> throw IllegalStateException("CategoryType don't have the value $it")
            }
            viewModel.isNextAvailable.value = true
        })
    }

    private fun initViews() {
        income = requireView().findViewById(R.id.ll_section_type_income)
        cons = requireView().findViewById(R.id.ll_section_type_expenses)
    }

    private fun setupSelectors() {
        income.setOnClickListener {
            viewModel.type.value = CategoryType.INCOME
        }
        cons.setOnClickListener {
            viewModel.type.value = CategoryType.EXPENSE
        }
    }

    private fun setupToolBar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.operation_choose_type), ToolbarType.ADD_OPERATION)
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (viewModel.isNextAvailable.value == true) {
                findNavController().navigate(R.id.action_chooseTypeFragment_to_chooseCategoryFragment)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun switchSelection(selected: View, unselected: View) {
        selected.findViewById<ImageView>(R.id.iv_checked).visibility = View.VISIBLE
        unselected.findViewById<ImageView>(R.id.iv_checked).visibility = View.INVISIBLE
    }
}