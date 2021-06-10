/**
 * FileName: KotlinBasic
 * Author: shiwenliang
 * Date: 2021/5/18 16:24
 * Description: 基础学习
 */
package com.example.kotlinbasic

import android.os.Parcel
import android.os.Parcelable

fun main() {
    add(1, 2, 3, 4, 5, 6, 7)
    printDemo()
    inAreaDemo()
    compareDemo()
    judgeDemo()

    NetManager.getInstance().show("NetManager_instane")

    Worker.getInstance().show("worker_instance")
}


/**
 * TODO 抽象类与接口、data,object
 * 1、所有的接口默认都是open
 * 2、在类实现接口的时候，接口后面不需要加()括号，多个接口实现时需要用逗号隔开
 * 3、在类与接口的实现类中，将类的继承放在前面
 *
 * 4、data 修饰的class 其实类似于java中的实体bean，会自动生成很多java的方法
 * 5、object 修饰的，相当于单例，只实例化一次
 */

class Worker {
    companion object {
        private var mInstance: Worker? = null

        @JvmStatic
        fun getInstance(): Worker {
            if (mInstance == null) {
                mInstance = Worker()
            }
            return mInstance!!
        }
    }

    fun show(mes: String) {
        println(mes)
    }
}

class NetManager {

    //单例
    object Holder {
        val instance = NetManager()
    }

    //伴生对象，静态区
    companion object {
        fun getInstance(): NetManager = Holder.instance
    }

    fun show(mes: String) {
        println(mes)
    }

}

data class HttpResultBean(val code: String, val msg: String, val result: Any)

/**
public final class HttpResultBean {
@NotNull
private final String code;
@NotNull
private final String msg;
@NotNull
private final Object result;

@NotNull
public final String getCode() {
return this.code;
}

@NotNull
public final String getMsg() {
return this.msg;
}

@NotNull
public final Object getResult() {
return this.result;
}

public HttpResultBean(@NotNull String code, @NotNull String msg, @NotNull Object result) {
Intrinsics.checkParameterIsNotNull(code, "code");
Intrinsics.checkParameterIsNotNull(msg, "msg");
Intrinsics.checkParameterIsNotNull(result, "result");
super();
this.code = code;
this.msg = msg;
this.result = result;
}

@NotNull
public final String component1() {
return this.code;
}

@NotNull
public final String component2() {
return this.msg;
}

@NotNull
public final Object component3() {
return this.result;
}

@NotNull
public final HttpResultBean copy(@NotNull String code, @NotNull String msg, @NotNull Object result) {
Intrinsics.checkParameterIsNotNull(code, "code");
Intrinsics.checkParameterIsNotNull(msg, "msg");
Intrinsics.checkParameterIsNotNull(result, "result");
return new HttpResultBean(code, msg, result);
}

// $FF: synthetic method
public static HttpResultBean copy$default(HttpResultBean var0, String var1, String var2, Object var3, int var4, Object var5) {
if ((var4 & 1) != 0) {
var1 = var0.code;
}

if ((var4 & 2) != 0) {
var2 = var0.msg;
}

if ((var4 & 4) != 0) {
var3 = var0.result;
}

return var0.copy(var1, var2, var3);
}

@NotNull
public String toString() {
return "HttpResultBean(code=" + this.code + ", msg=" + this.msg + ", result=" + this.result + ")";
}

public int hashCode() {
String var10000 = this.code;
int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
String var10001 = this.msg;
var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
Object var2 = this.result;
return var1 + (var2 != null ? var2.hashCode() : 0);
}

public boolean equals(@Nullable Object var1) {
if (this != var1) {
if (var1 instanceof HttpResultBean) {
HttpResultBean var2 = (HttpResultBean)var1;
if (Intrinsics.areEqual(this.code, var2.code) && Intrinsics.areEqual(this.msg, var2.msg) && Intrinsics.areEqual(this.result, var2.result)) {
return true;
}
}

return false;
} else {
return true;
}
}
}
 */

//接口
interface ICallBack {
    fun notify(message: String);
}

//抽象类
abstract class Person : ICallBack {
    abstract fun updateName(name: String)
    abstract fun updateAge(age: Int)
}

//实现类
class Student : Person() {
    override fun updateName(name: String) {
        TODO("Not yet implemented")
    }

    override fun updateAge(age: Int) {
        TODO("Not yet implemented")
    }

    override fun notify(message: String) {
        TODO("Not yet implemented")
    }

}

/**
 * TODO 类
 * 1、类与java的类相同，只是类的属性默认是public ，可以通过对象直接访问，需要显式声明为private
 * 2、在类的对象初始化时必须对属性进行初始化，这样你可以在得到示例时立即访问他的属性
 * 3、也可以使用lateinit来推迟属性的初始化
 * 4、伴生对象：用于定义在概念上与某个类型相关，但不与其特点对象关联的变量或函数。
 *            说白了： 类似于java中的static修饰的变量和方法
 * 5、主次构造：次构造必须调用主构造
 * 6、kotlin中的类默认是public final 修饰的，不能被继承，需要加上 open 修饰后才可以继承
 * 7、kotlin 所有的对象都是没有默认值的，可以使用lateinit 来进行懒加载初始化，lateinit只能修饰var的变量
 * 8、内部类必须加上inner ,否则只是嵌套类
 */


