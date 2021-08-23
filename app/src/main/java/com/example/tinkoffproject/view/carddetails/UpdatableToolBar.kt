package com.example.tinkoffproject.view.carddetails

enum class ToolbarType(
    val isBackVisible: Boolean,
    val isSettingsVisible: Boolean,
    val isTitleVisible: Boolean,
    val isCloseVisible: Boolean = false
) {
    DEFAULT(true, true, false),
    ADD_OPERATION(true, false, true),
    ADD_VALUE(false, false, false)
}

interface UpdatableToolBar {
    fun updateToolbar(title: String, type: ToolbarType = ToolbarType.DEFAULT)
}
