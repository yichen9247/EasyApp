package com.android.easyApp.model

data class ChatBottomFuncBox(
    val icon: Int,
    val name: String,
    val onClick: () -> Unit = {}
) {}