package com.example.tinkoffproject.ui.main.carddetails

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.CurrencyNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.data.dto.to_view.Wallet
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.NotificationType
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.ui.main.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.ui.main.dialog.ConfirmRemoveDialog
import com.example.tinkoffproject.utils.SHIMMER_MIN_TIME_MS
import com.example.tinkoffproject.utils.START_SHIMMER_TIME_ARG
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.utils.toLocal
import com.example.tinkoffproject.viewmodel.TransactionListViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class CardDetailsFragment : Fragment(R.layout.fragment_card_details) {
    val viewModel: TransactionListViewModel by activityViewModels()

    private lateinit var walletAmount: TextView
    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView
    private lateinit var walletName: TextView
    private lateinit var walletLimit: TextView
    private lateinit var btn: NextCustomButton

    private lateinit var mShimmerViewContainer: ShimmerFrameLayout
    private lateinit var container: View

    private var transactionAdapter: TransactionAdapter? = null

    private val confirmDialog: ConfirmRemoveDialog by lazy {
        ConfirmRemoveDialog(R.string.confirm_remove_transaction)
    }

    private var startShimmerTime = 0L

    private val args: CardDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            startShimmerTime = savedInstanceState.getLong(START_SHIMMER_TIME_ARG, 0L)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(START_SHIMMER_TIME_ARG, startShimmerTime)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupShimmer()

        setupExpensesIncomeLayout()
        setupNavigation()
        setupToolbar()
        setupDataObservers()

        setupRecyclerView(view)
    }

    private fun setupShimmer() {
        container.visibility = View.INVISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            container.visibility = View.VISIBLE
            mShimmerViewContainer.stopShimmer()
            mShimmerViewContainer.visibility = View.GONE
        }, 2000)
    }

    private fun initViews() {
        walletAmount = requireView().findViewById(R.id.tv_cash_sum)
        layoutIncome = requireView().findViewById(R.id.income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.consumption)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        walletName = requireView().findViewById(R.id.tv_cash_name)
        walletLimit = layoutExpenses.findViewById(R.id.tv_cash_limit)

        mShimmerViewContainer = requireView().findViewById(R.id.shimmer_container)
        container = requireView().findViewById(R.id.container)
        btn = requireView().findViewById(R.id.btn)
    }

    private fun setupDataObservers() {
        viewModel.wallet = args.wallet
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

    private fun setupNavigation() {
        btn.setOnClickListener {
            val action = CardDetailsFragmentDirections.actionCardDetailsToAddTransaction(
                true,
                viewModel.wallet
            )
            findNavController().navigate(action)
        }
    }

    private fun setupToolbar() {
        val updateActivity = activity as MainActivity
        updateActivity.updateToolbar("")
    }

    private fun updateWalletInfo(wallet: Wallet) {
        walletName.text = wallet.name
        walletAmount.text =
            wallet.balance?.let { formatMoney(it, wallet.currency.symbol) }

        layoutIncomeCash.text = formatMoney(0, wallet.currency.symbol)
        layoutExpensesCash.text = formatMoney(0, wallet.currency.symbol)


        wallet.balance?.let {
            updateLimitInfo(
                wallet.limit,
                it, wallet.currency
            )


        }
    }

    private fun onTransactionClick(position: Int) {
        val transaction = transactionAdapter?.data?.getOrNull(position)
        if (transaction != null) {

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
            text = " / ${formatMoney(limit, currency.symbol)}"
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

    private fun updateTransaction(state: State<List<TransactionNetwork>>) {
        when (state) {
            is State.LoadingState -> {
            }
            is State.ErrorState -> onError(state.exception)
            is State.DataState -> {
                transactionAdapter?.setData(state.data.map { it.toLocal() })
            }
        }
    }

    private fun onLoading() {
        startShimmerTime = System.currentTimeMillis()
        container.visibility = View.INVISIBLE
        mShimmerViewContainer.startShimmer()
        mShimmerViewContainer.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        val differ = System.currentTimeMillis() - startShimmerTime
        val doAfter =
            if (differ > SHIMMER_MIN_TIME_MS) 0 else SHIMMER_MIN_TIME_MS - differ
        object : CountDownTimer(doAfter, doAfter) {
            override fun onFinish() {
                container.visibility = View.VISIBLE
                mShimmerViewContainer.stopShimmer()
                mShimmerViewContainer.visibility = View.GONE
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    private fun onError(e: Throwable?) {
        btn.changeState(NextCustomButton.State.DEFAULT)

        val notType =
            if (e is IllegalAccessError) NotificationType.INTERNET_PROBLEM_ERROR else NotificationType.UNKNOWN_ERROR
        (requireActivity() as MainActivity).showNotification(notType)
    }


    private fun setupRecyclerView(view: View) {
        transactionAdapter = TransactionAdapter(::onTransactionClick)

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

        confirmDialog.setOnItemClickListener {
            val text: String
            if (it == 0) text = "cancel"
            else text = "confirm"
            Toast.makeText(requireContext(), "DELETE $text $pos", Toast.LENGTH_SHORT).show()
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