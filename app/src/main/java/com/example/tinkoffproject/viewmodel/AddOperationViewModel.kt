package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddOperationViewModel : ViewModel() {
    val _type = MutableLiveData<String>()
    val _category = MutableLiveData<String>()
    val _sum = MutableLiveData<Int>()
}