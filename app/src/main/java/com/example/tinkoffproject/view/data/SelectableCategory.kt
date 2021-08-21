package com.example.tinkoffproject.view.data

import com.example.tinkoffproject.model.data.dto.Category

data class SelectableCategory(
    val category: Category,
    var isChecked: Boolean = false
)
