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
 * 1） 协程是一种并发设计模式，用来简化异步执行的代码。协程是一种非抢占式或者说协作式的计算机程序并发调度的实现，程序可以主动挂起或者回复的执行。
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
 *  7、协程启动的三要素：上下文、启动模式，协程体
 *     上下文：协程环境的上下文
 *     启动模式：1） DEFAULT 立即执行协程体  （饿汉式启动）
 *              2）ATOMIC 立即执行协程体，但在开始运行之前无法取消
 *              3）UNDISPATCHED 立即在当前线程执行写错题，直到第一个suspend调用，他不经过任何的调度器
 *              4）LAZY   只有在需要的情况下运行（懒汉式启动），就是需要通过launch返回的job进行start(主动触发协程的调度)或join(隐士触发协程的调用)来完成执行
 *     协程体： 就类似于java中Thead中的run的函数体
 */

suspend fun main() {

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
