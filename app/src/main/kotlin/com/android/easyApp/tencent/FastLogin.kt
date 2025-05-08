package com.android.easyApp.tencent

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.easyApp.R
import com.android.easyApp.screen.login.controller.userLoginWithQQ
import com.android.easyApp.state.GlobalState
import com.android.easyApp.tencent.controller.handleTencentLogin
import com.android.easyApp.tencent.model.TencentViewModel
import com.android.easyApp.tencent.state.TencentState
import com.android.easyApp.utils.HandUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import top.chengdongqing.weui.core.ui.components.dialog.rememberDialogState
import top.chengdongqing.weui.core.ui.components.toast.rememberToastState
import top.chengdongqing.weui.core.utils.showToast
import top.chengdongqing.weui.core.utils.types.ToastType

@Composable
fun FastLogin() {
    TencentListener()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absoluteOffset(0.dp, 0.dp)
            .padding(40.dp, 0.dp, 40.dp, 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(0.5.dp)
                        .background(Color(0XFFE3E8F7))
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    fontSize = 12.sp,
                    text = "其它登录方式",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(15.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(0.5.dp)
                        .background(Color(0XFFE3E8F7))
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoginMethodBox(
                    key = "qq",
                    painter = R.drawable.ic_login_qq
                )
                Spacer(modifier = Modifier.width(25.dp))
                LoginMethodBox(
                    key = "wechat",
                    painter = R.drawable.ic_login_wechat
                )
                Spacer(modifier = Modifier.width(25.dp))
                LoginMethodBox(
                    key = "weibo",
                    painter = R.drawable.ic_login_weibo
                )
                Spacer(modifier = Modifier.width(25.dp))
                LoginMethodBox(
                    key = "alipay",
                    painter = R.drawable.ic_login_alipay
                )
                Spacer(modifier = Modifier.width(25.dp))
                LoginMethodBox(
                    key = "netease",
                    painter = R.drawable.ic_login_netease
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class, DelicateCoroutinesApi::class)
private fun LoginMethodBox(
    key: String,
    painter: Int,
    viewModel: TencentViewModel = viewModel()
) {
    val dialog = rememberDialogState()
    val context = LocalContext.current
    val deviceInfoPermissionState = rememberPermissionState(
        Manifest.permission.READ_PHONE_STATE
    )

    Image(
        modifier = Modifier
            .size(35.dp)
            .clickable(
                onClick = {
                    GlobalScope.launch {
                        when(key) {
                            "qq" -> handleTencentLogin(
                                dialog = dialog,
                                context = context,
                                viewModel = viewModel,
                                deviceInfoPermissionState = deviceInfoPermissionState
                            )
                            else -> HandUtils.openDevelopDialog(dialog)
                        }
                    }
                },
                indication = null,
                interactionSource = null
            ),
        painter = painterResource(painter),
        contentDescription = "Login Method"
    )
}

@Composable
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
private fun TencentListener(viewModel: TencentViewModel = viewModel()) {
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is TencentState.Success -> {
                if (GlobalState.tencentListen.value) GlobalScope.launch {
                    showToast(
                        type = ToastType.ERROR,
                        message = "登录成功"
                    )
                }
                viewModel.clearTencentListenFlag()
            }
            is TencentState.Error -> {
                if (GlobalState.tencentListen.value) {
                    showToast(
                        type = ToastType.ERROR,
                        message = "登录失败"
                    )
                }
                viewModel.clearTencentListenFlag()
            }
            else -> {}
        }
    }
}