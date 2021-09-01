package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.base_fragment.BaseFragment
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class NewWalletFragment :
    BaseFragment(R.layout.fragment_new_wallet, R.string.wallet_new, ToolbarType.ADD_OPERATION) {
    private lateinit var nameLayout: View
    private lateinit var currencyLayout: View
    private lateinit var limitLayout: View

    private val viewModel: AddWalletViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setData()
        setupNavigation()
        setupBtnObserver()
        setupData()
    }

    private fun setupData() {
        if (viewModel.name.value == "" || viewModel.limit.value == 0)
            viewModel.getWallet().observe(viewLifecycleOwner, {
                if (it is State.DataState) {
                    viewModel.limit.value = it.data.limit
                    viewModel.name.value = it.data.name
                }
            })
    }

    override fun initViews() {
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

    override fun setupNextButtonImpl() {
        setupNextButton(
            isDefaultErrorMessage = true,
            onSuccess = {
                if (viewModel.isChangeCase) {
                    viewModel.editWallet().observe(viewLifecycleOwner, ::onUpdate)
                } else {
                    viewModel.addWallet().observe(viewLifecycleOwner, ::onUpdate)
                }
            })
        btn.setTitle(if (viewModel.isChangeCase) R.string.wallet_update else R.string.wallet_create)
    }

    private fun onUpdate(state: State<WalletNetwork>) {
        when (state) {
            is State.LoadingState -> {
                btn.changeState(NextCustomButton.State.LOADING)
            }
            is State.ErrorState -> {
                onInternetError(state.exception)
            }
            is State.DataState -> {
                updateButtonState()

                findNavController().popBackStack(R.id.walletsList, false)
            }
        }
    }


    override fun isNextAvailable() =
        viewModel.currency.value != null && viewModel.name.value != null

    private fun setupBtnObserver() {
        viewModel.currency.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.name.observe(viewLifecycleOwner, {
            updateButtonState()
        })
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

        viewModel.name.observe(viewLifecycleOwner, {
            name.text = if (it == "") strDefault else it
        })
        viewModel.currency.observe(viewLifecycleOwner, {
            currency.text = it?.shortName ?: strDefault
        })

        viewModel.limit.observe(viewLifecycleOwner, {
            limit.text = strDefault
            it?.let {
                limit.text = formatMoney(it)
            }
        })
    }
}