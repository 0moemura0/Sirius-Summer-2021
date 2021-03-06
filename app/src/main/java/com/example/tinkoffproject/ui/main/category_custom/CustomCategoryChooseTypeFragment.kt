package com.example.tinkoffproject.ui.main.category_custom

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.ui.main.data.CategoryType
import com.example.tinkoffproject.viewmodel.CustomCategoryViewModel

class CustomCategoryChooseTypeFragment : Fragment(R.layout.operation_choose_type) {
    val viewModel: CustomCategoryViewModel by activityViewModels()
    private lateinit var btn: NextCustomButton
    private lateinit var income: LinearLayout
    private lateinit var cons: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupData()
        setupSelectors()
        setupNextButton()
        setupToolBar()
        setupBtnObserver()
    }

    private fun setupData() {
        viewModel.type.observe(viewLifecycleOwner, {
            when (it) {
                CategoryType.INCOME -> switchSelection(income, cons)
                CategoryType.EXPENSE -> switchSelection(cons, income)
                else -> throw IllegalStateException("CategoryType don't have the value $it")
            }
        })
    }

    private fun initViews() {
        btn = requireView().findViewById(R.id.btn)

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
            if (isNextAvailable()) {
                findNavController().navigate(R.id.action_to_newCategory)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isNextAvailable() = viewModel.type.value != null

    private fun setupBtnObserver() {
        viewModel.type.observe(viewLifecycleOwner, {
            updateButtonState()
        })
    }

    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
    }

    private fun switchSelection(selected: View, unselected: View) {
        selected.findViewById<ImageView>(R.id.iv_checked).visibility = View.VISIBLE
        unselected.findViewById<ImageView>(R.id.iv_checked).visibility = View.INVISIBLE
    }
}