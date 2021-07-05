/**
 * FileName: SkinResources
 * Author: shiwenliang
 * Date: 2021/6/30 15:32
 * Description:
 */
package com.leon.skinlib.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


class SkinResources {

    private var mSkinPkgName: String? = null
    private var isDefaultSkin = true

    companion object {
        private var instance: SkinResources? = null
        var mContext: Context? = null

        // app原始的resource
        private var mAppResources: Resources? = null

        // 皮肤包的resource
        private var mSkinResources: Resources? = null

        fun init(context: Context) {
            this.mContext = context
            mAppResources = context.getResources()
        }

        fun getInstance(): SkinResources {
            if (instance == null) {
                kotlin.synchronized(SkinResources@ this) {
                    if (instance == null) {
                        if (mContext != null) {
                            instance = SkinResources()
                        }
                    }
                }
            }
            return instance!!
        }
    }


    fun reset() {
        mSkinResources = null
        mSkinPkgName = ""
        isDefaultSkin = true
    }

    fun loadSkin(resources: Resources?, pkgName: String?) {
        mSkinResources = resources
        mSkinPkgName = pkgName
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null
    }

    /**
     * 1.通过原始app中的resId(R.color.XX)获取到自己的 名字
     * 2.根据名字和类型获取皮肤包中的ID
     */
    private fun getIdentifier(resId: Int): Int? {
        if (isDefaultSkin) {
            return resId
        }
        val resName: String = mAppResources?.getResourceEntryName(resId) ?: ""
        val resType: String =
            mAppResources?.getResourceTypeName(resId) ?: ""

        return mSkinResources?.getIdentifier(
            resName,
            resType,
            mSkinPkgName
        )
    }

    /**
     * 输入主APP的ID，到皮肤APK文件中去找到对应ID的颜色值
     * @param resId
     * @return
     */
    fun getColor(resId: Int): Int? {
        if (isDefaultSkin) {
            return mAppResources?.getColor(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources?.getColor(resId)
        } else skinId?.let { mSkinResources?.getColor(it) }
    }

    fun getColorStateList(resId: Int): ColorStateList? {
        if (isDefaultSkin) {
            return mAppResources?.getColorStateList(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources?.getColorStateList(resId)
        } else skinId?.let { mSkinResources?.getColorStateList(it) }
    }

    fun getDrawable(resId: Int): Drawable? {
        if (isDefaultSkin) {
            return mAppResources?.getDrawable(resId)
        }
        //通过 app的resource 获取id 对应的 资源名 与 资源类型
        //找到 皮肤包 匹配 的 资源名资源类型 的 皮肤包的 资源 ID
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources?.getDrawable(resId)
        } else skinId?.let { mSkinResources?.getDrawable(it) }
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    fun getBackground(resId: Int): Any? {
        val resourceTypeName: String =
            mAppResources?.getResourceTypeName(resId) ?: ""
        return if ("color" == resourceTypeName) {
            getColor(resId)
        } else {
            // drawable
            getDrawable(resId)
        }
    }
}