/**
 * FileName: CustomStandard
 * Author: shiwenliang
 * Date: 2021/6/9 16:43
 * Description:
 */
package com.leon.common.utils

import android.content.Context
import android.widget.Toast

/**
 * TODO 给Contenxt 扩展一个简单的toast
 */
fun Context.toast(info:String){
    Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
}


fun <T> T.SpeechToast(block: T.() -> Unit) = block()


/**
 * TODO 开启线程的模板
 */
fun openThread(start: Boolean, RunTask: () -> Unit): Thread? {
    val thread = object : Thread() {
        override fun run() {
            super.run()
            RunTask()
        }
    }
    return if (start) {
        thread.start()
        thread
    } else {
        null
    }
}

