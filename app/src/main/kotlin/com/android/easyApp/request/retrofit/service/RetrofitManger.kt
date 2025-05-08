package com.android.easyApp.request.retrofit.service

import com.android.easyApp.config.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitManger {
    val okHttpClient = OkHttpClient()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AppConfig.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}