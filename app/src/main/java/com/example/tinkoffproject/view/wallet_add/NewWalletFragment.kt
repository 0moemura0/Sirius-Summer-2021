package com.example.tinkoffproject.view.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddOperationViewModel
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class NewWalletFragment : Fragment(R.layout.fragment_new_wallet) {
    private val nameLayout: View by lazy { requireView().findViewById(R.id.ll_name_container) }
    private val currencyLayout: View by lazy { requireView().findViewById(R.id.ll_currency_container) }
    private val limitLayout: View by lazy { requireView().findViewById(R.id.ll_limit_container) }

    private val viewModel: AddWalletViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setupNextButton()
        setupToolbar()
        setupNavigation()
        //viewModel.isNextAvailable.value = true
    }

    private fun setupNavigation() {
        TODO("Not yet implemented")
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.wallet_new), ToolbarType.ADD_OPERATION)
    }

    private fun setupNextButton() {
//        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
//            if (viewModel.isNextAvailable.value == true) {
//                viewModel.prepareNext()
//                findNavController().popBackStack(R.id.walletsListFragment, false)
//            } else {
//                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
    }

    private fun setData() {
        val name: TextView = nameLayout.findViewById(R.id.tv_value)
        val currency: TextView = currencyLayout.findViewById(R.id.tv_value)
        val limit: TextView = limitLayout.findViewById(R.id.tv_value)

        val nameTitle: TextView = nameLayout.findViewById(R.id.tv_title)
        val currencyTitle: TextView = currencyLayout.findViewById(R.id.tv_title)
        val limitTitle: TextView = limitLayout.findViewById(R.id.tv_title)

        nameTitle.setText(R.string.name)
        currencyTitle.setText(R.string.currency)
        limitTitle.setText(R.string.limit)
    }
}