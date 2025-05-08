package com.android.easyApp.screen.disable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.easyApp.R
import com.xuexiang.xutil.XUtil

@Composable
fun NetErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .background(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "网络异常",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.app_error)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    fontSize = 17.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                    text = "为了软件的稳定运行，请在网络安全的情况下运行本应用"
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .background(
                            shape = RoundedCornerShape(6.dp),
                            color = colorResource(R.color.app_error)
                        )
                        .clickable(
                            onClick = {
                                XUtil.exitApp()
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "退出应用",
                        modifier = Modifier
                            .padding(20.dp, 0.dp),
                        color = Color(0XFFFFFFFF)
                    )
                }
            }
        }
    }
}