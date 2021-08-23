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
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddOperationViewModel

class NewOperationFragment : Fragment(R.layout.operation_new_operation) {
    private val viewModel: AddOperationViewModel by activityViewModels()

    private lateinit var sumLayout: View
    private lateinit var typeLayout: View
    private lateinit var categoryLayout: View
    private lateinit var dateLayout: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setData()
        setupNextButton()
        setupToolbar()
        setupNavigation()
        viewModel.isNextAvailable.value = true
    }

    private fun initViews(){
        sumLayout = requireView().findViewById(R.id.ll_sum_container)
        typeLayout = requireView().findViewById(R.id.ll_type_container)
        categoryLayout = requireView().findViewById(R.id.ll_category_container)
        dateLayout = requireView().findViewById(R.id.ll_date_container)
    }

    private fun setData() {

        val sum: TextView = sumLayout.findViewById(R.id.tv_value)
        val type: TextView = typeLayout.findViewById(R.id.tv_value)
        val category: TextView = categoryLayout.findViewById(R.id.tv_value)
        val date: TextView = dateLayout.findViewById(R.id.tv_value)

        val sumTitle: TextView = sumLayout.findViewById(R.id.tv_title)
        val typeTitle: TextView = typeLayout.findViewById(R.id.tv_title)
        val categoryTitle: TextView = categoryLayout.findViewById(R.id.tv_title)
        val dateTitle: TextView = dateLayout.findViewById(R.id.tv_title)

        sumTitle.setText(R.string.sum)
        typeTitle.setText(R.string.type)
        categoryTitle.setText(R.string.category)
        dateTitle.setText(R.string.date)

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

        sumLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newOperationFragment_to_setCashFragment)
        }

        typeLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newOperationFragment_to_chooseTypeFragment)
        }
        categoryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newOperationFragment_to_chooseCategoryFragment)
        }

        dateLayout.setOnClickListener {
            //TODO
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.operation_new), ToolbarType.ADD_OPERATION)
    }
}