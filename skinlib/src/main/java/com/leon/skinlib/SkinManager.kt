/**
 * FileName: SkinManager
 * Author: shiwenliang
 * Date: 2021/7/1 16:49
 * Description:
 */
package com.leon.skinlib

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import com.leon.skinlib.utils.SkinResources
import java.util.*


class SkinManager : Observable {
    var mContext: Application? = null

    constructor(application: Application) {
        mContext = application
        SkinPreference.init(mContext as Context)
        SkinResources.init(mContext as Context)
        var applicationLifeCycle = ApplicationLifeCycle(this)
        mContext?.registerActivityLifecycleCallbacks(applicationLifeCycle)
        loadSkin(SkinPreference.getInstance().getSkin())
    }

    fun loadSkin(skinPath: String?) {
        if (TextUtils.isEmpty(skinPath)) {
            SkinPreference.getInstance().reset()
            SkinResources.getInstance().reset()
        } else {
            //宿主app的资源
            val appResources = mContext?.resources
            val assetManager = AssetManager::class.java.newInstance()
            val method = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            method.invoke(assetManager, skinPath)

            val skinResources =
                Resources(assetManager, appResources?.displayMetrics, appResources?.configuration)

            val packageManager = mContext?.packageManager
            if (skinPath != null) {
                //获取皮肤包的包名
                val packageArchiveInfo =
                    packageManager?.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)

                var packageName = packageArchiveInfo?.packageName
                SkinResources.getInstance().loadSkin(skinResources, packageName)
                SkinPreference.getInstance().setSkin(skinPath)
            }
        }

        setChanged()
        notifyObservers(null)
    }

    companion object {
        private var instance: SkinManager? = null

        fun init(application: Application) {
            if (instance == null) {
                synchronized(SkinManager@ this) {
                    if (instance == null) {
                        instance = SkinManager(application)
                    }
                }
            }
        }


        fun getInstance(): SkinManager {
            return instance!!
        }
    }

}