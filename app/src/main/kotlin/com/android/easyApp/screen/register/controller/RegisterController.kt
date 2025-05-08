package com.android.easyApp.screen.register.controller

import com.android.easyApp.state.GlobalState
import com.android.easyApp.utils.HandUtils
import com.android.easyApp.utils.RouteUtils
import kotlinx.coroutines.*
import top.chengdongqing.weui.core.ui.components.dialog.DialogState
import top.chengdongqing.weui.core.ui.components.toast.ToastIcon
import top.chengdongqing.weui.core.ui.components.toast.ToastState
import top.chengdongqing.weui.core.utils.handleGenericError
import top.chengdongqing.weui.core.utils.showToast
import top.chengdongqing.weui.core.utils.types.ToastType
import kotlin.time.Duration

suspend fun userRegister(
    toast: ToastState,
    dialog: DialogState,
    data: Map<String, String>,
) = withContext(Dispatchers.Main.immediate) {
    val username = data["username"].toString()
    val password = data["password"].toString()
    val rePassword = data["rePassword"].toString()

    if (!HandUtils.mmkv.decodeBool("policy", false)) {
        dialog.show(
            title = "用户协议",
            okText = "我同意此协议",
            content = "为保障您的个人信息权益，请仔细阅读用户隐私协议",
            cancelText = "阅读协议",
            onCancel = {
                RouteUtils.navigationTo("policy")
            },
            onOk = {
                HandUtils.mmkv.encode("policy", true)
            }
        )
        return@withContext
    }

    if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
        showToast(
            type = ToastType.WARNING,
            message = "账号或密码不能为空"
        )
        return@withContext
    } else if (password != rePassword) {
        showToast(
            type = ToastType.WARNING,
            message = "两次输入密码不一致"
        )
        return@withContext
    }

    toast.show(
        mask = true,
        title = "正在注册中",
        icon = ToastIcon.LOADING,
        duration = Duration.INFINITE
    )

    try {
        HandUtils.mmkv.encode("login", true)
        HandUtils.mmkv.encode("username", username)
        HandUtils.mmkv.encode("password", password)
        showToast(
            type = ToastType.SUCCESS,
            message = "注册成功"
        )
        HandUtils.navigateToMain(GlobalState.navController.value)
    } catch (e: Exception) {
        handleGenericError(e)
    } finally {
        toast.hide()
    }
}