package com.android.easyApp.tencent.helper

import android.app.Activity
import android.content.Intent
import com.android.easyApp.EasyApp
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject
import top.chengdongqing.weui.core.utils.showToast
import top.chengdongqing.weui.core.utils.types.ToastType

class TencentLoginHelper private constructor(
    private val onError: (String) -> Unit,
    private val onCancel: () -> Unit,
    private val onSuccess: (String, String) -> Unit
) {
    companion object {
        fun create(
            onCancel: () -> Unit,
            onError: (String) -> Unit,
            onSuccess: (String, String) -> Unit,
        ): TencentLoginHelper {
            return TencentLoginHelper(onError, onCancel, onSuccess)
        }
    }

    private var currentListener: BaseUiListener? = null

    fun login(activity: Activity) {
        EasyApp.tencent.let {
            if (!it.isSessionValid) {
                currentListener = object : BaseUiListener() {
                    override fun onComplete(response: Any?) {
                        parseResponse(response)?.let { (openId, token) ->
                            onSuccess(openId, token)
                        } ?: onError("Invalid response format")
                    }

                    override fun onError(e: UiError?) {
                        onError(e?.errorMessage ?: "Unknown error")
                    }

                    override fun onCancel() {
                        null
                    }
                }
                it.login(activity, "all", currentListener)
            }
        }
    }

    private fun parseResponse(response: Any?): Pair<String, String>? {
        return try {
            val json = response as JSONObject
            val openId = json.getString("openid")
            val token = json.getString("access_token")
            if (openId.isNotEmpty() && token.isNotEmpty()) Pair(openId, token) else null
        } catch (_: Exception) {
            showToast(
                message = "回调解析异常",
                type = ToastType.ERROR
            )
            null
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, currentListener)
        }
        currentListener = null
    }

    abstract inner class BaseUiListener : IUiListener {
        override fun onWarning(code: Int) {}
    }
}