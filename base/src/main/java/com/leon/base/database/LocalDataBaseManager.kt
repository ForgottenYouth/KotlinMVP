/**
 * FileName: LocalDataBaseManager
 * Author: shiwenliang
 * Date: 2021/6/1 15:01
 * Description:
 */
package com.leon.base.database

class LocalDataBaseManager : IDataBaseRequest {
    var studentDao: StudentDao? = null

    /**
     * TODO 构造代码块
     */
    constructor(){
        studentDao = StudentDataBase.getDataBase()?.getStudentDao()
    }

    companion object {
        var INSTANCE: LocalDataBaseManager? = null
        fun getInstance(): LocalDataBaseManager? {
            if (INSTANCE == null) {
                synchronized(LocalDataBaseManager::class) {}
                if (INSTANCE == null) {
                    INSTANCE = LocalDataBaseManager()
                }
            }
            return INSTANCE
        }
    }

    override fun insert(vararg student: Student) {
        studentDao?.insert(*student)
    }

    override fun delete(vararg student: Student) {
        studentDao?.delete(*student)
    }

    override fun update(vararg student: Student) {
        studentDao?.update(*student)
    }

    override fun deleteAll() {
        studentDao?.deleteAll()
    }

    override fun queryAll(): List<Student>? {
        return studentDao?.query()
    }
}