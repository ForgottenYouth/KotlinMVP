/**
 * FileName: StudentDao
 * Author: shiwenliang
 * Date: 2021/6/1 9:59
 * Description:
 */
package com.leon.base.database

import androidx.room.*

/**
 * TODO 用来对数据库进行操作
 */

@Dao
interface StudentDao {

    
    /**
     * TODO 可变参数的insert
     */
    @Insert
    fun insert(vararg student: Student)

    /**
     * TODO 可变参数的insert
     */
    @Update
    fun update(vararg student: Student)
    
    /**
     * TODO 查询
     */
    @Query("SELECT * FROM Student ORDER BY ID DESC")
    fun query():List<Student>

    /**
     * TODO 删除全部
     */
    @Query("DELETE FROM student")
    fun deleteAll()

    @Delete
    fun  delete(vararg student: Student)
}