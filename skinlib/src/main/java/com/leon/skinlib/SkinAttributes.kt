/**
 * FileName: SkinAttributes
 * Author: shiwenliang
 * Date: 2021/7/1 10:33
 * Description:
 */
package com.leon.skinlib

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.leon.skinlib.utils.SkinResources
import com.leon.skinlib.utils.SkinThemeUtils

class SkinAttribute {
    var skinViews: MutableList<SkinView> = ArrayList<SkinView>()

    companion object {
        var mAttributes: MutableList<String> = ArrayList<String>()

        init {
            mAttributes.add("background")
            mAttributes.add("src")
            mAttributes.add("textColor")
            mAttributes.add("drawableLeft")
            mAttributes.add("drawableTop")
            mAttributes.add("drawableRight")
            mAttributes.add("drawableBottom")
        }
    }

    //记录下一个VIEW身上哪几个属性需要换肤textColor/src
    fun look(view: View, attrs: AttributeSet) {
        var mSkinPair = ArrayList<SkinPair>()
        var i = 0
        while (i < attrs.attributeCount) {
            val attributeName = attrs.getAttributeName(i) ?: continue
            if (mAttributes.contains(attributeName)) {
                val attributeValue = attrs.getAttributeValue(i)
                if (attributeValue.startsWith("#")) {
                    continue
                }
                var resId: Int = if (attributeValue.startsWith("?")) {
                    var attrId = attributeValue.substring(1).toInt()
                    SkinThemeUtils.getResId(view.context, IntArray(1, { attrId }))[0]
                } else {
                    //正常以@开头
                    attributeValue.substring(1).toInt()
                }
                var skinPair: SkinPair = SkinPair(attributeName, resId)
                mSkinPair.add(skinPair)
            }
            i++
        }

        if (!mSkinPair.isEmpty() || view as? SkinViewSupport != null) {
            var skinView = SkinView(view, mSkinPair)
            skinView.applySkin()
            skinViews.add(skinView)
        }
    }

    /**
     * TODO 对所有的view循环进行换肤
     */
    fun applySkins() {
        skinViews.forEach {
            it.applySkin()
        }
    }

}

class SkinView {

    var view: View
    var skinPairs: List<SkinPair>

    constructor(view: View, skinPairs: List<SkinPair>) {
        this.view = view
        this.skinPairs = skinPairs
    }

    /**
     * TODO 更新view自己的皮肤
     */
    fun applySkin() {
        applySkinSupport()
        skinPairs.forEach {
            var left: Drawable? = null
            var right: Drawable? = null
            var top: Drawable? = null
            var bottom: Drawable? = null
            if (it.resId == 0) {
                //有些设置了的背景或者颜色为null的情况，直接跳过，继续后面的
                return@forEach
            }
            when (it.attributeName) {
                "background" -> {
                    var background = SkinResources.getInstance().getBackground(it.resId)
                    if (background is Int) {
                        view.setBackgroundColor(background)
                    } else {
                        view.background = background as Drawable
                    }
                }
                "src" -> {
                    var background = SkinResources.getInstance().getBackground(it.resId)
                    if (background is Int) {
                        (view as ImageView).setImageDrawable(ColorDrawable(background))
                    } else {
                        (view as ImageView).setImageDrawable(background as Drawable)
                    }
                }
                "textColor" -> {
                    val colorId = SkinResources.getInstance().getColor(it.resId)
                    if (colorId != null) {
                        (view as TextView).setTextColor(colorId)
                    }
                }
                "drawableLeft" -> {
                    left = SkinResources.getInstance().getDrawable(it.resId)
                }
                "drawableRight" -> {
                    right = SkinResources.getInstance().getDrawable(it.resId)
                }
                "drawableTop" -> {
                    top = SkinResources.getInstance().getDrawable(it.resId)
                }
                "drawableBottom" -> {
                    bottom = SkinResources.getInstance().getDrawable(it.resId)
                }
                else -> {

                }
            }

            if (left != null || top != null || right != null || bottom != null) {
                (view as TextView).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
            }
        }
    }

    private fun applySkinSupport() {
        if (view is SkinViewSupport) {
            (view as SkinViewSupport).applyShin()
        }
    }
}

class SkinPair(
    var attributeName: String = "",
    var resId: Int = 0
)