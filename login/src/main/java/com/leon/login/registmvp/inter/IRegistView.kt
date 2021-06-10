/**
 * FileName: IRegistView
 * Author: shiwenliang
 * Date: 2021/5/28 11:49
 * Description:
 */
package com.leon.login.registmvp.inter

import com.leon.base.mvp.IMVPBaseView
import com.leon.login.entity.RegistResponse

interface IRegistView : IMVPBaseView {

    fun registSuccess(result: RegistResponse?)

    fun registFailure(error: String)
}