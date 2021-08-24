package com.example.tinkoffproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.mapper.toNetwork
import com.example.tinkoffproject.model.data.network.ApiService
import com.example.tinkoffproject.view.data.CategoryType

class AddCategoryViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var apiService: ApiService

    var type = MutableLiveData(CategoryType.INCOME)
    var name = MutableLiveData(application.getString(R.string.category_name_default))
    var iconId: MutableLiveData<Int> = MutableLiveData()
    var color: MutableLiveData<Int> = MutableLiveData()

    val isNextAvailable = MutableLiveData<Boolean>()

    init {
        init()
    }
    fun init(){
        type = MutableLiveData(CategoryType.INCOME)
        name = MutableLiveData(getApplication<Application>().getString(R.string.category_name_default))
        iconId = MutableLiveData()
        color = MutableLiveData()
    }


    fun addCategory() {
        val name = name.value
        val type = type.value
        val iconId = iconId.value
        val color = color.value
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