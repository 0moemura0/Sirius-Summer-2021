package com.example.tinkoffproject.viewmodel

import android.util.Log
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

    var id: Long = 0
    var type = MutableLiveData<CategoryType>()
    var category = MutableLiveData<Category>()
    var amount = MutableLiveData<Int>()
    var date = MutableLiveData(Date())

    private val categoriesIncome: MutableLiveData<List<Category>> = MutableLiveData()
    private val categoriesExpenses: MutableLiveData<List<Category>> = MutableLiveData()

    val isNextAvailable: MutableLiveData<Boolean> = MutableLiveData(false)

    val selectableCategoriesIncome: LiveData<List<SelectableCategory>> =
        Transformations.map(categoriesIncome) { categories ->
            categories.map {
                SelectableCategory(category = it, isChecked = category.value == it)
            }
        }
    val selectableCategoriesExpenses: LiveData<List<SelectableCategory>> =
        Transformations.map(categoriesExpenses) { categories ->
            categories.map { SelectableCategory(category = it, isChecked = category.value == it) }
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

        categoriesIncome.value =
            listOf(
                CategoryNetwork(
                    name = "Зарплата",
                    iconId = 1,
                    iconColor = "#00B92D",
                    isIncome = true,
                    id = 11
                ).toCategory(),
            )

    }

    private fun loadExpensesCategories() {
        categoriesExpenses.value =
            listOf(
                CategoryNetwork(
                    name = "Супермаркеты",
                    iconId = 0,
                    iconColor = "#339FEE",
                    isIncome = false,
                    id = 12
                ).toCategory(),
                CategoryNetwork(
                    name = "Спортзал",
                    iconId = 2,
                    iconColor = "#994747",
                    isIncome = false,
                    id = 13
                ).toCategory(),
            )
    }

    fun newTransaction() {
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

    fun init(transaction: Transaction? = null) {
        val transactionType =
            if (transaction?.isIncome == true) CategoryType.INCOME else CategoryType.EXPENSE

        type = MutableLiveData<CategoryType>(transactionType)
        category = MutableLiveData<Category>(transaction?.category)
        amount = MutableLiveData<Int>(transaction?.amount)
        id = transaction?.id ?: 0

        //TODO format date
        date = MutableLiveData(Date())

    }
}