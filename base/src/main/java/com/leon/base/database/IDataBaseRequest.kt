/**
 * FileName: IDataBaseRequest
 * Author: shiwenliang
 * Date: 2021/6/1 15:06
 * Description:
 */
package com.leon.base.database

interface IDataBaseRequest {
    fun insert(vararg student: Student)

    fun delete(vararg student: Student)

    fun update(vararg student: Student)

    fun deleteAll()

    fun queryAll():List<Student>?
}