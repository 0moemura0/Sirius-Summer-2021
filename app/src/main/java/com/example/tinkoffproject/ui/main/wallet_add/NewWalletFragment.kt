package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.NotificationType
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class NewWalletFragment : Fragment(R.layout.fragment_new_wallet) {
    private lateinit var nameLayout: View
    private lateinit var currencyLayout: View
    private lateinit var limitLayout: View
    private lateinit var btn: NextCustomButton

    private val viewModel: AddWalletViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setData()
        setupNextButton()
        setupToolbar()
        setupNavigation()
        setupBtnObserver()
    }

    private fun initViews() {
        nameLayout = requireView().findViewById(R.id.ll_name_container)
        currencyLayout = requireView().findViewById(R.id.ll_currency_container)
        limitLayout = requireView().findViewById(R.id.ll_limit_container)

        btn = requireView().findViewById(R.id.btn)
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
        requireView().findViewById<NextCustomButton>(R.id.btn).apply {
            setOnClickListener {
                if (isNextAvailable()) {
                    if (viewModel.isChangeCase) {
                        viewModel.editWallet().observe(viewLifecycleOwner, ::onUpdate)
                    } else {
                        viewModel.addWallet().observe(viewLifecycleOwner, ::onUpdate)
                    }
                } else {
                    Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            setTitle(if (viewModel.isChangeCase) R.string.wallet_update else R.string.wallet_create)
        }
    }

    private fun onUpdate(state: State<WalletNetwork>) {
        when (state) {
            is State.LoadingState -> {
                btn.changeState(NextCustomButton.State.LOADING)
            }
            is State.ErrorState -> {
                onError(state.exception)
            }
            is State.DataState -> {
                btn.changeState(NextCustomButton.State.DEFAULT)
                findNavController().popBackStack(R.id.walletsList, false)
            }
        }
    }

    private fun onError(e: Throwable?) {
        btn.changeState(NextCustomButton.State.DEFAULT)

        val notType =
            if (e is IllegalAccessError) NotificationType.INTERNET_PROBLEM_ERROR else NotificationType.UNKNOWN_ERROR
        (requireActivity() as MainActivity).showNotification(notType)
    }

    private fun isNextAvailable() =
        viewModel.currency.value != null && viewModel.name.value != null

    private fun setupBtnObserver() {
        viewModel.currency.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.name.observe(viewLifecycleOwner, {
            updateButtonState()
        })
    }

    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
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

        val strDefault = getString(R.string.dont_set)

        name.text = viewModel.name.value ?: strDefault
        currency.text = viewModel.currency.value?.shortName ?: strDefault

        limit.text = strDefault
        viewModel.limit.value?.let {
            limit.text = formatMoney(it)
        }
    }
}