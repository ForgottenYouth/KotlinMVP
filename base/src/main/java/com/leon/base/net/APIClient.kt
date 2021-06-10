/**
 * FileName: APIClient
 * Author: shiwenliang
 * Date: 2021/5/21 14:51
 * Description:访问服务器的API接口(WanAndroidAPI) 的 客户端管理
 */
package com.leon.base.net

import com.leon.base.config.Flag
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    private val okHttpClient = OkHttpClient().newBuilder().apply {
        readTimeout(10000, TimeUnit.SECONDS)
        connectTimeout(10000, TimeUnit.SECONDS)
        writeTimeout(10000, TimeUnit.SECONDS)
    }.build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl(Flag.BASE_URL1)
        client(okHttpClient)
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    fun <T> instanceRetrofit(apiInterface: Class<T>): T {
        return retrofit.create(apiInterface)
    }
}