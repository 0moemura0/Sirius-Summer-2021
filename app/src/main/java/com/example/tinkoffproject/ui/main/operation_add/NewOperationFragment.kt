package com.example.tinkoffproject.ui.main.operation_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.formatDate
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.base_fragment.BaseFragment
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.dialog.ChooseDatePickerFragment
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.viewmodel.AddTransactionViewModel

class NewOperationFragment : BaseFragment(
    R.layout.operation_new_operation,
    R.string.operation_new,
    ToolbarType.ADD_OPERATION
) {
    val viewModel: AddTransactionViewModel by activityViewModels()

    private lateinit var sumLayout: View
    private lateinit var typeLayout: View
    private lateinit var categoryLayout: View
    private lateinit var dateLayout: View

    private val dialog by lazy { ChooseDatePickerFragment() }

    private val args: NewOperationFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()
        initDialog()
        setData()
        setupNavigation()
        setupBtnObserver()
    }


    private fun initDialog() {
        dialog.setOnItemClickListener { calendar ->
            viewModel.date.value = calendar.time
            dialog.dismiss()
        }
    }

    override fun initViews() {
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


        viewModel.amount.observe(viewLifecycleOwner, {
            sum.text = viewModel.wallet?.currency?.let { it1 -> formatMoney(it, it1.symbol) }
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

    override fun setupNextButtonImpl() {
        setupNextButton(
            isDefaultErrorMessage = true,
            onSuccess = {
                val transaction = viewModel.transaction
                if (!viewModel.isChange) {
                    viewModel.addTransaction().observe(viewLifecycleOwner, ::onUpdate)
                } else {
                    transaction?.id?.let { it1 ->
                        viewModel.editTransaction(it1)
                            .observe(viewLifecycleOwner, ::onUpdate)
                    }
                }
            }
        )
    }

    private fun onUpdate(state: State<TransactionNetwork>) {
        when (state) {
            is State.LoadingState -> {
                btn.changeState(NextCustomButton.State.LOADING)
            }
            is State.ErrorState -> {
                onInternetError(state.exception)
            }
            is State.DataState -> {
                updateButtonState()
                findNavController().popBackStack(R.id.cardDetails, false)
            }
        }
    }

    override fun isNextAvailable() = viewModel.amount.value != null
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
}