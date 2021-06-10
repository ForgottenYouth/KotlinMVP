/**
 * FileName: LoginIPresenter
 * Author: shiwenliang
 * Date: 2021/5/27 10:37
 * Description:
 */
package com.leon.login.loginmvp.inter

import android.content.Context
import com.leon.base.mvp.IMVPBaseModule
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.base.mvp.IMVPBaseView
import com.leon.login.registmvp.inter.IRegistModule

abstract class ILoginPresenter<V : IMVPBaseView, M : IMVPBaseModule>(view: V?) :
    IMVPBasePresenter<V, M>(view) {
    abstract fun loginAction(context: Context, userName: String, password: String)
}