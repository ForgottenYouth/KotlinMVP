/**
 * FileName: MVPBaseIPresenter
 * Author: shiwenliang
 * Date: 2021/5/27 10:24
 * Description: MVP模型中的抽象Presenter ,即P层的抽象接口标准
 */
package com.leon.base.mvp

abstract class IMVPBasePresenter<VIEW : IMVPBaseView?, M : IMVPBaseModule?> {
    var view: VIEW? = null

    constructor(view: VIEW?) {
        this.view = view
    }

    var module: M = createModule()
    abstract fun createModule(): M

    /**
     * TODO 解绑View
     */
    fun unAttachView() {
        view = null
        module?.cancelRequest()
    }

    interface ModuleListener<S, F> {
        fun onModuleSuccessCallBack(result: S)
        fun onModuleFailureCallBack(result: F)
    }
}