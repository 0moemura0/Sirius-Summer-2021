package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.request.CreateCategory
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.repository.CategoryRepository
import com.example.tinkoffproject.ui.main.data.CategoryType
import com.example.tinkoffproject.utils.COLOR
import com.example.tinkoffproject.utils.IconEnum
import com.example.tinkoffproject.utils.IconEnum.Companion.fromLocalId
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CustomCategoryViewModel @Inject constructor(val repository: CategoryRepository) :
    ViewModel() {

    var _color = MutableLiveData<COLOR>()

    var name = MutableLiveData<String>()
    var color = MutableLiveData<String>()
    var iconId = MutableLiveData<Int>()
    var type = MutableLiveData<CategoryType>()

    val icons: List<Int> = IconEnum.customLocalsId

    fun init() {
        name = MutableLiveData<String>()
        color = MutableLiveData<String>()
        type = MutableLiveData<CategoryType>()
        iconId = MutableLiveData<Int>()

        _color = MutableLiveData<COLOR>()
    }

    fun isNameOk(): Boolean = name.value != null && !name.value.isNullOrBlank()
    fun isColorOk(): Boolean = color.value != null
    fun isIconOk(): Boolean = iconId.value ?: -1 >= 0
    fun isTypeOk(): Boolean = type.value != null

    fun isAllOk(): Boolean = isColorOk() && isNameOk() && isIconOk() && isTypeOk()

    fun addCategory(): LiveData<State<CategoryNetwork>> {
        val resource = MutableLiveData<State<CategoryNetwork>>(State.LoadingState)
        val name = name.value
        val type = type.value
        val iconId = iconId.value?.let { fromLocalId(it).remoteIconId }
        val color = color.value
        if (name != null && type != null && iconId != null && color != null) {
            val disp = repository.postCategory(
                CreateCategory(
                    name = name,
                    iconId = iconId,
                    isIncome = type == CategoryType.INCOME,
                    iconColor = color
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