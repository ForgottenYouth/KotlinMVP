/**
 * FileName: LoginIView
 * Author: shiwenliang
 * Date: 2021/5/27 10:37
 * Description:
 */
package com.leon.login.loginmvp.inter

import com.leon.base.mvp.IMVPBaseView
import com.leon.login.entity.LoginResponse

interface ILoginView : IMVPBaseView {

    fun loginSuccess(result: LoginResponse?)

    fun loginFailure(message:String?)
}