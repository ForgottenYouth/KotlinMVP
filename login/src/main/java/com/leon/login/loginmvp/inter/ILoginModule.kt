/**
 * FileName: LoginIModule
 * Author: shiwenliang
 * Date: 2021/5/27 10:37
 * Description:
 */
package com.leon.login.loginmvp.inter

import android.content.Context
import com.leon.base.mvp.IMVPBaseModule
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.login.entity.LoginResponse

interface ILoginModule : IMVPBaseModule {
    fun loginAction(
        context: Context,
        userName: String,
        password: String,
        loginCallBack: IMVPBasePresenter.ModuleListener<LoginResponse, String>
    )
}