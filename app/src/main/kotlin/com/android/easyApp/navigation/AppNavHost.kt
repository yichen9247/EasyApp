package com.android.easyApp.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.easyApp.config.AppConfig
import com.android.easyApp.screen.disable.NetErrorScreen
import com.android.easyApp.screen.home.HomeScreen
import com.android.easyApp.screen.login.LoginScreen
import com.android.easyApp.screen.policy.PolicyScreen
import com.android.easyApp.state.GlobalState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.tencent.tauth.Tencent
import top.chengdongqing.weui.core.utils.getIsVpnConnected
import top.chengdongqing.weui.core.utils.isTrue

@Composable
@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
fun AppNavHost() {
    val network = rememberNetworkObserver()
    val navController = rememberNavController()
    GlobalState.navController.value = navController

    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    } else null

    LaunchedEffect(Unit) {
        Tencent.setIsPermissionGranted(true);
        if (!(permissionState?.status?.isGranted.isTrue() || permissionState == null)) {
            permissionState.launchPermissionRequest()
        }
    }

    NavHost(
        navController,
        startDestination = when {
            network.isVpnConnected -> "net_err"
            else -> "login"
        },

        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(AppConfig.animationSpec.toInt())
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(AppConfig.animationSpec.toInt())
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(AppConfig.animationSpec.toInt())
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(AppConfig.animationSpec.toInt())
            )
        }
    ) {
        composable("login") {
            LoginScreen()
        }

        composable("home") {
            HomeScreen()
        }

        composable("policy") {
            PolicyScreen()
        }

        composable("net_err") {
            NetErrorScreen()
        }
    }
}

private data class NetworkInfo(
    val isVpnConnected: Boolean
)

@Composable
private fun rememberNetworkObserver(): NetworkInfo {
    val context = LocalContext.current
    var isVpnConnected by remember { mutableStateOf(getIsVpnConnected()) }
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    DisposableEffect(Unit) {
        val callbackHandler = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                getNetworkStatus()
            }

            override fun onLost(network: Network) {
                getNetworkStatus()
            }

            private fun getNetworkStatus() {
                isVpnConnected = getIsVpnConnected()
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callbackHandler)
        onDispose {
            connectivityManager.unregisterNetworkCallback(callbackHandler)
        }
    }
    return NetworkInfo(isVpnConnected)
}