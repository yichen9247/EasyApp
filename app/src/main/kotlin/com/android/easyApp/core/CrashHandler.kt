package com.android.easyApp.core

import android.util.Log
import com.android.easyApp.MainActivity
import com.xuexiang.xutil.XUtil
import top.chengdongqing.weui.core.utils.restartThisApp
import top.chengdongqing.weui.core.utils.showToast

class CrashHandler : Thread.UncaughtExceptionHandler {
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    init {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex)) mDefaultHandler?.uncaughtException(thread, ex)
    }

    private fun handleException(ex: Throwable): Boolean {
        ex.printStackTrace()
        Log.d("HandSock-Log", ex.message.toString())
        XUtil.getContext().showToast("应用崩溃，即将重启")
        Thread.sleep(2000)
        restartThisApp(MainActivity::class.java)
        return true
    }
}