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

class AddOperationViewModel : ViewModel() {
    private lateinit var apiService: ApiService

    val isIncome = MutableLiveData<CategoryType>()
    val category = MutableLiveData<Category>()
    val amount = MutableLiveData<Int>()

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
        category.value = categoriesIncome.value?.get(position)
    }

    fun prepareNext() {
        isNextAvailable.value = false
    }

    fun loadIncomeCategories() {
        if (categoriesIncome.value == null) {
            categoriesIncome.value =
                listOf(CategoryNetwork("Супермаркет", 0, "#F52222").toCategory())
        }
    }

    fun loadExpensesCategories() {
        if (categoriesExpenses.value == null) {
            categoriesExpenses.value =
                listOf(CategoryNetwork("Зарплата", 1, "#00B92D").toCategory())
        }
    }
}