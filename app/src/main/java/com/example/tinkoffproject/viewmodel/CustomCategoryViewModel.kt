package com.example.tinkoffproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.mapper.toNetwork
import com.example.tinkoffproject.view.data.CategoryType

class CustomCategoryViewModel(application: Application) : AndroidViewModel(application) {
    var name = MutableLiveData<String>()
    var colorId = MutableLiveData<Int>()
    var iconId = MutableLiveData<Int>()
    var type = MutableLiveData<CategoryType>()

    val icons: List<Int> = listOf(
        R.drawable.ic_shop,
        R.drawable.ic_plain,
        R.drawable.ic_diamond,
        R.drawable.ic_cafe,
        R.drawable.ic_medicine,
        R.drawable.ic_cup,
        R.drawable.ic_wi_fi,
        R.drawable.ic_theather,
        R.drawable.ic_train,
        R.drawable.ic_pet,
        R.drawable.ic_sport,
        R.drawable.ic_net,
        R.drawable.ic_bus,
        R.drawable.ic_palma,
        R.drawable.ic_hands,
        R.drawable.ic_music,
        R.drawable.ic_cap,
        R.drawable.ic_gift,
        R.drawable.ic_phone,
        R.drawable.ic_garden,
        R.drawable.ic_movie,
        R.drawable.ic_car,
        R.drawable.ic_medicine_bag,
        R.drawable.ic_education,
        R.drawable.ic_tv,
        R.drawable.ic_icon,
        R.drawable.ic_wear,
        R.drawable.ic_money,
        R.drawable.ic_balloon,
        R.drawable.ic_dots
    )


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