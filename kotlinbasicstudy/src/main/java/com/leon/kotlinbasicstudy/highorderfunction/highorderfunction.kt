/**
 * FileName: highorderfunction
 * Author: shiwenliang
 * Date: 2021/6/7 11:26
 * Description:高阶函数学习
 */
package com.leon.kotlinbasicstudy.highorderfunction

import kotlin.concurrent.thread
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.math.log


/**
 * TODO 将函数作为参数传递给主函数，该主函数称为高阶函数
 */
//类似于typedef
typealias LOGIN = (String, String) -> Unit

fun main() {

    /**
     * TODO 自定义轮询器(仿系统的repeat)
     *
     * 说明：
     *     1) 我们在使用下标的时候，下标从0 开始，所有这里在执行轮询的时候，需要使用区间，但是必须until 去掉尾部，否则会多执行一次
     *     2) step 后面的步长，必须要明确说明，不能作为参数来进行赋值
     *     3) 真正的操作是在action这个函数参数的函数体内完成
     *
     */
    fun doWhile(count: Int, action: (Int) -> Unit) {
        for (index in 0 until count step 1) {
            action(index)
        }
    }

    doWhile(10) {
        println("自定义轮询器的下标：$it")
    }


    /**
     * TODO 4. 自定义线程的封装
     *
     * 说明：
     *     1) 我们分封装的线程，这样后续我们可以直接值关心耗时操作即可，
     *     2) openThread 可以作为一个模板来使用
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

    //对比系统的thread代码，大体类似

    openThread(true) {
        println("我是在子线程中执行的耗时操作：输出字符串")
    }


    /**
     * TODO 3. 高阶里面的特色：let、apply 、also、run、with、takeIf、takeUnless、repeat
     *
     *    1) let : 其实就是将调用类型的对象自己作为参数传递给block的函数体
     *    2) apply: 其实就是在执行完block函数后，将apply的调用者返回，类似于建造者模式的链式调用
     *    3) also:
     *    4) run:
     *    5) with:
     *    6) takeIf:
     *    7) repeat: 其实就是系统提供出来的一个轮询器，方便我们遍历数组
     */

    var Leon = "Leon"
    Leon.let {
        println("let 参数it的值是${it}")
    }

    var temp = Leon.apply {
        println("apply 回传的this的值是$this")
    }
    println("apply 执行后的结果$temp")

    repeat(Leon.length) {
        println("repeat轮询：it=${it},leon的当前位置的字符是：${Leon[it]}")
    }



    /**
     * TODO 2、高阶里面手写标准
     * 说明：
     *     1) kotlin中的泛型除了数据类型，还包含了方法，方法也算一个类型，即万能类型
     *     2) T 是泛型，R是返回类型的泛型
     *     3) T.MyRun相当于给泛型T增加了一个MyRun的扩展函数， mm: T.() -> R 代表给T增加一个匿名的扩展函数
     *     4) 类似于RxJava中的链式调用
     *
     */
    fun <T, R> T.MyRun(mm: () -> R): R {
        return mm()
    }

    //给T增加一个匿名函数 T.()
    fun <T, R> myWith(input: T, mm: T.() -> R): R {
        return input.mm()
    }

    var age = 0
    age.MyRun {
        "headworld"
    }

    var name = "Leon"
    myWith(name) {
        length//此时可以直接获取name的长度
    }

    /**
     * TODO 1、高阶
     * 说明：
     *     1)高阶函数的最后一个参数是一个函数时，
     *       在实现函数体时 可以在函数参数括号外使用{}来实现函数参数的函数体
     *       当
     *     2) 使用typealias 来给高阶函数起一个别名 typealias LOGIN= (String, String) -> Unit
     *        他就像一个数据类型一样的使用
     *     3) 当函数参数只有一个参数时，可以直接使用{}进行闭包，默认将一个参数命名为it，
     *                 有多个参数时，无法默认 则需要指明参数名，并用->指向函数体
     *     4) 如果函数参数已经有了实现体，那么可以通过:: 来直接调用；
     *        :: 内部原理：就是将show_run函数的对象赋值给show_14的参数，那么说明show_run 是可以赋值给一个变量的
     *
     */

    //1.4  :: 来调用高阶的函数参数

    fun show_14(number: Int, mm: (Int) -> String): String {
        return mm(number)
    }

    fun show_run(number: Int) = "通过:: 来调用函数参数的实现函数_$number"

    //
    val showRunObj = ::show_run
    println(show_14(50, showRunObj))
    println(show_14(100, ::show_run))



    //1.3   多个参数的函数参数
    fun show(mm: (String) -> Unit) {
        mm("一个参数")
    }

    show {
        println("一个参数的函数参数：$it")
    }

    fun show1(mm: (String, Int) -> Unit) {
        mm("多个个参数", 1)
    }
    //下面是调用
    show1 { param1, param2 ->
        println("多个参数的函数参数：param1=$param1,param2=$param2")
    }


    //1.1

    fun loginService(username: String, password: String, login: (String, String) -> Unit) {
        login(username, password)
    }

    //高级别名的使用
    fun loginService2(username: String, password: String, login: LOGIN) {
        login(username, password)
    }

    //真正使用
    fun loginEngine(username: String, password: String): Unit {
        loginService(username, password) { username, pwd ->
            //此处就是高阶函数的函数体
            println("username=${username},pwd=${password}")
        }
    }

    loginEngine("leon", "123456")
}



