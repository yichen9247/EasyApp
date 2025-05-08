package com.android.easyApp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.easyApp.utils.RouteUtils

@Composable
fun TitleHeader(name: String, desc: String) {
    Column(Modifier.padding(20.dp, 40.dp)) {
        Text(
            text = name,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.secondary,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = desc,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SubPageToolbar(
    name: String,
    action: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                RouteUtils.navigationBack()
            }) {
                Icon(
                    contentDescription = "返回",
                    tint = MaterialTheme.colorScheme.secondary,
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack
                )
            }
        },
        actions = action
    )
}