/**
 * FileName: SkinPreference
 * Author: shiwenliang
 * Date: 2021/6/30 15:25
 * Description: 用sp保存用户选择的皮肤
 */
package com.leon.skinlib

import android.content.Context
import android.content.SharedPreferences
import com.leon.skinlib.utils.SkinResources
import kotlinx.coroutines.InternalCoroutinesApi


class SkinPreference {
    companion object {
        private var mPref: SharedPreferences? = null
        private val SKIN_SHARED = "skins"
        private val KEY_SKIN_PATH = "skin-path"
        private var instance: SkinPreference? = null

        fun init(context: Context) {
            mPref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE)
        }

        fun getInstance(): SkinPreference {
            if (instance == null) {
                synchronized(SkinPreference@ this) {
                    if (instance == null) {
                        instance = SkinPreference()
                    }
                }
            }
            return instance!!
        }
    }


    fun setSkin(skinPath: String?) {
        mPref!!.edit().putString(KEY_SKIN_PATH, skinPath).apply()
    }

    fun reset() {
        mPref!!.edit().remove(KEY_SKIN_PATH).apply()
    }

    fun getSkin(): String? {
        return mPref!!.getString(KEY_SKIN_PATH, null)
    }
}


