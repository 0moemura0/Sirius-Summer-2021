package com.example.tinkoffproject.ui.main.operation_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.formatDate
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.ui.main.dialog.ChooseDatePickerFragment
import com.example.tinkoffproject.viewmodel.AddTransactionViewModel

class NewOperationFragment : Fragment(R.layout.operation_new_operation) {
    val viewModel: AddTransactionViewModel by activityViewModels()

    private lateinit var sumLayout: View
    private lateinit var typeLayout: View
    private lateinit var categoryLayout: View
    private lateinit var dateLayout: View
    private lateinit var btn: NextCustomButton

    private val dialog by lazy { ChooseDatePickerFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initDialog()
        setData()
        setupNextButton()
        setupToolbar()
        setupNavigation()
        setupBtnObserver()
    }

    private fun initDialog() {
        dialog.setOnItemClickListener { calendar ->
            viewModel.date.value = calendar.time
            dialog.dismiss()
        }
    }

    private fun initViews() {
        sumLayout = requireView().findViewById(R.id.ll_sum_container)
        typeLayout = requireView().findViewById(R.id.ll_type_container)
        categoryLayout = requireView().findViewById(R.id.ll_category_container)
        dateLayout = requireView().findViewById(R.id.ll_date_container)
        btn = requireView().findViewById(R.id.btn)
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
            if (isNextAvailable()) {
                viewModel.addTransaction().observe(viewLifecycleOwner, {
                    findNavController().popBackStack(R.id.cardDetails, false)
                })
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isNextAvailable() = viewModel.amount.value != null
            && viewModel.category.value != null
            && viewModel.date.value != null
            && viewModel.type.value != null

    private fun setupBtnObserver() {
        viewModel.amount.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.category.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.date.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.type.observe(viewLifecycleOwner, {
            updateButtonState()
        })
    }

    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
    }

    private fun setupNavigation() {

        sumLayout.setOnClickListener {
            val action =
                NewOperationFragmentDirections.actionNewOperationToSetCash(isFromMain = true)
            findNavController().navigate(action)
        }

        typeLayout.setOnClickListener {
            val action =
                NewOperationFragmentDirections.actionNewOperationToChooseType(isFromMain = true)
            findNavController().navigate(action)
        }
        categoryLayout.setOnClickListener {
            val action =
                NewOperationFragmentDirections.actionNewOperationToChooseCategory(isFromMain = true)
            findNavController().navigate(action)
        }

        dateLayout.setOnClickListener {
            if (!dialog.isAdded) {
                dialog.show(childFragmentManager, ChooseDatePickerFragment.TAG)
            }
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.operation_new), ToolbarType.ADD_OPERATION)
    }
}