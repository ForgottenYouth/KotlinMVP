/**
 * FileName: LoginModuleImpl
 * Author: shiwenliang
 * Date: 2021/5/27 10:36
 * Description:
 */
package com.leon.login.loginmvp.impl

import android.content.Context
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.base.net.APIClient
import com.leon.base.net.APIResponse
import com.leon.login.api.WanAndroidAPI
import com.leon.login.entity.LoginResponse
import com.leon.login.loginmvp.inter.ILoginModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginModuleImpl : ILoginModule {

    override fun loginAction(
        context: Context,
        userName: String,
        password: String,
        loginCallBack: IMVPBasePresenter.ModuleListener<LoginResponse, String>
    ) {
        APIClient.instanceRetrofit(WanAndroidAPI::class.java)
            .loginAction(userName, password)  // 起点  往下流  ”包装Bean“
            .subscribeOn(Schedulers.io()) // 给上面请求服务器的操作，分配异步线程
            .observeOn(AndroidSchedulers.mainThread()) // 给下面更新UI操作，分配main线程
            .subscribe(object : APIResponse<LoginResponse>(context) {

                override fun failure(errorMsg: String?) {
                    if (errorMsg != null) {
                        loginCallBack.onModuleFailureCallBack(errorMsg)
                    }
                }

                override fun success(data: LoginResponse) {
                    loginCallBack.onModuleSuccessCallBack(data)
                }
            })
    }

    override fun cancelRequest() {
    }
}