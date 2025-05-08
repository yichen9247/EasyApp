package com.android.easyApp.tencent.controller

import android.app.Activity
import android.content.Context
import com.android.easyApp.state.GlobalState
import com.android.easyApp.tencent.model.TencentViewModel
import com.android.easyApp.utils.HandUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.chengdongqing.weui.core.ui.components.dialog.DialogState

@OptIn(ExperimentalPermissionsApi::class)
suspend fun handleTencentLogin(
    context: Context,
    dialog: DialogState,
    viewModel: TencentViewModel,
    deviceInfoPermissionState: PermissionState
) = withContext(Dispatchers.Main.immediate) {
    if (GlobalState.tencentListen.value) return@withContext

    if (!deviceInfoPermissionState.status.isGranted) {
        deviceInfoPermissionState.launchPermissionRequest()
        return@withContext
    }

    if (!HandUtils.mmkv.decodeBool("policy", false)) {
        HandUtils.openPrivacyDialog(dialog)
        return@withContext
    }

    if (context is Activity) {
        viewModel.handleLogin(context)
        GlobalState.tencentListen.value = true
    }
}