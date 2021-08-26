package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.request.CreateCategory
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.repository.CategoryRepository
import com.example.tinkoffproject.ui.main.data.CategoryType
import com.example.tinkoffproject.utils.IconEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CustomCategoryViewModel @Inject constructor(val repository: CategoryRepository) :
    ViewModel() {
    var name = MutableLiveData<String>()
    var color = MutableLiveData<Int>()
    var iconId = MutableLiveData<Int>()
    var type = MutableLiveData<CategoryType>()

    val icons: List<Int> = IconEnum.customLocalsId

    fun init() {
        name = MutableLiveData<String>()
        color = MutableLiveData<Int>()
        type = MutableLiveData<CategoryType>()
    }

    fun addCategory(): LiveData<State<CategoryNetwork>> {
        val resource = MutableLiveData<State<CategoryNetwork>>(State.LoadingState)
        val name = name.value
        val type = type.value
        val iconId = iconId.value
        val color = color.value
        if (name != null && type != null && iconId != null && color != null) {
            val disp = repository.postCategory(
                CreateCategory(
                    name = name,
                    iconId = iconId,
                    isIncome = type == CategoryType.INCOME,
                    iconColor = color.toString()
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
}