/**
 * FileName: ICollectPresenter
 * Author: shiwenliang
 * Date: 2021/6/1 11:04
 * Description:
 */
package com.leon.main.collectmvp.inter

import com.leon.base.mvp.IMVPBaseModule
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.base.mvp.IMVPBaseView
import com.leon.base.database.Student

abstract class ICollectPresenter<V : IMVPBaseView?, M : IMVPBaseModule?>(view: V?) :
    IMVPBasePresenter<V, M>(view) {

    abstract fun insert(vararg students: Student)
    abstract fun update(vararg students: Student)
    abstract fun delete(vararg students: Student)
    abstract fun deleteAll()
    abstract fun queryAll()
}