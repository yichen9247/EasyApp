package com.android.easyApp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import coil.ImageLoader
import coil.disk.DiskCache
import com.android.easyApp.config.AppConfig
import com.android.easyApp.state.GlobalState
import com.tencent.mmkv.MMKV
import com.tencent.tauth.Tencent

class EasyApp : Application() {
    companion object {
        lateinit var tencent: Tencent
        lateinit var instance: EasyApp
        lateinit var imageLoader: ImageLoader
        private set
    }

    override fun onCreate() {
        initCoil()
        instance = this
        super.onCreate()
        MMKV.initialize(this)
        Tencent.setIsPermissionGranted(true)
        tencent = Tencent.createInstance(AppConfig.tencentAppId, this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            private var resumedActivities = 0
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
                if (resumedActivities++ == 0) {
                    GlobalState.isAppForGround.value = true
                }
            }

            override fun onActivityPaused(activity: Activity) {
                if (--resumedActivities == 0) {
                    GlobalState.isAppForGround.value = false
                }
            }
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivityDestroyed(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        })
    }

    private fun initCoil() {
        imageLoader = ImageLoader.Builder(this)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("coil_disk_cache"))
                    .maxSizeBytes(500 * 1024 * 1024)
                    .build()
            }
            .build()
    }
}