package com.android.easyApp.screen.login.controller

import com.android.easyApp.config.AppConfig
import com.android.easyApp.state.GlobalState
import com.android.easyApp.utils.HandUtils
import kotlinx.coroutines.*
import top.chengdongqing.weui.core.ui.components.dialog.DialogState
import top.chengdongqing.weui.core.ui.components.toast.ToastIcon
import top.chengdongqing.weui.core.ui.components.toast.ToastState
import top.chengdongqing.weui.core.utils.handleGenericError
import top.chengdongqing.weui.core.utils.showToast
import top.chengdongqing.weui.core.utils.types.ToastType
import kotlin.time.Duration

suspend fun userLogin(
    toast: ToastState,
    dialog: DialogState,
    auto: Boolean = false,
    data: Map<String, String>,
) = withContext(Dispatchers.Main.immediate) {
    val username = data["username"].toString()
    val password = data["password"].toString()

    if (!HandUtils.mmkv.decodeBool("policy", false)) {
        HandUtils.openPrivacyDialog(dialog)
        return@withContext
    }

    if (username.isEmpty() || password.isEmpty()) {
        showToast(
            type = ToastType.WARNING,
            message = "账号或密码不能为空"
        )
        return@withContext
    }

    toast.show(
        mask = true,
        title = "正在登录中",
        icon = ToastIcon.LOADING,
        duration = Duration.INFINITE
    )

    try {
        HandUtils.mmkv.encode("login", true)
        HandUtils.mmkv.encode("username", username)
        HandUtils.mmkv.encode("password", password)

        delay(AppConfig.animationSpec)
        if (!auto) showToast(
            type = ToastType.SUCCESS,
            message = "登录成功"
        )
        HandUtils.navigateToMain(GlobalState.navController.value)
    } catch (e: Exception) {
        handleGenericError(e)
    } finally {
        toast.hide()
    }
}

suspend fun userAutoLogin(
    toast: ToastState,
    dialog: DialogState
) = withContext(Dispatchers.Main.immediate) {
    userLogin(
        auto = true,
        data = mapOf(
            "username" to HandUtils.mmkv.decodeString("username", "").toString(),
            "password" to HandUtils.mmkv.decodeString("password", "").toString()
        ),
        toast = toast,
        dialog = dialog
    )
}

suspend fun userLoginWithQQ(toast: ToastState) = withContext(Dispatchers.Main.immediate) {
    try {

    } catch (e: Exception) {
        handleGenericError(e)
    } finally {
        toast.hide()
    }
}