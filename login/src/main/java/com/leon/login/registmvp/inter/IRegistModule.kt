/**
 * FileName: IRegistModule
 * Author: shiwenliang
 * Date: 2021/5/28 16:53
 * Description:
 */
package com.leon.login.registmvp.inter

import android.content.Context
import com.leon.base.mvp.IMVPBaseModule
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.login.entity.RegistResponse

interface IRegistModule : IMVPBaseModule {

    fun registAction(
        context: Context,
        username: String,
        password: String,
        repassword: String,
        callBack: IMVPBasePresenter.ModuleListener<RegistResponse, String>
    )
}