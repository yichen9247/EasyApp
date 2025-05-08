package com.android.easyApp.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.easyApp.component.TitleHeader
import com.android.easyApp.screen.login.controller.userAutoLogin
import com.android.easyApp.screen.login.controller.userLogin
import com.android.easyApp.tencent.FastLogin
import com.android.easyApp.utils.HandUtils
import com.android.easyApp.utils.RouteUtils
import kotlinx.coroutines.*
import top.chengdongqing.weui.core.ui.components.dialog.rememberDialogState
import top.chengdongqing.weui.core.ui.components.input.WeInput
import top.chengdongqing.weui.core.ui.components.toast.ToastState
import top.chengdongqing.weui.core.ui.components.toast.rememberToastState
import top.chengdongqing.weui.core.ui.theme.FontLinkColor
import top.chengdongqing.weui.core.utils.openUrlInBrowse

@Composable
fun LoginScreen() {
    val toast = rememberToastState()
    val dialog = rememberDialogState()

    LaunchedEffect(Unit) {
        if (HandUtils.mmkv.decodeBool("login", false)) {
            launch {
                userAutoLogin(
                    toast = toast,
                    dialog = dialog
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            item {
                TitleHeader(
                    name = "用户登录",
                    desc = "欢迎回来，全端服务已就绪，跨端数据实时同步，随时随地畅享无缝体验"
                )
            }
            item { LoginBody(toast = toast) }
        }
    }
    FastLogin()
}

@Composable
private fun LoginBody(toast: ToastState) {
    val coroutineScope = rememberCoroutineScope()
    var username by remember { mutableStateOf(HandUtils.mmkv.decodeString("username", "")) }
    var password by remember { mutableStateOf(HandUtils.mmkv.decodeString("password", "")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeInput(
            value = username,
            label = "账号",
            placeholder = "请输入账号",
        ) { username = it }

        WeInput(
            value = password,
            label = "密码",
            placeholder = "请输入密码",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        ) { password = it }

        Spacer(modifier = Modifier.height(20.dp))
        LinksRow()
        Spacer(modifier = Modifier.height(20.dp))

        LoginButton(
            toast = toast,
            username = username.toString(),
            password = password.toString(),
            coroutineScope = coroutineScope
        )
    }
}

@Composable
private fun LinksRow() {
    val dialog = rememberDialogState()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            fontSize = 16.sp,
            text = "立即注册",
            color = FontLinkColor,
            modifier = Modifier.clickable {
                RouteUtils.navigationTo("register")
            }
        )

        Text(
            fontSize = 16.sp,
            text = "忘记密码",
            color = FontLinkColor,
            modifier = Modifier.clickable {
                dialog.show(
                    title = "忘记密码",
                    okText = "联系管理员",
                    content = "暂不提供在线密码重置服务，请联系平台管理员",
                    cancelText = "知道了",
                    onOk = {
                        openUrlInBrowse("https://qm.qq.com/q/AKdIXRHMBM")
                    }
                )
            }
        )
    }
}

@Composable
private fun LoginButton(
    username: String,
    password: String,
    toast: ToastState,
    coroutineScope: CoroutineScope
) {
    val dialog = rememberDialogState()
    Button(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        onClick = {
            coroutineScope.launch {
                userLogin(
                    toast = toast,
                    data = mapOf(
                        "username" to username,
                        "password" to password
                    ),
                    dialog = dialog
                )
            }
        }
    ) {
        Text(
            text = "立即登录",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}