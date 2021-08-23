package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.adapter.category.CategoryAdapter
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.view.data.CategoryType
import com.example.tinkoffproject.view.data.OnItemSelectListener
import com.example.tinkoffproject.viewmodel.AddOperationViewModel

class ChooseCategoryFragment : Fragment(R.layout.operation_choose_category) {
    private val viewModel: AddOperationViewModel by activityViewModels()
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadCategories()

        setupNavigation()
        setupData()
        setupRecyclerView()
        setupNextButton()
        setupToolbar()
    }

    private fun openChooseColor(){
        //TODO
    }

    private fun setupNavigation(){
        /*requireView().findViewById<View>(R.id.dtn_add_category).setOnClickListener {
            findNavController().navigate(R.id.action_chooseCategoryFragment_to_newCategoryFragment)
        }*/
    private fun setupData() {
        viewModel.category.observe(viewLifecycleOwner, {
            viewModel.isNextAvailable.value = true
        })
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (viewModel.isNextAvailable.value == true) {
                findNavController().navigate(R.id.action_chooseCategoryFragment_to_newOperationFragment)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupRecyclerView() {
        val recycler: RecyclerView = requireView().findViewById(R.id.rv_category)
        categoryAdapter.apply {
            when (viewModel.type.value) {
                CategoryType.INCOME -> viewModel.selectableCategoriesIncome
                CategoryType.EXPENSE -> viewModel.selectableCategoriesExpenses
                else -> viewModel.selectableCategoriesIncome
            }.observe(viewLifecycleOwner, { setData(it) })
            setOnItemClickListener(object : OnItemSelectListener {
                override fun onItemSelect(position: Int) {
                    viewModel.setCategory(position)
                }
            })
        }

        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.choose_category), ToolbarType.ADD_OPERATION)
    }

}