package com.android.easyApp.screen.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.easyApp.R
import com.android.easyApp.state.GlobalState
import com.xuexiang.xutil.XUtil
import top.chengdongqing.weui.core.DefaultInfoPage
import kotlin.system.exitProcess

data class NavItem(val id: String, val title: String, val icon: Int)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen() {
    val frameSelect = GlobalState.localFrame
    var backPressedTime by remember { mutableLongStateOf(0L) }

    Scaffold(
        bottomBar = {
            HomeNavigation()
        },
        topBar = {
            HomeTopAppBar()
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (frameSelect.value) {
                "home" -> DefaultInfoPage("首页界面")
                "user" -> DefaultInfoPage("我的界面")
                else -> DefaultInfoPage("默认界面")
            }
        }
    }

    BackHandler(enabled = true) {
        if (frameSelect.value != "home") {
            frameSelect.value = "home"
            return@BackHandler
        }
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime > 2000) {
            backPressedTime = currentTime
            Toast.makeText(XUtil.getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show()
        } else exitProcess(0)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopAppBar() {
    val frameSelect = GlobalState.localFrame
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        title = {
            Text(
                text = when (frameSelect.value) {
                    "home" -> "首页"
                    else -> "未知"
                }
            )
        }
    )
}

@Composable
fun HomeNavigation() {
    val frameSelect = GlobalState.localFrame
    val items = listOf(
        NavItem("home", "首页", R.drawable.ic_action_home),
        NavItem("other1", "未知", R.drawable.ic_more_apps),
        NavItem("other2", "未知", R.drawable.ic_more_apps),
        NavItem("other3", "未知", R.drawable.ic_more_apps),
        NavItem("user", "我的", R.drawable.ic_action_person)
    )

    NavigationBar(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            contentDescription = item.title,
                            painter = painterResource(id = item.icon),
                        )
                    },
                    label = { Text(text = item.title) },
                    selected = frameSelect.value == item.id,
                    onClick = { frameSelect.value = item.id },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}