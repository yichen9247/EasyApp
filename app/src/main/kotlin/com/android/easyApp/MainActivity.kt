package com.android.easyApp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.easyApp.core.CrashHandler
import com.android.easyApp.navigation.AppNavHost
import com.android.easyApp.tencent.model.TencentViewModel
import com.tencent.tauth.Tencent
import top.chengdongqing.weui.core.ui.theme.WeUITheme

class MainActivity : ComponentActivity() {
    private val viewModel: TencentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTencent()
        initEasyApp()
        setContent {
            WeUITheme {
                AppNavHost()
            }
        }
    }

    private fun initEasyApp() {
        CrashHandler()
        installSplashScreen()
    }

    private fun initTencent() {
        val authorities = "com.android.easyApp.provider"
        Tencent.createInstance("0", this.applicationContext, authorities);
    }

    @Deprecated("Should use Activity Result API", level = DeprecationLevel.HIDDEN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handleActivityResult(requestCode, resultCode, data)
    }
}