open class Car(id: Int)//主构造
{
    var name: String = ""
    var age: Int = 0

    //在初始化块中进行初始化属性
    init {
        name = "leon"
        age = 18
    }

    //    伴生对象
    companion object {
        val TAG: String = "Person"

    }

    //次构造， 也可以重载
    constructor(id: Int, name: String) : this(id) {
        this.name = name
    }

    //内部类，可以访问外部类成员
    inner class Wheel {
        fun show() {
            println(name)
        }
    }

    //外部类，不以访问外部类成员
    class Light {
        fun show() {
//            println(name)
        }
    }
}

class pCar(id: Int) : Car(id) {

}

/**
 * TODO 循环和标签
 * 1、标签：标签名@   做一个标记，相当于goto
 * 2、for in    foreach   来完成循环
 */
fun cycleAndTagDemo() {
    tag@ for (j in 1..20) {
        for (i in 1..100) {
            if (i == 50) {
                break@tag//结束外层循环
            }
        }
    }
}


/**
 * TODO 条件判断
 * 1、if else  与java中的基本相同，只是每一个条件的最后一句执行结果是作为if else 的满足条件返回值
 * 2、when 相当于java中的switch , 但是when 是可以有返回值的，且when的条件可以是任意的类型或表达式,且可以是区间;
 *   如果要接受when的返回值，则必须在when的条件最后加else 来完成返回值
 *   如果返回值数据类型不相同，可以使用Any?来修饰接受结果的变量，相当于java中的Object
 */

fun judgeDemo() {
    println("条件判断")
    var number = 100
    var ifResult = if (number == 100)
        "true"
    else
        "false"
    println(ifResult)

    var result: Any? = when (number) {
        0 -> "0"
        100 -> "100"
        in 100..200 -> "200"
        else -> 'M'
    }
    println(number)
}

/**
 * TODO 比较
 * 1、== 来比较两个对象的值
 * 2、=== 来比较两个对象的地址
 */

fun compareDemo() {
    var number: Int = 1000
    var number1: Int = 1000
    println(number == number1)
    println(number === number1)
}

/**
 * TODO 区间
 * 1、in 在for循环中执行区间操作  ..代表区间范围（从小到大）；downTo 从小到大
 * 2、使用step 来进行跳跃式访问
 * 3、until 排除最后元素的访问
 */
fun inAreaDemo() {
    println("区间输出：")
    for (i in 1..10) {
        println(i)
    }
    println("-----------")
    for (i in 10 downTo 1) {
        println(i)
    }

    println("-----------")
    for (i in 1 until 10) {
        println(i)
    }
}

/**
 * TODO NULL空检查机制
 * 1、在声明变量时使用?  代表通知所有在使用此变量时需要注意，这个变量可能为空,
 *    补救措施：
 *    1） 在使用时加上? 代表为空时则不执行
 *    2） 使用两个!!，来表示此变量不为空，如果为空时会报出空指针异常
 *    3） 类似于java进行非空的判断
 *    4）使用 ?: 运算符来处理为null时的情况
 */
fun emptyCheckDemo() {
    println("null 空检查机制")
    var info: String? = null

    var len = info?.length ?: 0
    println(info?.length)
}

/**
 * TODO 字符串模板
 * 1、$ 变量名
 * 2、自动换行用 """ """ 三组双引号打印   使用trimIndent()去掉空格  使用triMargin()来去掉字符串中的某个字符
 */
fun printDemo() {
    println("字符串模板输出：")
    var name = "张三"
    var age = 28
    println("name=$name,age = $age")

    var info = """
        hello
        world
    """.trimIndent()
    println(info)
}

/**
 * TODO  函数
 * 格式：fun   函数名（参数列表）：返回值类型 { 函数体 }
 * 1、使用vararg 可变长参数来完成类似java中的可变参数
 * 2、匿名函数：没有函数名，直接通过输入和输出更直接的进行标识
 * 3、高阶函数：一个函数可以将另外一个函数作为参数，将其他函数用作参数的函数就是高阶函数（类似于java中的额回调接口）
 */
fun add(number: Double, number2: Double): Double {
    return number + number2
}

//类型推导返回类型
fun add(number1: Int, number2: Int) = number1 + number2

//可变长参数
fun add(vararg value: Int) {
    for (i in value) {
        println(i)
    }
}

//lambda 表达式  add代表对匿名函数的引用，（Int ,Int）是输入参数  ->Int 是输出的返回值 {}是真正的函数体的实现
var add: (Int, Int) -> Int = { number1, number2 -> number1 + number2 }

//高阶函数
fun highLevelFun(number: Int, add: (number1: Int) -> Int): Int {
    return add(number)
}


/**
 * TODO 变量声明
 * 格式：val/var + 变量名称 ：类型 = 初始值
 * val 定义的是常量   只读    final int value = 12;
 * var 可读可写     相当于java中的String info = "String";
 *
 */
var info = "String"
val value = 12

/**
 * TODO 概述
 * 由于google与sun公司的种种原因，现在google已经将Kotlin列为第一语言，kotlin是一种可以与android兼容的语言，他不仅语法简介，更具表达性，还具备类型安全和空值安全的特性。
 * 可以与java无缝互通。
 */