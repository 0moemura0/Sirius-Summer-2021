package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddOperationViewModel
import com.google.android.material.textfield.TextInputEditText

class SetCashFragment : Fragment(R.layout.operation_set_cash) {

    private val viewModel: AddOperationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputEditText: TextInputEditText = view.findViewById(R.id.et_sum)
        var isNextAvailable = false
        val btn: TextView = view.findViewById(R.id.btn)

        inputEditText.doAfterTextChanged {
            isNextAvailable = !it.isNullOrEmpty()
        }
        btn.setOnClickListener {
            if (isNextAvailable) {
                viewModel._sum.value = inputEditText.text.toString().toInt()
                findNavController().navigate(R.id.action_setCashFragment_to_chooseTypeFragment)
            } else {
                Toast.makeText(view.context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.enter_count), ToolbarType.ADD_OPERATION)
    }
}