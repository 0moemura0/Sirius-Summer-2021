package com.example.tinkoffproject.ui.main.wallet

import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.NotificationType
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.ui.main.carddetails.MySwipeHelper
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.ui.main.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.ui.main.dialog.ConfirmRemoveDialog
import com.example.tinkoffproject.utils.SHIMMER_MIN_TIME_MS
import com.example.tinkoffproject.utils.START_SHIMMER_TIME_ARG
import com.example.tinkoffproject.utils.asTransaction
import com.example.tinkoffproject.utils.toLocal
import com.example.tinkoffproject.viewmodel.WalletListViewModel
import com.facebook.shimmer.ShimmerFrameLayout
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

    private lateinit var walletsRecycler: RecyclerView
    private lateinit var walletsHiddenRecycler: RecyclerView

    private lateinit var btn: NextCustomButton

    private lateinit var mShimmerViewContainer: ShimmerFrameLayout
    private lateinit var container: View

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
            startShimmerTime = savedInstanceState.getLong(START_SHIMMER_TIME_ARG, 0L)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_HIDDEN_VISIBLE, isHiddenVisible)
        outState.putLong(START_SHIMMER_TIME_ARG, startShimmerTime)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupShimmer()

        setupViews()

        setupToolbar()
        setupExpensesIncomeLayout()
        setupNavigation()

        initRecyclerAdapters()
        setupRecycler(walletAdapter!!, walletsRecycler)
        setupRecycler(hiddenWalletAdapter!!, walletsHiddenRecycler)

        setupCurrency()

        //viewModel.
    }

    private fun setupShimmer() {

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
            if (isHiddenVisible) {
                hiddenWalletAdapter?.setData(emptyList())
                showMoreTitle.setText(R.string.show_hidden_wallets)
                showMoreIcon.rotation = 0F
            } else {
                /*hiddenWalletAdapter?.setData(viewModel.hiddenWallet.value?.map { it.toTransaction() }
                    ?: emptyList())*/
                showMoreTitle.setText(R.string.hide)
                showMoreIcon.rotation = 180F
            }
            isHiddenVisible = !isHiddenVisible
        }

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
                    add(
                        MyButton(context!!, R.drawable.ic_hide) { pos ->
                            onHideClicked(pos, transactionAdapter)
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

    private fun onLoading() {
        startShimmerTime = System.currentTimeMillis()
        container.visibility = View.INVISIBLE
        mShimmerViewContainer.startShimmer()
        mShimmerViewContainer.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        val differ = System.currentTimeMillis() - startShimmerTime
        val doAfter = if (differ > SHIMMER_MIN_TIME_MS) 0 else SHIMMER_MIN_TIME_MS - differ
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


    private fun updateWallets(state: State<List<WalletNetwork>>) {
        when (state) {
            is State.LoadingState -> {
                onLoading()
            }
            is State.ErrorState -> {
                stopLoading()
                onError(state.exception)
            }
            is State.DataState -> {
                stopLoading()
                walletAdapter?.setData(state.data
                    .map { it.toLocal() }
                    .filter { !it.hidden }
                    .map { it.asTransaction() })
                hiddenWalletAdapter?.setData(state.data
                    .map { it.toLocal() }
                    .filter { it.hidden }
                    .map { it.asTransaction() })
            }
        }
    }

    private fun onError(e: Throwable?) {
        btn.changeState(NextCustomButton.State.DEFAULT)

        val notType =
            if (e is IllegalAccessError) NotificationType.INTERNET_PROBLEM_ERROR else NotificationType.UNKNOWN_ERROR
        (requireActivity() as MainActivity).showNotification(notType)
    }

    private fun initViews() {
        layoutIncome = requireView().findViewById(R.id.wallets_income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.wallets_expenses)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        currencyContainer1 = requireView().findViewById(R.id.l_currency_1)
        currencyContainer2 = requireView().findViewById(R.id.l_currency_2)
        currencyContainer3 = requireView().findViewById(R.id.l_currency_3)

        mShimmerViewContainer = requireView().findViewById(R.id.shimmer_container)
        container = requireView().findViewById(R.id.container)
        btn = requireView().findViewById(R.id.btn)

        walletsRecycler = requireView().findViewById(R.id.rv_wallets)
        walletsHiddenRecycler = requireView().findViewById(R.id.rv_wallets_hidden)
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
                                    btn.changeState(NextCustomButton.State.LOADING)
                                }
                                is State.ErrorState -> {
                                    onError(it.exception)
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
        Toast.makeText(requireContext(), "EDIT Clicked $pos \uFDFC", Toast.LENGTH_SHORT).show()
        val wallet  = transactionAdapter.data[pos].asWallet()
        val action = WalletsListFragmentDirections.actionToChangeWallet(wallet)
        findNavController().navigate(action)
    }

    private fun onHideClicked(pos: Int, transactionAdapter: TransactionAdapter) {
        Toast.makeText(requireContext(), "HIDE Clicked $pos \uFDFC", Toast.LENGTH_SHORT).show()
    }
}
