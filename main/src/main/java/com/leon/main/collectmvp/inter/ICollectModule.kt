/**
 * FileName: ICollectModule
 * Author: shiwenliang
 * Date: 2021/6/1 11:10
 * Description:
 */
package com.leon.main.collectmvp.inter

import com.leon.base.mvp.IMVPBaseModule
import com.leon.base.mvp.IMVPBasePresenter
import com.leon.base.database.Student

interface ICollectModule : IMVPBaseModule {
    fun insert(listener: IMVPBasePresenter.ModuleListener<String, String>, vararg students: Student)

    fun update(listener: IMVPBasePresenter.ModuleListener<String, String>, vararg students: Student)

    fun delete(listener: IMVPBasePresenter.ModuleListener<String, String>, vararg students: Student)

    fun deleteAll(listener: IMVPBasePresenter.ModuleListener<String, String>)

    fun queryAll(listener: IMVPBasePresenter.ModuleListener<List<Student>, String>)
}