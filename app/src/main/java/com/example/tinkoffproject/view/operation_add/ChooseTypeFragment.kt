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
import com.example.tinkoffproject.viewmodel.AddOperationViewModel

class ChooseTypeFragment : Fragment(R.layout.operation_choose_type) {
    private val viewModel: AddOperationViewModel by activityViewModels()

    enum class CategoryType(val catNameId: Int) {
        INCOME(R.string.income), EXPENSE(R.string.consumption)
    }

    var selectedType: CategoryType? = null

    companion object {
        private const val KEY_SELECTED_TYPE = "SELECTED_TYPE"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_SELECTED_TYPE, selectedType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val income: TextView = view.findViewById(R.id.tv_income)
        val cons: TextView = view.findViewById(R.id.tv_consumption)
        val btn: TextView = view.findViewById(R.id.btn)

        if(savedInstanceState != null){
            selectedType = savedInstanceState.getSerializable(KEY_SELECTED_TYPE) as CategoryType?
        }
        var isNextAvailable = selectedType != null
        if (selectedType != null) {
            when (selectedType) {
                CategoryType.EXPENSE -> {
                    select(cons, income)
                }
                CategoryType.INCOME -> {
                    select(income, cons)
                }
            }
        }

        income.setOnClickListener {
            selectedType = CategoryType.INCOME
            isNextAvailable = true
            select(it, cons)
        }

        cons.setOnClickListener {
            selectedType = CategoryType.EXPENSE
            isNextAvailable = true
            select(it, income)
        }

        btn.setOnClickListener {
            if (isNextAvailable) {
                viewModel._type.value = getString(selectedType?.catNameId!!)
                findNavController().navigate(R.id.action_chooseTypeFragment_to_chooseCategoryFragment)
            } else {
                Toast.makeText(view.context, getString(R.string.enter_value), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateToolbar()
    }

    private fun updateToolbar() {
        activity?.findViewById<TextView>(R.id.title)?.text = getString(R.string.operation_choose_type)
        activity?.findViewById<ImageView>(R.id.iv_settings)?.visibility = View.INVISIBLE
    }

    private fun select(selected: View, unselected: View) {
        selected.setBackgroundResource(R.color.gray_light)
        unselected.background = null
    }
}