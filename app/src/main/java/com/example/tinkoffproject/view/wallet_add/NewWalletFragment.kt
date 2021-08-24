package com.example.tinkoffproject.view.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.utils.formatMoney
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class NewWalletFragment : Fragment(R.layout.fragment_new_wallet) {
    private lateinit var nameLayout: View
    private lateinit var currencyLayout: View
    private lateinit var limitLayout: View

    private val viewModel: AddWalletViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        nameLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newWallet_to_setWalletName)
        }
        currencyLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newWallet_to_setWalletCurrency)
        }
        limitLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newWallet_to_setWalletLimit)
        }

        setData()
        setupNextButton()
        setupToolbar()
        setupNavigation()
    }

    private fun initViews() {
        nameLayout = requireView().findViewById(R.id.ll_name_container)
        currencyLayout = requireView().findViewById(R.id.ll_currency_container)
        limitLayout = requireView().findViewById(R.id.ll_limit_container)
    }

    private fun setupNavigation() {
        nameLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newWallet_to_setWalletName)
        }
        currencyLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newWallet_to_setWalletCurrency)
        }
        limitLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newWallet_to_setWalletLimit)
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.wallet_new), ToolbarType.ADD_OPERATION)
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (isNextAvailable()) {
                viewModel.addWallet()
                findNavController().popBackStack(R.id.walletsList, false)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isNextAvailable() =
        viewModel.currency.value != null && viewModel.name.value != null && viewModel.limit.value != null


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

        viewModel.name.observe(viewLifecycleOwner, {
            name.text = it
        })

        currency.setText(R.string.dont_set)
        viewModel.currency.observe(viewLifecycleOwner, {
            currency.text = it.shortName
        })

        limit.setText(R.string.dont_set)
        viewModel.limit.observe(viewLifecycleOwner, {
            limit.text = formatMoney(it)
        })
    }
}