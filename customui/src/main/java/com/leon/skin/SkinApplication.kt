/**
 * FileName: SkinApplication
 * Author: shiwenliang
 * Date: 2021/7/1 19:17
 * Description:
 */
package com.leon.skin

import android.app.Application
import com.leon.skinlib.SkinManager

class SkinApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
    }
}