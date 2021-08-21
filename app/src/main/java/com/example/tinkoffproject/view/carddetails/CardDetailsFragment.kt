package com.example.tinkoffproject.view.carddetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.utils.State
import com.example.tinkoffproject.model.utils.formatMoney
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.view.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.view.adapter.transaction.TransactionTouchHelperCallback
import com.example.tinkoffproject.viewmodel.CardDetailsViewModel

private val data: List<Transaction> = emptyList()/*listOf(
    Transaction(
        id = 35,
        date = 1629294379553,
        isIncome = true,
        category = CategoryNetwork("Супермаркет", 0, "F52222"),
        amount = 10000,
    ),
    Transaction(
        id = 22,
        date = 1629294379553,
        isIncome = true,
        category = CategoryNetwork = ("Супермаркет", 0, "F52222",
        amount = 10001,
    ),
    Transaction(
        id = 10,
        date = 1629294379553,
        isIncome = true,
        category = CategoryNetwork("Супермаркет", 0, "F52222"),
        amount = 10010,
    ),
    Transaction(
        id = 9,
        date = 1629294379553,
        isIncome = true,
        category = CategoryNetwork("Супермаркет", 0, "F52222"),
        amount = 10100,
    ),
    Transaction(
        id = 8,
        date = 1629294379553,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11000,
    ),
    Transaction(
        id = 7,
        date = 1629224699479,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11001,
    ),
    Transaction(
        id = 6,
        date = 1629224699479,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11010,
    ),
    Transaction(
        id = 5,
        date = 1629138299480,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11100,
    ),
    Transaction(
        id = 4,
        date = 1629051899480,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11101,
    ),
    Transaction(
        id = 3,
        date = 1629051899480,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11110,
    ),
    Transaction(
        id = 2,
        date = 1628447099480,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11111,
    ),
    Transaction(
        id = 1,
        date = 1628447099480,
        type = TransactionType.EXPENSES,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 11245,
    ),
)*/

class CardDetailsFragment : Fragment(R.layout.fragment_card_details) {
    private val viewModel: CardDetailsViewModel by viewModels()

    private lateinit var walletAmount: TextView
    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView
    private lateinit var walletName: TextView
    private lateinit var walletLimit: TextView

    private var transactionAdapter: TransactionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupExpensesIncomeLayout()
        setupNavigation()
        setupToolbar()
        setupDataObservers()

        setupRecyclerView(view)
    }

    private fun initViews() {
        walletAmount = requireView().findViewById(R.id.tv_cash_sum)
        layoutIncome = requireView().findViewById(R.id.income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.consumption)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        walletName = requireView().findViewById(R.id.tv_cash_name)
        walletLimit = layoutExpenses.findViewById(R.id.tv_cash_limit)

    }

    private fun setupDataObservers() {
        viewModel.wallet.observe(viewLifecycleOwner, ::updateWalletInfo)
        viewModel.transaction.observe(viewLifecycleOwner, ::updateTransaction)
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

    private fun setupNavigation() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            findNavController().navigate(R.id.action_cardDetailsFragment_to_setCashFragment)
        }
    }

    private fun setupToolbar() {
        val updateActivity = activity as MainActivity
        updateActivity.updateToolbar("")
    }

    private fun updateWalletInfo(state: State<Wallet>) {
        when (state) {
            is State.ErrorState -> onError(state.exception)
            is State.LoadingState -> {
                //TODO анимация либо на кнопке, либо свайп энд рефреш
            }
            is State.DataState -> {
                val wallet = state.data
                walletName.text = wallet.name
                walletAmount.text =
                    formatMoney(wallet.incomeAmount - wallet.expensesAmount, wallet.currency)

                layoutIncomeCash.text = formatMoney(wallet.incomeAmount, wallet.currency)
                layoutExpensesCash.text = formatMoney(wallet.expensesAmount, wallet.currency)

                transactionAdapter?.currency = wallet.currency

                updateLimitInfo(wallet.limit, wallet.expensesAmount, wallet.currency)
            }
        }
    }

    private fun updateLimitInfo(limit: Int?, expenses: Int, currency: Currency) {
        val colorId: Int
        val alpha: Float
        val text: String
        if (limit == null) {
            text = ""
            colorId = R.color.white
            alpha = 1f
        } else {
            text = " / ${formatMoney(limit, currency)}"
            if (limit > expenses) {
                colorId = R.color.red_main
                alpha = 1f
            } else {
                colorId = R.color.white
                alpha = 0.6f
            }
        }

        walletLimit.alpha = alpha
        walletLimit.text = text
        walletLimit.setTextColor(ContextCompat.getColor(walletLimit.context, colorId))
    }

    private fun updateTransaction(state: State<List<Transaction>>) {
        when (state) {
            is State.LoadingState -> {
            }
            is State.ErrorState -> onError(state.exception)
            is State.DataState -> {
                transactionAdapter?.setData(state.data)
            }
        }
    }

    private fun onError(e: Throwable?) {
        //TODO интерфейс активити, который бы воказывал сообщение об ошибке
    }


    private fun setupRecyclerView(view: View) {
        transactionAdapter = TransactionAdapter()

        transactionAdapter.apply {
            //setHasStableIds(true)
        }
        val recycler: RecyclerView = view.findViewById(R.id.rcv_transaction)

        val offsetNormal = resources.getDimension(R.dimen.view_dimen_normal).toInt()
        val decorator = TransactionItemDecorator(offsetNormal)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
            addItemDecoration(decorator)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            transactionAdapter?.setData(data)
        }, 3000)

        val itemTouchHelper = ItemTouchHelper(TransactionTouchHelperCallback())
        itemTouchHelper.attachToRecyclerView(recycler)
    }
}