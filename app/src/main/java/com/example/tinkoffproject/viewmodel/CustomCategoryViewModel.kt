package com.example.tinkoffproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.mapper.CategoryEnum
import com.example.tinkoffproject.model.data.mapper.toNetwork
import com.example.tinkoffproject.view.data.CategoryType

class CustomCategoryViewModel(application: Application) : AndroidViewModel(application) {
    var name = MutableLiveData<String>()
    var colorId = MutableLiveData<Int>()
    var iconId = MutableLiveData<Int>()
    var type = MutableLiveData<CategoryType>()

    val icons: List<Int> = CategoryEnum.locals


    fun init() {
        name = MutableLiveData<String>()
        colorId = MutableLiveData<Int>()
        type = MutableLiveData<CategoryType>()
    }

    fun addCategory() {
        val name = name.value
        val type = type.value
        val iconId = iconId.value
        val color = colorId.value
        if (name != null && type != null && iconId != null && color != null) {
            Category(
                name = name,
                resIconId = iconId,
                isIncome = type == CategoryType.INCOME,
                color = color
            ).toNetwork()
        } else onCategoryAddFailed()
    }

    private fun onCategoryAddFailed(e: Exception? = null) {

    }
}