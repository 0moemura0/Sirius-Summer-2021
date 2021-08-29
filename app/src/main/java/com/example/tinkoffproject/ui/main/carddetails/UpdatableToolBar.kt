package com.example.tinkoffproject.ui.main.carddetails

enum class ToolbarType(
    val isBackVisible: Boolean = false,
    val isSettingsVisible: Boolean = false,
    val isTitleVisible: Boolean = false,
    val isCloseVisible: Boolean = false
) {
    DEFAULT(true, true, false),
    ADD_OPERATION(true, false, true),
    ADD_VALUE(isCloseVisible = true),
    INVISIBLE
}

interface UpdatableToolBar {
    fun updateToolbar(title: String, type: ToolbarType = ToolbarType.DEFAULT)
}
