package top.chengdongqing.weui.core.ui.components.searchbar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.chengdongqing.weui.core.utils.clickableWithoutRipple

@Composable
fun WeSearchBar(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = "搜索",
    disabled: Boolean = false,
    focused: Boolean? = null,
    onFocusChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onBtnClick: (() -> Unit)? = null,
    onChange: (value: String) -> Unit,
) {
    var localFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val finalFocused = focused ?: localFocused
    fun setFocus(value: Boolean) {
        localFocused = value
        onFocusChange?.invoke(value)
    }

    // 输入框自动聚焦
    LaunchedEffect(finalFocused) {
        if (finalFocused) {
            focusRequester.requestFocus()
        }
    }
    // 返回时先取消聚焦
    BackHandler(finalFocused) {
        setFocus(false)
    }

    Row(
        modifier = modifier
            .height(45.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            if (finalFocused) {
                BasicTextField(
                    value,
                    onValueChange = onChange,
                    modifier = Modifier.focusRequester(focusRequester),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Search,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 4.dp)
                                    .size(20.dp),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (value.isEmpty()) {
                                    Text(
                                        fontSize = 16.sp,
                                        text = placeholder,
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                }
                                innerTextField()
                            }
                        }
                    })
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickableWithoutRipple {
                            if (!disabled) {
                                setFocus(true)
                            }
                            onClick?.invoke()
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(24.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = placeholder,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
        }
        if (finalFocused) {
            Spacer(modifier = Modifier.width(15.dp))
            Button(
                modifier = Modifier
                    .fillMaxHeight(),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    onBtnClick?.invoke()
                }
            ) {
                Text(
                    text = "搜索",
                    fontSize = 16.sp,
                    color = Color(0XFFFFFFFF)
                )
            }
        }
        /*if (finalFocused) {
            Text(
                text = "取消",
                color = FontLinkColor,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickableWithoutRipple {
                        setFocus(false)
                        onChange("")
                    }
                    .padding(start = 8.dp)
            )
        }*/
    }
}