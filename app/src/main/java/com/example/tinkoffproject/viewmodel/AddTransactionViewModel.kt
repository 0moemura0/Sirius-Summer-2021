package com.example.tinkoffproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.request.CreateTransaction
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.to_view.Category
import com.example.tinkoffproject.data.dto.to_view.Transaction
import com.example.tinkoffproject.data.dto.to_view.Wallet
import com.example.tinkoffproject.data.repository.CategoryRepository
import com.example.tinkoffproject.data.repository.TransactionRepository
import com.example.tinkoffproject.ui.main.data.CategoryType
import com.example.tinkoffproject.ui.main.data.SelectableCategory
import com.example.tinkoffproject.utils.toLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    val repository: TransactionRepository, val repositoryCategory: CategoryRepository
) : ViewModel() {

    var wallet: Wallet? = null
    var id: Int = 0
    var type = MutableLiveData<CategoryType>()
    var category = MutableLiveData<Category>()
    var amount = MutableLiveData<Int>()
    var date = MutableLiveData(Date())

    private val categoriesIncome: MutableLiveData<List<Category>> = MutableLiveData()
    private val categoriesExpenses: MutableLiveData<List<Category>> = MutableLiveData()

    val isNextAvailable: MutableLiveData<Boolean> = MutableLiveData(false)

    val selectableCategoriesIncome: LiveData<List<SelectableCategory>> =
        Transformations.map(categoriesIncome) { categories ->
            categories.map { SelectableCategory(category = it, isChecked = category.value == it) }
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
                ).toLocal(),
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
                ).toLocal(),
                CategoryNetwork(
                    name = "Спортзал",
                    iconId = 2,
                    iconColor = "#994747",
                    isIncome = false,
                    id = 13
                ).toLocal(),
            )

    }

    fun editTransaction(
        id: Int
    ): LiveData<State<TransactionNetwork>> {
        val resource = MutableLiveData<State<TransactionNetwork>>(State.LoadingState)
        //TODO logic
//        val disp = repository.editTransaction(id, CreateTransaction())
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    resource.value = State.DataState(it)
//                },
//                {
//                    resource.value = State.ErrorState(it)
//                }
//            )
        return resource
    }

    fun addTransaction(): LiveData<State<TransactionNetwork>> {
        val amount = this.amount.value
        val category = this.category.value
        val type = this.type.value
        val date = this.date.value
        val resource = MutableLiveData<State<TransactionNetwork>>(State.LoadingState)
        if (amount != null && category != null && type != null && date != null) {
            val disp = repository.postTransaction(
                CreateTransaction(
                    value = amount,
                    isIncome = type == CategoryType.INCOME,
                    ts = date.time,
                    currencyShortStr = wallet?.currency?.shortName,
                    walletId = wallet?.id,
                    categoryId = category.id
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        resource.value = State.DataState(it)
                    },
                    {
                        resource.value = State.ErrorState(it)
                        Log.e("TAG", "addTransaction: " + it)
                    }
                )
        } else resource.value = State.ErrorState(IllegalArgumentException("Что-то null"))
        return resource
    }

    fun getCategories(): LiveData<State<List<CategoryNetwork>>> {
        val resource = MutableLiveData<State<List<CategoryNetwork>>>(State.LoadingState)
        val type = this.type.value == CategoryType.INCOME
        val disp = repositoryCategory.getCategoriesByType(type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    resource.value = State.DataState(it)
                },
                {
                    resource.value = State.ErrorState(it)
                }
            )
        return resource
    }

    fun init(transaction: Transaction? = null, _wallet: Wallet? = null) {
        val transactionType =
            if (transaction?.isIncome == true) CategoryType.INCOME else CategoryType.EXPENSE
        wallet = _wallet
        type = MutableLiveData<CategoryType>(transactionType)
        category = MutableLiveData<Category>(transaction?.category)
        amount = MutableLiveData<Int>(transaction?.amount)
        id = (transaction?.id ?: 0)

        //TODO format date
        date = MutableLiveData(Date())

    }
}