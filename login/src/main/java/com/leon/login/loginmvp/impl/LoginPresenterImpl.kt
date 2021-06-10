/**
 * FileName: LoginPresenter
 * Author: shiwenliang
 * Date: 2021/5/27 10:35
 * Description:
 */
package com.leon.login.loginmvp.impl

import android.content.Context
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.login.entity.LoginResponse
import com.leon.login.loginmvp.inter.ILoginPresenter
import com.leon.login.loginmvp.inter.ILoginView

class LoginPresenterImpl constructor(loginView: ILoginView?) :
    ILoginPresenter<ILoginView, LoginModuleImpl>(loginView),
    IMVPBasePresenter.ModuleListener<LoginResponse, String> {


    override fun loginAction(context: Context, userName: String, password: String) {
        module.loginAction(context, userName, password, this)
    }

    override fun onModuleSuccessCallBack(result: LoginResponse) {
        view?.loginSuccess(result)
    }

    override fun onModuleFailureCallBack(result: String) {
        view?.loginFailure(result)
    }

    override fun createModule(): LoginModuleImpl = LoginModuleImpl()
}