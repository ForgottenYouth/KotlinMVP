/**
 * FileName: ApplicationLivecycleCallBack
 * Author: shiwenliang
 * Date: 2021/7/1 16:44
 * Description:
 */
package com.leon.skinlib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import com.leon.skinlib.utils.SkinThemeUtils
import java.lang.reflect.Field
import java.util.*


class ApplicationLifeCycle : Application.ActivityLifecycleCallbacks {

    private var mObserable: Observable? = null
    private var mLayoutInflaterFactories: ArrayMap<Activity, SkinLayoutInflaterFactory> = ArrayMap()

    constructor(observable: Observable) {
        mObserable = observable
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        /**
         * 更新状态栏
         */
        SkinThemeUtils.updateStatusBarColor(activity)

        //获得Activity的布局加载器
        var layoutInflater = activity.layoutInflater
        val skinLayoutInflaterFactory = SkinLayoutInflaterFactory(activity)
        try {
            //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
            //如设置过抛出一次
            //设置 mFactorySet 标签为false

            var field: Field = LayoutInflaterCompat::class.java.getDeclaredField("sCheckedField")
            field.setAccessible(true)
            field.setBoolean(layoutInflater, false)

            var methods = LayoutInflaterCompat::class.java.declaredMethods
            var method = methods.find { it.name == "forceSetFactory2" }
            method?.isAccessible = true
            method?.invoke(LayoutInflaterCompat::class, layoutInflater,skinLayoutInflaterFactory)
            mLayoutInflaterFactories.put(activity, skinLayoutInflaterFactory)
            mObserable?.addObserver(skinLayoutInflaterFactory)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        val observer = mLayoutInflaterFactories.remove(activity)
        SkinManager.getInstance().deleteObserver(observer)
    }
}