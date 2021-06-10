/**
 * FileName: Student
 * Author: shiwenliang
 * Date: 2021/6/1 9:49
 * Description:
 */
package com.leon.base.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * TODO ROOM 是以注解的方式在编译期帮我们生成很多代码，方便我们对数据库的操作
 */
@Entity
class Student:Serializable {

    //设为主键
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    //给name 设置别名，别名的优先级更高
    @ColumnInfo(name = "username")
    var name: String=""

    var age: Int = 0


    //次构造    会自动调用主构造
    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}