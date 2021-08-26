package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.to_view.Category
import com.example.tinkoffproject.data.dto.request.CreateTransaction
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.repository.CategoryRepository
import com.example.tinkoffproject.data.repository.TransactionRepository
import com.example.tinkoffproject.ui.main.data.CategoryType
import com.example.tinkoffproject.ui.main.data.SelectableCategory
import com.example.tinkoffproject.utils.toCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddOperationViewModel @Inject constructor(
    val repository: TransactionRepository, val repositoryCategory: CategoryRepository
) : ViewModel() {

    var wallet: WalletNetwork? = null
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
                    CategoryNetwork(
                        name = "Зарплата",
                        iconId = 1,
                        iconColor = "#00B92D",
                        isIncome = true,
                        id = 11
                    ).toCategory(),
                )
        }
    }

    private fun loadExpensesCategories() {
        if (categoriesExpenses.value == null) {
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
                    value = amount.toDouble(),
                    isIncome = type == CategoryType.INCOME,
                    ts = date.time.toString(),
                    currencyShortStr = wallet?.currency?.shortStr,
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

    fun init() {
        type = MutableLiveData<CategoryType>()
        category = MutableLiveData<Category>()
        amount = MutableLiveData<Int>()
        date = MutableLiveData(Date())

    }
}