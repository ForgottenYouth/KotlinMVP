/**
 * FileName: CollectionPresneterImpl
 * Author: shiwenliang
 * Date: 2021/6/1 11:18
 * Description:
 */
package com.leon.main.collectmvp.impl

import com.leon.main.collectmvp.inter.ICollectPresenter
import com.leon.main.collectmvp.inter.ICollectView
import com.leon.base.database.Student

class CollectionPresneterImpl(view: ICollectView?) :
    ICollectPresenter<ICollectView, CollectModuleImpl>(view) {

    override fun createModule(): CollectModuleImpl = CollectModuleImpl()

    /**
     * TODO 可变参数的传递，使用*来取值进行传递
     */
    override fun insert(vararg students: Student) {
        module.insert(object : ModuleListener<String, String> {
            override fun onModuleSuccessCallBack(result: String) {
                view?.showResult(result)
                queryAll()
            }

            override fun onModuleFailureCallBack(result: String) {
            }
        }, *students)
    }

    override fun update(vararg students: Student) {
        module.update(object : ModuleListener<String, String> {
            override fun onModuleSuccessCallBack(result: String) {
            }

            override fun onModuleFailureCallBack(result: String) {
            }
        }, *students)
    }

    override fun delete(vararg students: Student) {
        module.delete(object : ModuleListener<String, String> {
            override fun onModuleSuccessCallBack(result: String) {
            }

            override fun onModuleFailureCallBack(result: String) {
            }
        }, *students)
    }

    override fun deleteAll() {
        module.deleteAll(object : ModuleListener<String, String> {
            override fun onModuleSuccessCallBack(result: String) {
            }

            override fun onModuleFailureCallBack(result: String) {
            }
        })
    }

    override fun queryAll() {
        module.queryAll(object : ModuleListener<List<Student>, String> {
            override fun onModuleSuccessCallBack(result: List<Student>) {
                view?.showResultSuccess(result)
            }

            override fun onModuleFailureCallBack(result: String) {
            }
        })
    }

}