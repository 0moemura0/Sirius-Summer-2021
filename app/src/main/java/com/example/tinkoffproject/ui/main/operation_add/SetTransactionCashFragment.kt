package com.example.tinkoffproject.ui.main.operation_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditMoneyFragment
import com.example.tinkoffproject.viewmodel.AddTransactionViewModel

class SetTransactionCashFragment : EditMoneyFragment(R.layout.layout_set_value) {
    val viewModel: AddTransactionViewModel by activityViewModels()

    private val args: SetTransactionCashFragmentArgs by navArgs()
    private var isNewOperation = true

    companion object {
        const val IS_NEW_OPERATION = "IS_NEW_OPERATION"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) isNewOperation =
            savedInstanceState.getBoolean(IS_NEW_OPERATION)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_NEW_OPERATION, isNewOperation)
        super.onSaveInstanceState(outState)
    }

    private fun setupData() {
        if (args.isNewOperation && isNewOperation) {
            isNewOperation = false
            viewModel.init(args.transaction, args.wallet)
        }
        if (args.transaction != null) {
            findNavController().navigate(R.id.action_setCash_to_changeTransaction)
        }
        setValue(viewModel.amount.value?.toString() ?: "")
    }

    override fun setupNextButtonImpl() {
        val action =
            if (args.isFromMain) R.id.action_to_newOperation else R.id.action_setCash_to_chooseType
        setupNextButton(
            resId = action,
            onSuccess = ::saveData,
            onError = ::setDefaultError
        )
    }

    private fun saveData() {
        viewModel.amount.value = getValueAsInt()
    }
}