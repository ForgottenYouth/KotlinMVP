/**
 * FileName: StudentDataBase
 * Author: shiwenliang
 * Date: 2021/6/1 10:08
 * Description:
 */
package com.leon.base.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * TODO 数据库
 */
@Database(entities = [Student::class], version = 1)
abstract class StudentDataBase : RoomDatabase() {

    abstract fun getStudentDao(): StudentDao

    companion object {
        private var INSTANCE: StudentDataBase? = null

        fun getDataBase(context: Context): StudentDataBase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, StudentDataBase::class.java, "StudentDataBase")
                        .allowMainThreadQueries()//允许主线程允许
                        .build()
            }
            return INSTANCE
        }

        fun getDataBase(): StudentDataBase? = INSTANCE
    }


}