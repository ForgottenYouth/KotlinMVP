/**
 * FileName: RegistPresenterImpl
 * Author: shiwenliang
 * Date: 2021/5/28 11:53
 * Description:
 */
package com.leon.login.registmvp.impl

import android.content.Context
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.login.entity.RegistResponse
import com.leon.login.registmvp.inter.IRegistPresenter
import com.leon.login.registmvp.inter.IRegistView

class RegistPresenterImpl(view: IRegistView?) :
    IRegistPresenter<IRegistView, RegistModuleImpl>(view),
    IMVPBasePresenter.ModuleListener<RegistResponse, String> {

    override fun registAction(
        context: Context,
        userName: String,
        password: String,
        repassword: String
    ) {
        module.registAction(context, userName, password, repassword, this)
    }


    override fun onModuleSuccessCallBack(result: RegistResponse) {
        view?.registSuccess(result)
    }

    override fun onModuleFailureCallBack(result: String) {
        view?.registFailure(result)
    }

    override fun createModule(): RegistModuleImpl {
        return RegistModuleImpl()
    }
}