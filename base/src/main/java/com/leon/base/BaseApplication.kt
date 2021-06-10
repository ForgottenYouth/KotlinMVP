/**
 * FileName: BaseApplication
 * Author: shiwenliang
 * Date: 2021/5/25 16:30
 * Description:
 */
package com.leon.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.leon.base.database.StudentDataBase

class BaseApplication : Application() {

    private object Holder {
        val instance: BaseApplication = BaseApplication()
    }


    companion object {
        fun getInstance(): BaseApplication {
            return Holder.instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        ARouter.init(this)
        /**
         * TODO 路由sdk的初始化
         */
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }


//        StudentDataBase.getDataBase(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}