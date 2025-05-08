package com.android.easyApp.tencent.state

sealed class TencentState {
    object Idle : TencentState()
    object Loading : TencentState()
    object Cancelled : TencentState()
    data class Error(val message: String) : TencentState()
    data class Success(val openId: String, val token: String) : TencentState()
}