package com.example.tinkoffproject.view.carddetails

enum class ToolbarType(
    val isBackVisible: Boolean,
    val isSettingsVisible: Boolean,
    val isTitleVisible: Boolean
) {
    DEFAULT(true, true, false),
    ADD_OPERATION(true, false, true)
}

interface UpdatableToolBar {
    fun updateToolbar(title: String, type: ToolbarType = ToolbarType.DEFAULT)
}
