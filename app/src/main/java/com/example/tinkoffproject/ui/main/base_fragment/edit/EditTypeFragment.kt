package com.example.tinkoffproject.ui.main.base_fragment.edit

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.data.CategoryType

abstract class EditTypeFragment(
    @LayoutRes res: Int,
    @StringRes private val toolbarRes: Int = R.string.operation_choose_type,
    toolbarType: ToolbarType = ToolbarType.ADD_OPERATION
) : EditFragment(res, toolbarRes, toolbarType) {
    private lateinit var income: LinearLayout
    private lateinit var expenses: LinearLayout

    companion object {
        private const val IS_INCOME_ARG = "IS_INCOME_ARG"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSelectors()
        savedInstanceState?.let {
            setValue(if (it.getBoolean(IS_INCOME_ARG)) CategoryType.INCOME else CategoryType.EXPENSE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_INCOME_ARG, getValue() == CategoryType.INCOME)
    }


    override fun getValue(): CategoryType =
        if (getSelectableView(income).visibility == View.VISIBLE) CategoryType.INCOME else CategoryType.EXPENSE


    override fun initViews() {
        income = requireView().findViewById(R.id.ll_section_type_income)
        expenses = requireView().findViewById(R.id.ll_section_type_expenses)
    }

    private fun setupSelectors() {
        income.setOnClickListener {
            switchSelection(income, expenses)
        }
        expenses.setOnClickListener {
            switchSelection(expenses, income)
        }
    }

    fun setValue(type: CategoryType) {
        when (type) {
            CategoryType.INCOME -> switchSelection(income, expenses)
            CategoryType.EXPENSE -> switchSelection(expenses, income)
        }
    }

    private fun switchSelection(selected: View, unselected: View) {
        updateButtonState()
        getSelectableView(selected).visibility = View.VISIBLE
        getSelectableView(unselected).visibility = View.INVISIBLE
    }

    private fun getSelectableView(view: View) = view.findViewById<ImageView>(R.id.iv_checked)
}