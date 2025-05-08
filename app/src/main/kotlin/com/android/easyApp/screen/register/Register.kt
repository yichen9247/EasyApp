package com.android.easyApp.screen.register

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
import com.android.easyApp.screen.register.controller.userRegister
import com.android.easyApp.tencent.FastLogin
import com.android.easyApp.utils.RouteUtils
import kotlinx.coroutines.*
import top.chengdongqing.weui.core.ui.components.dialog.rememberDialogState
import top.chengdongqing.weui.core.ui.components.input.WeInput
import top.chengdongqing.weui.core.ui.components.toast.rememberToastState
import top.chengdongqing.weui.core.ui.theme.FontLinkColor

@Composable
fun RegisterScreen() {
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
                    name = "用户注册",
                    desc = "欢迎回来，全端服务已就绪，跨端数据实时同步，随时随地畅享无缝体验"
                )
            }
            item { RegisterBody() }
        }
    }
    FastLogin()
}

@Composable
private fun RegisterBody() {
    val coroutineScope = rememberCoroutineScope()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeInput(
            value = username,
            label = "账号",
            labelWidth = 84.dp,
            placeholder = "请输入账号"
        ) { username = it }

        WeInput(
            value = password,
            label = "密码",
            labelWidth = 84.dp,
            placeholder = "请输入密码",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        ) { password = it }

        WeInput(
            value = rePassword,
            label = "重复密码",
            labelWidth = 84.dp,
            placeholder = "请再次输入密码",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        ) { rePassword = it }

        Spacer(modifier = Modifier.height(20.dp))

        LinksRow()

        Spacer(modifier = Modifier.height(20.dp))

        RegisterButton(
            username = username,
            password = password,
            rePassword = rePassword,
            coroutineScope = coroutineScope
        )
    }
}

@Composable
private fun LinksRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            fontSize = 16.sp,
            text = "返回登录",
            color = FontLinkColor,
            modifier = Modifier.clickable {
                RouteUtils.navigationBack()
            }
        )
    }
}

@Composable
private fun RegisterButton(
    username: String,
    password: String,
    rePassword: String,
    coroutineScope: CoroutineScope
) {
    val toast = rememberToastState()
    val dialog = rememberDialogState()

    Button(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        onClick = {
            coroutineScope.launch {
                userRegister(
                    toast = toast,
                    data = mapOf(
                        "username" to username,
                        "password" to password,
                        "rePassword" to rePassword
                    ),
                    dialog = dialog
                )
            }
        }
    ) {
        Text(
            text = "立即注册",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}