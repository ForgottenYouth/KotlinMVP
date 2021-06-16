/**
 * FileName: KotlinCoroutines
 * Author: shiwenliang
 * Date: 2021/5/20 11:34
 * Description:kotlin 协程
 */
package com.example.kotlinbasic

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

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
 *       "回调+黑魔法"
 *
 *  5、线程与协程的区别：
 *     线程： 有操作系统调度，依赖进程，特别多的时候会内存溢出
 *     协程：是用户来控制的，协程更加轻量级    没有任何问题
 *
 *  6、协程job的cancel是不会马上取消的，而cancelAndJoin是回马上取消的
 *  7、协程启动的三要素：上下文、启动模式，协程体
 *     上下文：协程环境的上下文
 *     启动模式：1）DEFAULT 立即执行协程体  （饿汉式启动）
 *              2）ATOMIC 立即执行协程体，但在开始运行之前无法取消
 *              3）UNDISPATCHED 立即在当前线程执行写错题，直到第一个suspend调用，他不经过任何的调度器
 *              4）LAZY   只有在需要的情况下运行（懒汉式启动），就是需要通过launch返回的job进行start(主动触发协程的调度)或join(隐士触发协程的调用)来完成执行
 *     协程体： 就类似于java中Thead中的run的函数体
 *
 *  8、协程调度
 *     在我们启动一个协程的时候，需要传入的第一个参数是上下文，默认是一个空的上下文（EmptyCoroutineContext），
 *     这个空上下文是继承于CoroutineContext ，
 *     看CoroutineContext的源码可以明白，其实他就只是一个以key为索引的递归list
 *     1）当需要给协程添加一个名字的时候，使用CoroutineName 来完成，查看源码可以发现其实CoroutineName 也是CoroutiuneContext的子类
 *     2）当我们需要给协程添加多个上下文时，需要使用+来完成多个上下文
 *     3）协程的拦截器其实也是一种比较特殊的上下文，他有个特殊的功能：就是可以控制协程的执行，为了保证他功能的正确性，我们会把它放在上下文集合的最后面，
 *        这一点有点类似于java中的可变参数需要放在尾部一样
 *        协程的拦截器和OKHttp做网络请求时候的拦截器一样的道理，
 *        协程启动的时候都会调用Continuation.resumeWith的操作，
 *        所以我们自己可以实现一个自己的拦截器：MyContinuationInterceptor, 如下面的代码示例：
 *     4）经过上面的自定义拦截器，我们发现只要在resumeWith中我们自己来进行线程切换，这就完成了一个简单的调度器(Dispatchers)。
 *       调度器也是上下文的一个实现,同时也是拦截器的一个实现（看源码CoroutineDispatcher），所以他们三者的关系：
 *       CoroutinuationContext (    ContinuationInterceptor  (Dispatchers))
 *       Dispatchers中的dispatch方法在其实现的ContinuationInterceptor的interceptContinuation方法中调用，这样就试下你了协程的调度
 *       所有我们想要自己实现调度器，那么我们就直接继承CoroutineDispatcher就可以了
 *       调度器的目的：切换线程
 *       多个线程中的协程切换会引起一定的开销，还存在线程安全的问题，所以要尽量简写协程的切换。
 *
 *       *** 如果遇到一些kotlin 系统使用internal修饰的无法直接使用，我们可以将其代码复制到我们的代码中，使用扩展函数来达到我们使用的目的***
 *
 *
 *
 *
 */
class MyContinuationInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>) =
        MyContinuation(continuation)
}

class MyContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {
    override val context = continuation.context
    override fun resumeWith(result: Result<T>) {
        println("<MyContinuation>---resumeWith： $result")
        continuation.resumeWith(result)
    }
}

suspend fun main() {
    /**
     * TODO 解释一下下面的代码：
     * 启动一个名字为CustomInterceptor的线程，然后在其内部新启动一个async的协程，
     * 当外部协程执行到await()的时候，挂起等待内部线程的delay时间到了后，执行完async的函数体后，将返回值返给await()的接受者，
     * 然后接着执行await()后面的代码
     */
    Dispatchers.Main
    GlobalScope.launch(CoroutineName("CustomInterceptor") +MyContinuationInterceptor()) {
        println("111111111111")
        val async = async {
            println("3333333333333")
            delay(2000)
            println("4444444444444")
            "66666666666"
        }
        println("555555555555")
        println(async.await())
        println("7777777777")
    }
    println("22222222222222222")

    /**
     * TODO  执行的结果如下:
    <MyContinuation>---resumeWith： Success(kotlin.Unit)
    111111111111
    <MyContinuation>---resumeWith： Success(kotlin.Unit)
    3333333333333
    555555555555
    22222222222222222
    <MyContinuation>---resumeWith： Success(kotlin.Unit)
    4444444444444
    <MyContinuation>---resumeWith： Success(66666666666)
    66666666666
    7777777777
     */
    Thread.sleep(4000)

    println("-----------------------------------")


    //runBlocking 是外协程
//    runBlocking {
//
//
//        //非阻塞式  类似于守护线程，会随着main线程的结束而结束，
//        GlobalScope.launch(Dispatchers.IO) {
//            launch(Dispatchers.IO) {
//                repeat(10) {
//                    Thread.sleep(4000)
//                    println("ddddddddd")
//                }
//            }
//        }
//
//        println("EEEEEEEEEEEEE")
//        Thread.sleep(200)
//        println("FFFFFFFFFF")
//
//
//
//        //阻塞式
//        //当前是在协程环境中
//        //launch 是内协程
//        launch(Dispatchers.IO) {
//            repeat(10) {
//                Thread.sleep(1000)
//                println("aaaaaaaaaaaaa")
//            }
//        }
//
//        withContext(Dispatchers.IO) {
//            Thread.sleep(11000)
//            println("BBBBBBBBBBBBBBB")
//        }
//
//        var deferred = async(Dispatchers.IO) {
//            Thread.sleep(12000)
//            println("CCCCCCCC")
//        }
//        deferred.await()
//    }
}
