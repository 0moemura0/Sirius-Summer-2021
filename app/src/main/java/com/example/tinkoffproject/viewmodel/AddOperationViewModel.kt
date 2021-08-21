package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.mapper.toCategory
import com.example.tinkoffproject.model.data.network.ApiService
import com.example.tinkoffproject.model.data.network.dto.CategoryNetwork
import com.example.tinkoffproject.view.data.CategoryType
import com.example.tinkoffproject.view.data.SelectableCategory
import java.util.*

class AddOperationViewModel : ViewModel() {
    private lateinit var apiService: ApiService

    val type = MutableLiveData<CategoryType>()
    val category = MutableLiveData<Category>()
    val amount = MutableLiveData<Int>()

    val date = MutableLiveData<Date>(Date())

    private val categoriesIncome: MutableLiveData<List<Category>> = MutableLiveData()
    private val categoriesExpenses: MutableLiveData<List<Category>> = MutableLiveData()

    val isNextAvailable: MutableLiveData<Boolean> = MutableLiveData(false)

    val selectableCategoriesIncome: LiveData<List<SelectableCategory>> =
        Transformations.map(categoriesIncome) { categories ->
            categories.map { SelectableCategory(category = it) }
        }
    val selectableCategoriesExpenses: LiveData<List<SelectableCategory>> =
        Transformations.map(categoriesExpenses) { categories ->
            categories.map { SelectableCategory(category = it) }
        }

    fun setCategory(position: Int) {
        val newCategory: Category? = when (type.value) {
            CategoryType.INCOME -> categoriesIncome.value?.get(position)
            CategoryType.EXPENSE -> categoriesExpenses.value?.get(position)
            else -> null
        }

        if (newCategory != null)
            category.value = newCategory
    }

    fun loadCategories() {
        when (type.value) {
            CategoryType.INCOME -> loadIncomeCategories()
            CategoryType.EXPENSE -> loadExpensesCategories()
        }
    }

    private fun loadIncomeCategories() {
        if (categoriesIncome.value == null) {
            categoriesIncome.value =
                listOf(
                    CategoryNetwork("Зарплата", 1, "#00B92D").toCategory(),
                )
        }
    }

    private fun loadExpensesCategories() {
        if (categoriesExpenses.value == null) {
            categoriesExpenses.value =
                listOf(
                    CategoryNetwork("Супермаркеты", 0, "#339FEE").toCategory(),
                    CategoryNetwork("Спортзал", 2, "#994747").toCategory(),
                )
        }
    }

    fun newTransaction(){
    }

    fun addTransaction() {
        // TODO нельзя использовать Transaction, но использовать TransactionNetwork неправильно
    }
}