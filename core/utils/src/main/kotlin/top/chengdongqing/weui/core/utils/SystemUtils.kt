package top.chengdongqing.weui.core.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.BatteryManager
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.xuexiang.xutil.XUtil
import kotlinx.coroutines.CancellationException
import top.chengdongqing.weui.core.utils.types.ToastType
import java.io.File

@Composable
fun rememberStatusBarHeight(): Dp {
    val density = LocalDensity.current
    val statusBars = WindowInsets.statusBars

    return remember {
        with(density) {
            statusBars.getTop(this).toDp()
        }
    }
}

@Composable
fun rememberBatteryInfo(): BatteryInfo {
    val batteryStatus = LocalContext.current.registerReceiver(
        null,
        IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    )

    val level by remember {
        derivedStateOf {
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?: 0
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, 0) ?: 0
            (level / scale.toFloat() * 100).toInt()
        }
    }
    val isCharging by remember {
        derivedStateOf {
            val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        }
    }

    return BatteryInfo(level, isCharging)
}

data class BatteryInfo(
    val level: Int,
    val isCharging: Boolean
)

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}


// 获取安卓唯一设备码
@SuppressLint("HardwareIds")
fun getAndroidId(): String {
    return Settings.Secure.getString(XUtil.getContext().contentResolver, Settings.Secure.ANDROID_ID)
}

// 获取程序的缓存大小
fun getAppCacheSize(): String {
    val dir = XUtil.getContext().cacheDir
    return formatSize(calculateSize(dir))
}

// 重启当前程序的进程
fun restartThisApp(activity: Class<*>) {
    val context = XUtil.getContext()
    val intent = Intent(context, activity)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    Runtime.getRuntime().exit(0)
}

// 计算程序的缓存大小
private fun formatSize(sizeBytes: Long): String {
    val units = listOf("B", "KB", "MB", "GB")
    var size = sizeBytes.toDouble()
    var unitIndex = 0

    while (size > 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }
    return "%.2f%s".format(size, units[unitIndex])
}

// 计算程序的缓存大小
private fun calculateSize(file: File): Long {
    var size = 0L
    if (file.isDirectory) {
        file.listFiles()?.forEach { child ->
            size += calculateSize(child)
        }
    } else size = file.length()
    return size
}

// 获取程序的版本名称
fun getAppVersionName(): String {
    val context = XUtil.getContext()
    return context.packageManager.getPackageInfo(context.packageName, 0).versionName
}

// 在浏览器中打开链接
fun openUrlInBrowse(url: String) {
    XUtil.getContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}


// 用于处理未知的异常
fun handleGenericError(e: Exception) {
    if (e is CancellationException) return
    showToast(
        type = ToastType.ERROR,
        message = e.message ?: "未知错误"
    )
}

// 打开任意类型的文件
fun openFileWithDevice(uri: Uri) {
    val context = XUtil.getContext()
    val mimeType = context.contentResolver.getType(uri) ?: "*/*"

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, mimeType)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        showToast(
            type = ToastType.ERROR,
            message = "没有应用可以打开此文件"
        )
    }
}