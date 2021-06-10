/**
 * FileName: KotlinCoroutines
 * Author: shiwenliang
 * Date: 2021/5/20 11:34
 * Description:kotlin 协程
 */
package com.example.kotlinbasic

import android.util.Log
import kotlinx.coroutines.*

/**
 * TODO 协程 专题
 * 1） 协程是一种并发设计模式，用来简化异步执行的代码
 * 2）特点：
 *      轻量：一个线程上运行多个协程，协程支持挂起，不会使用正在运行协程的线程阻塞，挂起比阻塞节省内容，且支持多个并行操作
 *      内存泄漏少：使用结构化并发机制在一个作用域内支线多项操作
 *      内置取消支持：取消操作会自动在允许中的整个协程层次结构内传播
 *      jetpack集成：许多jetpack库都提供了全部协程支持的扩展
 *  3）所有协程都必须在一个作用域内允许，一个CoroutineScope管理一个或多个相关的协程
 *  4原理： Kotlin 使用堆栈帧管理要运行哪个函数以及所有局部变量。挂起协程时，系统会复制并保存当前的堆栈帧以供稍后使用。
 *          恢复时，会将堆栈帧从其保存位置复制回来，然后函数再次开始运行。
 *          即使代码可能看起来像普通的顺序阻塞请求，协程也能确保网络请求避免阻塞主线程
 *
 *  5、线程与协程的区别：
 *     线程： 有操作系统调度，依赖进程，特别多的时候会内存溢出
 *     协程：是用户来控制的，协程更加轻量级    没有任何问题
 *
 *  6、协程job的cancel是不会马上取消的，而cancelAndJoin是回马上取消的
 */

fun main() {

    //runBlocking 是外协程
    runBlocking {


        //非阻塞式  类似于守护线程，会随着main线程的结束而结束，
        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.IO) {
                repeat(10) {
                    Thread.sleep(4000)
                    println("ddddddddd")
                }
            }
        }

        println("EEEEEEEEEEEEE")
        Thread.sleep(200)
        println("FFFFFFFFFF")



        //阻塞式
        //当前是在协程环境中
        //launch 是内协程
        launch(Dispatchers.IO) {
            repeat(10) {
                Thread.sleep(1000)
                println("aaaaaaaaaaaaa")
            }
        }

        withContext(Dispatchers.IO) {
            Thread.sleep(11000)
            println("BBBBBBBBBBBBBBB")
        }

        var deferred = async(Dispatchers.IO) {
            Thread.sleep(12000)
            println("CCCCCCCC")
        }
        deferred.await()
    }
}
