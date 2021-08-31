package com.example.tinkoffproject.ui.main.operation_add

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.dto.to_view.Category
import com.example.tinkoffproject.ui.main.adapter.category.CategoryAdapter
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditFragment
import com.example.tinkoffproject.ui.main.data.CategoryType
import com.example.tinkoffproject.viewmodel.AddTransactionViewModel

class ChooseCategoryFragment
    : EditFragment(R.layout.operation_choose_category, R.string.choose_category) {
    val viewModel: AddTransactionViewModel by activityViewModels()
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter() }

    private val args: ChooseCategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupCreateCategoryBtn()
        setupNextButton()
        setupBtnObserver()

        setupData()
    }

    private fun setupData() {
        viewModel.getCategories().observe(viewLifecycleOwner, ::onCategoriesUpdate)

        val isIncome = viewModel.type.value == CategoryType.INCOME
        if (isIncome) {
            viewModel.selectableCategoriesIncome.observe(viewLifecycleOwner, {
                categoryAdapter.setData(it)
            })
        } else {
            viewModel.selectableCategoriesExpenses.observe(viewLifecycleOwner, {
                categoryAdapter.setData(it)
            })
        }
    }

    private fun onCategoriesUpdate(state: State<List<CategoryNetwork>>) {
        when (state) {
            is State.LoadingState -> {
                onLoading()
            }
            is State.ErrorState -> {
                onInternetError(state.exception)
            }
            is State.DataState -> {
                viewModel.categories().value = state.data
                updateButtonState()

                val indexSelected = viewModel.categories().value
                    ?.indexOfFirst { it.id == getValue()?.id }
                    ?: -1
                if (indexSelected >= 0) categoryAdapter.currentSelected = indexSelected
            }
        }
    }


    private fun setupCreateCategoryBtn() {
        val createTextView: TextView = requireView().findViewById(R.id.tv_create)
        createTextView.setOnClickListener {
            val action = ChooseCategoryFragmentDirections.actionToChooseNewCategoryType(
                true,
                viewModel.type.value == CategoryType.INCOME
            )
            findNavController().navigate(action)
        }
    }

    override fun setupNextButtonImpl() {
        val action =
            if (args.isFromMain) R.id.action_to_newOperation else R.id.action_chooseTransactionCategory_to_newOperation

        setupNextButton(
            resId = action,
            context = context
        )
    }

    override fun getValue(): Category? = viewModel.category.value

    override fun isNextAvailable() = getValue() != null

    private fun setupBtnObserver() {
        viewModel.category.observe(viewLifecycleOwner, {
            updateButtonState()
        })
    }


    private fun setupRecyclerView() {
        val recycler: RecyclerView = requireView().findViewById(R.id.rv_category)
        categoryAdapter.apply {
            when (viewModel.type.value) {
                CategoryType.INCOME -> viewModel.selectableCategoriesIncome
                CategoryType.EXPENSE -> viewModel.selectableCategoriesExpenses
                else -> viewModel.selectableCategoriesIncome
            }.observe(viewLifecycleOwner, { setData(it) })
            setOnItemClickListener { position -> viewModel.setCategory(position) }
        }

        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }
    }
}