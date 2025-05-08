package top.chengdongqing.weui.core.utils

import android.widget.Toast
import com.xuexiang.xutil.XUtil
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.chengdongqing.weui.core.utils.types.ToastType

fun showToast(type: ToastType, message: String) {
    CoroutineScope(Dispatchers.Main).launch {
        val context = XUtil.getContext()
        when (type) {
            ToastType.INFO -> Toasty.info(
                context, message,
                Toast.LENGTH_SHORT
            ).show()
            ToastType.ERROR -> Toasty.error(
                context, message,
                Toast.LENGTH_SHORT
            ).show()
            ToastType.WARNING -> Toasty.warning(
                context, message,
                Toast.LENGTH_SHORT
            ).show()
            ToastType.SUCCESS -> Toasty.success(
                context, message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}