package com.android.easyApp.state

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController

object GlobalState {
    val localFrame = mutableStateOf("home")
    val isAppForGround = mutableStateOf(true)
    val tencentListen = mutableStateOf(false)
    val navController = mutableStateOf<NavController?>(null)
}