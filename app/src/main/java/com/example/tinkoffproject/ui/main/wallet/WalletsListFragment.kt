package com.example.tinkoffproject.ui.main.wallet

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.data.dto.to_view.Transaction
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.ui.main.carddetails.*
import com.example.tinkoffproject.ui.main.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.ui.main.dialog.ConfirmRemoveDialog
import com.example.tinkoffproject.utils.WALLET_AS_CATEGORY
import com.example.tinkoffproject.utils.toLocal
import com.example.tinkoffproject.viewmodel.WalletListViewModel
import java.text.DecimalFormat


class WalletsListFragment : Fragment(R.layout.fragment_wallets_list) {
    private val viewModel: WalletListViewModel by activityViewModels()

    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView

    private lateinit var currencyContainer1: View
    private lateinit var currencyContainer2: View
    private lateinit var currencyContainer3: View

    private val confirmDialog: ConfirmRemoveDialog by lazy {
        ConfirmRemoveDialog(R.string.confirm_remove_wallet)
    }

    private var transactionAdapter: TransactionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupViews()

        setupToolbar()
        setupExpensesIncomeLayout()
        setupNavigation()
        setupRecycler()
        setupCurrency()

    }

    private fun setupViews() {
        layoutIncome.isClickable = false
        layoutIncome.isFocusable = false

        layoutExpenses.isClickable = false
        layoutExpenses.isFocusable = false

    }

    private fun setupCurrency() {
        viewModel.loadCurrencyInfo()
        viewModel.currency.observe(viewLifecycleOwner, {
            updateCurrencyInfo(it[0], currencyContainer1)
            updateCurrencyInfo(it[1], currencyContainer2)
            updateCurrencyInfo(it[2], currencyContainer3)
        })
    }

    private fun updateCurrencyInfo(currency: Currency, container: View) {
        val dec = DecimalFormat("#.##")
        container.findViewById<TextView>(R.id.tv_name).text = currency.shortName
        container.findViewById<TextView>(R.id.tv_delta).text = dec.format(currency.rate)
        val arrow = container.findViewById<ImageView>(R.id.uv_arrow)
        val arrayResId = if (currency.isUp) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        arrow.setImageResource(arrayResId)
    }

    private fun setupRecycler() {
        transactionAdapter = TransactionAdapter(::onWalletClick, false)

        transactionAdapter.apply {
            //setHasStableIds(true)
        }
        val recycler: RecyclerView = requireView().findViewById(R.id.rv_wallets)

        val offsetNormal = resources.getDimension(R.dimen.view_dimen_normal).toInt()
        val decorator = TransactionItemDecorator(offsetNormal)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
            addItemDecoration(decorator)
        }
        val swipe = object : MySwipeHelper(context, recycler, 180) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder?,
                buffer: MutableList<MyButton>
            ) {
                buffer.apply {
                    add(
                        MyButton(context!!, R.drawable.ic_delete) { pos ->
                            onRemoveClicked(pos)
                        }
                    )
                    add(
                        MyButton(context!!, R.drawable.ic_edit) { pos ->
                            onChangeClicked(pos)
                        }
                    )
                    add(
                        MyButton(context!!, R.drawable.ic_hide) { pos ->
                            onHideClicked(pos)
                        }
                    )
                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recycler)


        viewModel.getWalletsList().observe(viewLifecycleOwner, ::updateWallets)
    }

    private fun onWalletClick(position: Int) {
        val wallet = transactionAdapter?.data?.getOrNull(position)
        if (wallet != null) {
            val action = WalletsListFragmentDirections.actionToTransactions(wallet.id)
            findNavController().navigate(action)
        }
    }

    private fun updateWallets(state: State<List<WalletNetwork>>) {
        when (state) {
            is State.LoadingState -> {
            }
            is State.ErrorState -> onError(state.exception)
            is State.DataState -> {
                transactionAdapter?.setData(state.data.map {
                    Transaction(
                        id = it.id.toLong(),
                        date = 0,
                        isIncome = false,
                        category = WALLET_AS_CATEGORY,
                        amount = (it.balance ?: 0).toInt(),
                        amountFormatted = formatMoney(
                            (it.balance ?: 0).toInt(),
                            it.currency.toLocal()
                        )
                    )
                })
            }
        }
    }

    private fun onError(e: Throwable?) {
        //TODO интерфейс активити, который бы воказывал сообщение об ошибке
    }

    private fun initViews() {
        layoutIncome = requireView().findViewById(R.id.wallets_income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.wallets_expenses)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        currencyContainer1 = requireView().findViewById(R.id.l_currency_1)
        currencyContainer2 = requireView().findViewById(R.id.l_currency_2)
        currencyContainer3 = requireView().findViewById(R.id.l_currency_3)
    }

    private fun setupExpensesIncomeLayout() {
        layoutIncome.apply {
            findViewById<TextView>(R.id.tv_type).setText(R.string.income)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_green)
        }
        layoutExpenses.apply {
            findViewById<TextView>(R.id.tv_type).setText(R.string.expenses)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_red)
        }
    }

    private fun setupNavigation() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            val action = WalletsListFragmentDirections.actionToAddWallet(true)
            findNavController().navigate(action)
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("", ToolbarType.INVISIBLE)
    }

    private fun onRemoveClicked(pos: Int) {
        if (!confirmDialog.isAdded)
            confirmDialog.show(childFragmentManager, ChooseColorDialogFragment.TAG)

        confirmDialog.setOnItemClickListener {
            val text: String
            if (it == 0) text = "cancel"
            else text = "confirm"
            Toast.makeText(requireContext(), "DELETE $text $pos", Toast.LENGTH_SHORT).show()
            confirmDialog.dismiss()
        }
    }

    private fun onChangeClicked(pos: Int) {
        Toast.makeText(requireContext(), "EDIT Clicked $pos \uFDFC", Toast.LENGTH_SHORT).show()
        viewModel.wallets.value?.let {
            if (it is State.DataState) {
                val wallet = it.data.getOrNull(pos)
                val action = WalletsListFragmentDirections.actionToChangeWallet(wallet)
                findNavController().navigate(action)
            }
        }
    }

    private fun onHideClicked(pos: Int) {
        Toast.makeText(requireContext(), "HIDE Clicked $pos \uFDFC", Toast.LENGTH_SHORT).show()
    }
}