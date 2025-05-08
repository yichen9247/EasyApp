package com.android.easyApp.utils

import androidx.navigation.NavController
import com.tencent.mmkv.MMKV
import top.chengdongqing.weui.core.ui.components.dialog.DialogState

object HandUtils {
    val mmkv by lazy { MMKV.defaultMMKV() }

    fun navigateToMain(navController: NavController?) {
        navController?.popBackStack()
        navController?.navigate("home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun openDevelopDialog(dialog: DialogState) {
        dialog.show(
            onCancel = null,
            title = "温馨提示",
            okText = "我已知晓",
            content = "当前功能暂不可用，请留意后续软件更新",
        )
    }

    fun openPrivacyDialog(dialog: DialogState) {
        dialog.show(
            title = "用户协议",
            okText = "我同意此协议",
            content = "为保障您的个人信息权益，请仔细阅读用户隐私协议",
            cancelText = "阅读协议",
            onCancel = {
                RouteUtils.navigationTo("policy")
            },
            onOk = {
                mmkv.encode("policy", true)
            }
        )
    }
}