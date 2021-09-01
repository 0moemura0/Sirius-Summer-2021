package com.example.tinkoffproject.ui.main.carddetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.data.dto.to_view.Wallet
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.ui.main.base_fragment.WithNextButton
import com.example.tinkoffproject.ui.main.base_fragment.main.MainFragment
import com.example.tinkoffproject.ui.main.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.ui.main.dialog.ConfirmRemoveDialog
import com.example.tinkoffproject.utils.formatLimit
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.utils.toLocal
import com.example.tinkoffproject.viewmodel.TransactionListViewModel

class CardDetailsFragment : MainFragment(R.layout.fragment_card_details), WithNextButton {
    val viewModel: TransactionListViewModel by activityViewModels()

    private lateinit var walletAmount: TextView
    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView
    private lateinit var walletName: TextView
    private lateinit var walletLimit: TextView
    private lateinit var limitOverDescription: TextView

    private var transactionAdapter: TransactionAdapter? = null

    private val confirmDialog: ConfirmRemoveDialog by lazy {
        ConfirmRemoveDialog(R.string.confirm_remove_transaction)
    }

    private val args: CardDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupExpensesIncomeLayout()
        setupNextButtonImpl()
        setupDataObservers()

        setupRecyclerView()
    }

    override fun initViews() {
        walletAmount = requireView().findViewById(R.id.tv_cash_sum)
        layoutIncome = requireView().findViewById(R.id.income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.consumption)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        walletName = requireView().findViewById(R.id.tv_cash_name)
        walletLimit = layoutExpenses.findViewById(R.id.tv_cash_limit)

        limitOverDescription = requireView().findViewById(R.id.tv_limit_over_description)
    }

    private fun setupDataObservers() {
        viewModel.wallet = args.wallet
        viewModel.income.observe(viewLifecycleOwner, {
            layoutIncomeCash.text =
                formatMoney(it ?: 0, args.wallet.currency.symbol)

            walletAmount.text = formatMoney(
                ((it ?: 0) - (viewModel.expenses.value ?: 0)),
                args.wallet.currency.symbol
            )

        })
        viewModel.expenses.observe(viewLifecycleOwner, {
            layoutExpensesCash.text =
                formatMoney(it ?: 0, args.wallet.currency.symbol)

            walletAmount.text = formatMoney(
                ((viewModel.income.value ?: 0) - (it ?: 0)),
                args.wallet.currency.symbol
            )
        })
        viewModel.getTransactionList().observe(viewLifecycleOwner, ::updateTransaction)
        updateWalletInfo(args.wallet)
    }


    private fun setupExpensesIncomeLayout() {
        layoutIncome.apply {
            findViewById<TextView>(R.id.tv_type).text = context.getString(R.string.income)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_green)
        }
        layoutExpenses.apply {
            findViewById<TextView>(R.id.tv_type).text = context.getString(R.string.expenses)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_red)
        }
    }

    override fun setupNextButtonImpl() {
        val action = CardDetailsFragmentDirections.actionCardDetailsToAddTransaction(
            true,
            viewModel.wallet
        )
        setupNextButton(action = action, isDefaultErrorMessage = true)
    }

    private fun updateWalletInfo(wallet: Wallet) {
        walletName.text = wallet.name
        walletAmount.text =
            wallet.balance?.let { formatMoney(it, wallet.currency.symbol) }

        layoutIncomeCash.text = formatMoney(0, wallet.currency.symbol)
        layoutExpensesCash.text = formatMoney(0, wallet.currency.symbol)


        if (wallet.limit == null) {
            walletLimit.visibility = View.INVISIBLE
            limitOverDescription.visibility = View.GONE
        } else {
            updateLimitInfo(wallet.limit, false, wallet.currency)
        }

        updateIncomeExpenses(wallet)
    }

    private fun updateIncomeExpenses(wallet: Wallet) {
        viewModel.getIncomeExpense(args.wallet.id).observe(viewLifecycleOwner, {
            when (it) {
                is State.LoadingState -> {
                    onLoading()
                }
                is State.ErrorState -> {
                    onInternetError(it.exception)
                }
                is State.DataState -> {
                    updateButtonState()
                    layoutIncomeCash.text =
                        formatMoney((it.data.income ?: 0).toInt(), args.wallet.currency.symbol)
                    layoutExpensesCash.text =
                        formatMoney((it.data.expenses ?: 0).toInt(), args.wallet.currency.symbol)
                    walletAmount.text = formatMoney(
                        ((it.data.income ?: 0).toInt() - (it.data.expenses ?: 0).toInt()),
                        args.wallet.currency.symbol
                    )
                    if (wallet.limit != null && it.data.expenses != null) {
                        updateLimitInfo(
                            wallet.limit,
                            it.data.expenses > wallet.limit,
                            wallet.currency
                        )
                    }
                }
            }
        })
    }

    private fun onTransactionClick(position: Int) {
        val transaction = transactionAdapter?.data?.getOrNull(position)
        if (transaction != null) {

        }
    }

    private fun updateLimitInfo(limit: Int?, isOver: Boolean, currency: Currency) {
        val text: String

        val limitStatus: LimitStatus
        if (limit == null) {
            limitStatus = LimitStatus.HIDE
            text = ""
        } else {
            text = formatLimit(limit, currency.symbol)
            limitStatus = if (isOver) LimitStatus.OVER else LimitStatus.NORMAL
        }

        limitOverDescription.visibility = limitStatus.descriptionVisibility
        walletLimit.visibility = limitStatus.visibility
        walletLimit.alpha = limitStatus.alpha
        walletLimit.text = text
        walletLimit.setTextColor(ContextCompat.getColor(walletLimit.context, limitStatus.color))
    }

    private fun updateTransaction(state: State<List<TransactionNetwork>>) {
        when (state) {
            is State.LoadingState -> {
                onShimmerLoading()
            }
            is State.ErrorState -> onInternetError(state.exception, true)
            is State.DataState -> {
                stopShimmerLoading()
                transactionAdapter?.setData(state.data.map { it.toLocal() })
            }
        }
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(::onTransactionClick)

        transactionAdapter.apply {
            //setHasStableIds(true)
        }
        val recycler: RecyclerView = requireView().findViewById(R.id.rcv_transaction)

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
                buffer.add(
                    MyButton(
                        context!!,
                        R.drawable.ic_delete
                    ) { pos -> onRemoveClicked(pos) }
                )

                buffer.add(
                    MyButton(
                        context!!,
                        R.drawable.ic_edit
                    ) { pos -> onChangeClicked(pos) }
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun onRemoveClicked(pos: Int) {
        if (!confirmDialog.isAdded)
            confirmDialog.show(childFragmentManager, ChooseColorDialogFragment.TAG)

        confirmDialog.setOnItemClickListener { isConfirmed ->
            if (isConfirmed != 0 && transactionAdapter != null) {
                val transaction = transactionAdapter!!.data[pos]
                transaction.let { t ->
                    viewModel.deleteTransaction(t.id)
                        .observe(viewLifecycleOwner,
                            {
                                when (it) {
                                    is State.LoadingState -> {
                                        btn.changeState(NextCustomButton.State.LOADING)
                                    }
                                    is State.ErrorState -> {
                                        onInternetError(it.exception)
                                    }
                                    is State.DataState -> {
                                        btn.changeState(NextCustomButton.State.LOADING)
                                        if (t.isIncome)
                                            viewModel.income.value?.minus(t.value)
                                        else
                                            viewModel.expenses.value?.minus(t.value)
                                        transactionAdapter!!.onItemRemoved(pos)
                                        updateIncomeExpenses(args.wallet)
                                    }
                                }
                            })
                }
            }
            confirmDialog.dismiss()
        }
    }

    private fun onChangeClicked(pos: Int) {
        val transaction = transactionAdapter?.data?.getOrNull(pos)
        val action =
            CardDetailsFragmentDirections.actionToChangeTransaction(transaction, viewModel.wallet)
        findNavController().navigate(action)
    }


}