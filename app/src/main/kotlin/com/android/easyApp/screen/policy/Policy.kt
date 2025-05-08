package com.android.easyApp.screen.policy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.easyApp.R
import com.android.easyApp.component.SubPageToolbar
import com.android.easyApp.config.PolicyConfig

@Composable
fun PolicyScreen() {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            SubPageToolbar(
                name = "政策"
            )
        }
    ) { paddingValues ->
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(state = scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            PolicyBody()
        }
    }
}

@Composable
private fun PolicyBody() {
    SelectionContainer {
        Text(
            fontSize = 16.sp,
            modifier = Modifier
                .padding(15.dp),
            textAlign = TextAlign.Justify,
            text = PolicyConfig.userPolicy,
            color = MaterialTheme.policyColorScheme.descColor
        )
    }
}

private data class PolicyColors(
    val descColor: Color,
    val titleColor: Color,
)

private val MaterialTheme.policyColorScheme: PolicyColors
    @Composable
    get() = PolicyColors(
        descColor = colorScheme.onSecondary,
        titleColor = colorResource(R.color.title_color)
    )