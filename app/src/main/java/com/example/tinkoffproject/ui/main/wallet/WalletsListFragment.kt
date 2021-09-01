package com.example.tinkoffproject.ui.main.wallet

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.ui.main.base_fragment.main.MainFragment
import com.example.tinkoffproject.ui.main.carddetails.MySwipeHelper
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.ui.main.dialog.ConfirmRemoveDialog
import com.example.tinkoffproject.utils.*
import com.example.tinkoffproject.viewmodel.WalletListViewModel
import kotlin.math.roundToInt


class WalletsListFragment :
    MainFragment(R.layout.fragment_wallets_list, toolbarType = ToolbarType.INVISIBLE) {
    private val viewModel: WalletListViewModel by activityViewModels()

    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView
    private lateinit var layoutSum: TextView

    private lateinit var currencyContainer1: View
    private lateinit var currencyContainer2: View
    private lateinit var currencyContainer3: View

    private lateinit var walletsRecycler: RecyclerView
    private lateinit var walletsHiddenRecycler: RecyclerView


    private val confirmDialog: ConfirmRemoveDialog by lazy {
        ConfirmRemoveDialog(R.string.confirm_remove_wallet)
    }

    companion object {
        const val IS_HIDDEN_VISIBLE = "IS_HIDDEN_VISIBLE"
    }

    private var walletAdapter: TransactionAdapter? = null
    private var hiddenWalletAdapter: TransactionAdapter? = null
    private var isHiddenVisible = false
    private var startShimmerTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            isHiddenVisible = savedInstanceState.getBoolean(IS_HIDDEN_VISIBLE) == true
        }

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_HIDDEN_VISIBLE, isHiddenVisible)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupViews()

        setupExpensesIncomeLayout()

        initRecyclerAdapters()
        setupRecycler(walletAdapter!!, walletsRecycler)
        setupRecycler(hiddenWalletAdapter!!, walletsHiddenRecycler)

        setupCurrency()

    }


    private fun setupViews() {
        layoutIncome.isClickable = false
        layoutIncome.isFocusable = false

        layoutExpenses.isClickable = false
        layoutExpenses.isFocusable = false

        val showMore: View = requireView().findViewById(R.id.l_show_hidden)
        val showMoreTitle: TextView = showMore.findViewById(R.id.tv_title)
        val showMoreIcon: ImageView = showMore.findViewById(R.id.iv_arrow)

        showMoreTitle.setText(R.string.show_more)
        showMoreIcon.rotation = 0F

        showMore.setOnClickListener {
            onClickMore(showMore)
        }
    }

    private fun onClickMore(showMore: View) {
        val showMoreTitle: TextView = showMore.findViewById(R.id.tv_title)
        val showMoreIcon: ImageView = showMore.findViewById(R.id.iv_arrow)
        if (isHiddenVisible) {
            hiddenWalletAdapter?.hideData()
            showMoreTitle.setText(R.string.show_hidden_wallets)
            showMoreIcon.rotation = 0F
        } else {
            hiddenWalletAdapter?.showData()
            /*hiddenWalletAdapter?.setData(viewModel.hiddenWallet.value?.map { it.toTransaction() }
                ?: emptyList())*/
            showMoreTitle.setText(R.string.hide)
            showMoreIcon.rotation = 180F
        }
        isHiddenVisible = !isHiddenVisible
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
        container.findViewById<TextView>(R.id.tv_name).text = currency.shortName
        container.findViewById<TextView>(R.id.tv_delta).text = formatCurrency(currency.rate)
        val arrow = container.findViewById<ImageView>(R.id.uv_arrow)
        val arrayResId = if (currency.isUp) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        arrow.setImageResource(arrayResId)
    }

    private fun initRecyclerAdapters() {
        hiddenWalletAdapter =
            TransactionAdapter(::onHiddenWalletClick, isTransaction = false, isHiddenWallet = true)
        walletAdapter = TransactionAdapter(::onNotHiddenWalletClick, isTransaction = false)
    }

    private fun setupRecycler(transactionAdapter: TransactionAdapter, recycler: RecyclerView) {
        transactionAdapter.apply {
            //setHasStableIds(true)
        }
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
                if (viewHolder?.itemViewType == TransactionAdapter.TYPE_NO_TRANSACTION) return
                buffer.apply {
                    add(
                        MyButton(context!!, R.drawable.ic_delete) { pos ->
                            onRemoveClicked(pos, transactionAdapter)
                        }
                    )
                    add(
                        MyButton(context!!, R.drawable.ic_edit) { pos ->
                            onChangeClicked(pos, transactionAdapter)
                        }
                    )
                    val hideIcon =
                        if (transactionAdapter.isHiddenWallet) R.drawable.ic_show else R.drawable.ic_hide
                    add(
                        MyButton(context!!, hideIcon) { pos ->
                            onHideClicked(
                                pos,
                                transactionAdapter,
                                transactionAdapter.isHiddenWallet
                            )
                        }
                    )
                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recycler)


        viewModel.getWalletsList().observe(viewLifecycleOwner, ::updateWallets)
    }

    private fun onNotHiddenWalletClick(position: Int) {
        onWalletClick(position, walletAdapter)
    }

    private fun onHiddenWalletClick(position: Int) {
        onWalletClick(position, hiddenWalletAdapter)
    }

    private fun onWalletClick(position: Int, adapter: TransactionAdapter?) {
        if (adapter?.data?.getOrNull(position) != null) {
            viewModel.getWalletsList().observe(viewLifecycleOwner, {
                if (it is State.DataState) {
                    val wallet = it.data.filter { w -> w.hidden == adapter.isHiddenWallet }
                        .getOrNull(position)
                    if (wallet != null) {
                        val action =
                            WalletsListFragmentDirections.actionToTransactions(wallet.toLocal())
                        findNavController().navigate(action)
                    }
                }
            })
        }
    }

    private fun updateWallets(state: State<List<WalletNetwork>>, withLoading: Boolean = true) {
        when (state) {
            is State.LoadingState -> {
                if (withLoading) onShimmerLoading()
                else onLoading()
            }
            is State.ErrorState -> {
                onInternetError(state.exception, withLoading)
            }
            is State.DataState -> {
                if (withLoading) stopShimmerLoading()
                else updateButtonState()

                var expenses = 0.0
                var income = 0.0

                state.data.forEach {
                    viewModel.getIncomeExpense(it.id).observe(viewLifecycleOwner, { pack ->
                        if (pack is State.DataState) {
                            expenses += pack.data.expenses ?: 0.0
                            income += pack.data.income ?: .0
                            layoutExpensesCash.text =
                                formatMoney(expenses.roundToInt(), it.currency.symbol)
                            layoutIncomeCash.text =
                                formatMoney(income.roundToInt(), it.currency.symbol)
                            layoutSum.text =
                                formatMoney((income - expenses).roundToInt(), it.currency.symbol)
                        }
                    })
                }
                walletAdapter?.setData(state.data
                    .map { it.toLocal() }
                    .filter { !it.hidden }
                    .map { it.asTransaction() })
                hiddenWalletAdapter?.setData(state.data
                    .map { it.toLocal() }
                    .filter { it.hidden }
                    .map { it.asTransaction() })
                onClickMore(requireView().findViewById(R.id.l_show_hidden))
            }
        }
    }

    override fun initViews() {
        layoutIncome = requireView().findViewById(R.id.wallets_income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.wallets_expenses)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        currencyContainer1 = requireView().findViewById(R.id.l_currency_1)
        currencyContainer2 = requireView().findViewById(R.id.l_currency_2)
        currencyContainer3 = requireView().findViewById(R.id.l_currency_3)

        walletsRecycler = requireView().findViewById(R.id.rv_wallets)
        walletsHiddenRecycler = requireView().findViewById(R.id.rv_wallets_hidden)
        layoutSum = requireView().findViewById(R.id.tw_sum)
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

    override fun setupNextButtonImpl() {
        setupNextButton(
            isDefaultErrorMessage = true,
            action = WalletsListFragmentDirections.actionToAddWallet(true)
        )
    }

    private fun onRemoveClicked(pos: Int, adapter: TransactionAdapter) {
        if (!confirmDialog.isAdded)
            confirmDialog.show(childFragmentManager, ChooseColorDialogFragment.TAG)

        confirmDialog.setOnItemClickListener { isConfirmed ->
            if (isConfirmed != 0) {
                adapter.data[pos].let { wallet ->
                    viewModel.deleteWallet(wallet.walletId).observe(viewLifecycleOwner,
                        {
                            when (it) {
                                is State.LoadingState -> {
                                    onLoading()
                                }
                                is State.ErrorState -> {
                                    onInternetError(it.exception)
                                }
                                is State.DataState -> {
                                    adapter.onItemRemoved(pos)
                                }
                            }
                        })
                }
            }
            confirmDialog.dismiss()
        }
    }

    private fun onChangeClicked(pos: Int, transactionAdapter: TransactionAdapter) {
        val wallet = transactionAdapter.data[pos].asWallet()
        val action = WalletsListFragmentDirections.actionToChangeWallet(wallet)
        findNavController().navigate(action)
    }

    private fun onHideClicked(pos: Int, transactionAdapter: TransactionAdapter, isHide: Boolean) {
        viewModel.getWalletsList().observe(viewLifecycleOwner, { it ->
            if (it is State.DataState) {
                val wallet =
                    it.data.find { w -> w.id == transactionAdapter.data[pos].id }?.toLocal()
                if (wallet != null) {
                    viewModel.wallet = wallet
                    viewModel.editWallet(!isHide).observe(viewLifecycleOwner, {
                        when (it) {
                            is State.LoadingState -> {
                                onLoading()
                            }
                            is State.ErrorState -> {
                                onInternetError(it.exception)
                            }
                            is State.DataState -> {
                                updateButtonState()
                                if (!isHide) {
                                    walletAdapter?.onItemRemoved(pos)
                                    hiddenWalletAdapter?.onItemInserted(
                                        hiddenWalletAdapter?.data?.size ?: 0,
                                        it.data.toLocal().asTransaction(),
                                        !isHiddenVisible
                                    )
                                } else {
                                    hiddenWalletAdapter?.onItemRemoved(pos)
                                    walletAdapter?.onItemInserted(
                                        walletAdapter?.data?.size ?: 0,
                                        it.data.toLocal().asTransaction(),
                                        !isHiddenVisible
                                    )
                                }
                                /*viewModel.getWalletsList()
                                    .observe(viewLifecycleOwner, { w ->
                                        updateWallets(w, false)
                                    })*/
                            }
                        }
                    })
                }
            }
        })
    }
}
