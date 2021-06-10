/**
 * FileName: IHomePresenter
 * Author: shiwenliang
 * Date: 2021/6/9 9:52
 * Description:
 */
package com.leon.main.homemvp.inter

import com.leon.base.mvp.IMVPBaseModule
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.base.mvp.IMVPBaseView

abstract class IHomePresenter <V : IMVPBaseView?, M : IMVPBaseModule?>(view: V?) :
    IMVPBasePresenter<V, M>(view) {
}