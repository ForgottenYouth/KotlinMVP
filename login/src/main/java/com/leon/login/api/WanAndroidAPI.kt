/**
 * FileName: WanAndroidAPI
 * Author: shiwenliang
 * Date: 2021/5/21 14:38
 * Description:
 */
package com.leon.login.api

import com.leon.base.net.ResponseWrapper
import com.leon.login.entity.LoginResponse
import com.leon.login.entity.RegistResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WanAndroidAPI {
    /**
     * 登录API
     * username=Derry-vip&password=123456
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun loginAction(
        @Field("username") username: String,
        @Field("password") password: String
    )
            : Observable<ResponseWrapper<LoginResponse>> // 返回值

    /**
     * 注册API
     */

    @POST("/user/register")
    @FormUrlEncoded
    fun registAction(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    )
            : Observable<ResponseWrapper<RegistResponse>> // 返回值
}