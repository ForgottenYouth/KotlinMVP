/**
 * FileName: SkinLayoutInflaterFactory
 * Author: shiwenliang
 * Date: 2021/7/1 15:37
 * Description: 用来接管系统的view的生产过程
 */
package com.leon.skinlib

import android.R.attr
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.leon.skinlib.utils.SkinThemeUtils
import java.lang.Exception
import java.lang.reflect.Constructor
import java.util.*


class SkinLayoutInflaterFactory : LayoutInflater.Factory2, Observer {

    private val mClassPrefixList = arrayOf(
        "android.widget.",
        "android.webkit.",
        "android.app.",
        "android.view."
    )

    private val mConstructorSignature = arrayOf(
        Context::class.java,
        AttributeSet::class.java
    )
    private val sConstructorMap = HashMap<String, Constructor<out View?>>()

    var skinAttribute: SkinAttribute
    var mContext: Context

    constructor(context: Context) {
        mContext = context
        skinAttribute = SkinAttribute()
    }

    /**
     * TODO 实现系统的工厂接口
     */
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var createView = createSDKView(name, context, attrs)
        if (createView == null) {
            createView = createView(name, context, attrs)
        }
        if (createView != null) {
            skinAttribute.look(createView, attrs)
        }
        return createView
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        var constructor: Constructor<out View?>? = sConstructorMap.get(attr.name)

        try {
            if (null == constructor) {
                val clazz =
                    context.classLoader.loadClass(name).asSubclass(View::class.java)
                constructor = clazz.getConstructor(*mConstructorSignature)

                //此处是记录已经创建的view构造器，下次遇到相同的view的时候就不需要再次反射构造了
                if (constructor != null) {
                    sConstructorMap.put(name, constructor)
                }
            }
            if (constructor != null) {
                //创建view的实例
                return constructor.newInstance(context, attrs)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return null
    }

    /**
     * TODO 通过反射创建系统的view
     */
    private fun createSDKView(name: String, context: Context, attrs: AttributeSet): View? {
        if (-1 != name.indexOf('.')) {
            return null
        }

        mClassPrefixList.forEach {
            val createView = createView(it + name, context, attrs)
            if (createView != null) {
                return createView
            }
        }
        return null

    }

    /**
     * TODO 当前工厂作为一个观察者，收到换肤的通知后，循环让每个view自行加载对应的资源
     */
    override fun update(o: Observable?, arg: Any?) {
        SkinThemeUtils.updateStatusBarColor(mContext as Activity)
        skinAttribute.applySkins()
    }

}