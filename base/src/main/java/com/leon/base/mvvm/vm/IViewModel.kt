/**
 * FileName: IViewModel
 * Author: shiwenliang
 * Date: 2021/5/25 15:39
 * Description:
 */
package com.leon.base.mvvm.vm

import com.leon.base.mvvm.v.IView

interface IViewModel {

    fun bindView(iView:IView)
}