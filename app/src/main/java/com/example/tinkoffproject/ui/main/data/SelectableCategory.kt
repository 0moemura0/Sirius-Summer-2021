package com.example.tinkoffproject.ui.main.data

import com.example.tinkoffproject.data.dto.to_view.Category


data class SelectableCategory(
    val category: Category,
    var isChecked: Boolean = false
)
