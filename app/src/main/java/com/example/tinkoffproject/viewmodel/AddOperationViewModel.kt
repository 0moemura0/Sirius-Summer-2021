package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.mapper.toCategory
import com.example.tinkoffproject.model.data.mapper.toNetwork
import com.example.tinkoffproject.model.data.network.ApiService
import com.example.tinkoffproject.model.data.network.dto.response.CategoryNetwork
import com.example.tinkoffproject.model.utils.formatMoney
import com.example.tinkoffproject.view.data.CategoryType
import com.example.tinkoffproject.view.data.SelectableCategory
import java.util.*

class AddOperationViewModel : ViewModel() {
    private lateinit var apiService: ApiService

    var type = MutableLiveData<CategoryType>()
    var category = MutableLiveData<Category>()
    var amount = MutableLiveData<Int>()

    var date = MutableLiveData(Date())

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


    init {
        init()
    }
    fun setCategory(position: Int) {
        val newCategory: Category? = when (type.value) {
            CategoryType.INCOME -> categoriesIncome.value?.get(position)
            CategoryType.EXPENSE -> categoriesExpenses.value?.get(position)
            else -> null
        }

        if (newCategory != null)
            category.value = newCategory!!
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
                    CategoryNetwork("Зарплата", 1, "#00B92D", isIncome = true).toCategory(),
                )
        }
    }

    private fun loadExpensesCategories() {
        if (categoriesExpenses.value == null) {
            categoriesExpenses.value =
                listOf(
                    CategoryNetwork("Супермаркеты", 0, "#339FEE", isIncome = false).toCategory(),
                    CategoryNetwork("Спортзал", 2, "#994747", isIncome = false).toCategory(),
                )
        }
    }

    fun newTransaction(){
    }

    fun addTransaction() {
        val amount = this.amount.value
        val category = this.category.value
        val type = this.type.value
        val date = this.date.value

        if (amount != null && category != null && type != null && date != null)
            Transaction(
                id = 0,
                amount = amount,
                category = category,
                date = date.time,
                isIncome = type == CategoryType.INCOME,
                amountFormatted = formatMoney(amount)
            ).toNetwork()
    }

    fun init(){
        type = MutableLiveData<CategoryType>()
        category = MutableLiveData<Category>()
        amount = MutableLiveData<Int>()

        date = MutableLiveData(Date())

    }
}