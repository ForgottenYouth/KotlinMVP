/**
 * FileName: generic
 * Author: shiwenliang
 * Date: 2021/6/9 17:36
 * Description:
 */
package com.leon.kotlinbasicstudy.generic


//在声明泛型时候的泛型读写模式的限定
class Student<in T> {
    //可写
    fun setData(data: T) {
    }
    //不可读，报错
//    fun getData(): T?{
//        return null
//    }
}

class Teacher<out T> {
    //不可写，报错
//    fun setData(data: T) {
//    }

    //可读
    fun getData(): T? {
        return null
    }
}


open class fuclass {
    var name1 = "fuclass"
}

class ziclass : fuclass() {
    var name = "ziclass"
}

fun main() {

    /**
     * TODO in out 的读写模式
     */
    var list: MutableList<out fuclass> = ArrayList<ziclass>()
    var temp = ziclass()
//    list.add(temp)//不能修改  报错
    var item1 = list.get(0)//获取

    var list2: MutableList<in ziclass> = ArrayList<fuclass>()
//    list2.add(fuclass())
    var item = list2[0]
    println("name=${(item as ziclass).name},name1=${item.name1}")

}
