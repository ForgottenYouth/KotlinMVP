/**
 * FileName: CollectModuleImpl
 * Author: shiwenliang
 * Date: 2021/6/1 11:19
 * Description:
 */
package com.leon.main.collectmvp.impl

import com.leon.base.mvp.IMVPBasePresenter
import com.leon.main.collectmvp.inter.ICollectModule
import com.leon.base.database.LocalDataBaseManager
import com.leon.base.database.Student

class CollectModuleImpl : ICollectModule {
    override fun insert(
        listener: IMVPBasePresenter.ModuleListener<String, String>,
        vararg students: Student
    ) {
        LocalDataBaseManager.getInstance()?.insert(*students)
        listener.onModuleSuccessCallBack("insert success")
    }

    override fun update(
        listener: IMVPBasePresenter.ModuleListener<String, String>,
        vararg students: Student
    ) {
        LocalDataBaseManager.getInstance()?.update(*students)
        listener.onModuleSuccessCallBack("update success")
    }

    override fun delete(
        listener: IMVPBasePresenter.ModuleListener<String, String>,
        vararg students: Student
    ) {
        LocalDataBaseManager.getInstance()?.delete(*students)
        listener.onModuleSuccessCallBack("delete success")
    }


    override fun deleteAll(listener: IMVPBasePresenter.ModuleListener<String, String>) {
        LocalDataBaseManager.getInstance()?.deleteAll()
        listener.onModuleSuccessCallBack("deleteAll success")
    }

    override fun queryAll(listener: IMVPBasePresenter.ModuleListener<List<Student>, String>) {
        var result: List<Student> = LocalDataBaseManager.getInstance()?.queryAll() as List<Student>
        listener.onModuleSuccessCallBack(result)
    }

    override fun cancelRequest() {
    }
}