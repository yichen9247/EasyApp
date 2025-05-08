package com.android.easyApp.utils

import androidx.compose.runtime.mutableLongStateOf
import androidx.navigation.NavController
import com.android.easyApp.state.GlobalState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object RouteUtils {
    private val lastBackClickTime = mutableLongStateOf(0L)
    private val lastLinkClickTime = mutableLongStateOf(0L)
    private val navController: NavController? get() = GlobalState.navController.value

    fun navigationTo(route: String) {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastLinkClickTime.longValue) > 500) {
            lastLinkClickTime.longValue = currentTime
            CoroutineScope(Dispatchers.Main).launch {
                navController?.navigate(route)
            }
        }
    }

    fun navigationBack() {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastBackClickTime.longValue) > 500) {
            lastBackClickTime.longValue = currentTime
            CoroutineScope(Dispatchers.Main).launch {
                navController?.popBackStack()
            }
        }
    }
}