/**
 * FileName: RegistModule
 * Author: shiwenliang
 * Date: 2021/5/28 16:54
 * Description:
 */
package com.leon.login.registmvp.impl

import android.content.Context
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.base.net.APIClient
import com.leon.base.net.APIResponse
import com.leon.login.api.WanAndroidAPI
import com.leon.login.entity.RegistResponse
import com.leon.login.registmvp.inter.IRegistModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegistModuleImpl : IRegistModule {


    override fun registAction(
        context: Context,
        username: String,
        password: String,
        repassword: String,
        callBack: IMVPBasePresenter.ModuleListener<RegistResponse, String>
    ) {
        APIClient.instanceRetrofit(WanAndroidAPI::class.java)
            .registAction(username, password, repassword)  // 起点  往下流  ”包装Bean“
            .subscribeOn(Schedulers.io()) // 给上面请求服务器的操作，分配异步线程
            .observeOn(AndroidSchedulers.mainThread()) // 给下面更新UI操作，分配main线程
            .subscribe(object : APIResponse<RegistResponse>(context) {

                override fun failure(errorMsg: String?) {
                    if (errorMsg != null) {
                        callBack.onModuleFailureCallBack(errorMsg)
                    }
                }

                override fun success(data: RegistResponse) {
                    callBack.onModuleSuccessCallBack(data)
                }
            })
    }

    override fun cancelRequest() {
    }
}