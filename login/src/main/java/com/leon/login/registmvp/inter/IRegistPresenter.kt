/**
 * FileName: IRegistPresenter
 * Author: shiwenliang
 * Date: 2021/5/28 11:52
 * Description:
 */
package com.leon.login.registmvp.inter

import android.content.Context
import com.leon.base.mvp.IMVPBaseModule
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.base.mvp.IMVPBaseView

abstract class IRegistPresenter<V : IMVPBaseView, M : IMVPBaseModule>(view: V?) :
    IMVPBasePresenter<V, M>(view) {

    abstract fun registAction(
        context: Context,
        userName: String,
        password: String,
        repassword: String
    )
}