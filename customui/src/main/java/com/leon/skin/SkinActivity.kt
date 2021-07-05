/**
 * FileName: SkinActivity
 * Author: shiwenliang
 * Date: 2021/6/18 14:24
 * Description:
 */
package com.leon.skin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.leon.base.config.RoutePath
import com.leon.customui.R
import com.leon.skinlib.SkinManager

@Route(path = RoutePath.SKIN)
class SkinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.skin_main)

        findViewById<Button>(R.id.change).setOnClickListener {
            change()
        }

        findViewById<Button>(R.id.org).setOnClickListener {
            org()
        }
    }

    fun change() {
        SkinManager.getInstance().loadSkin("/data/data/com.leon.kotlinretrofitdemo/goldskinapk-debug.apk")
    }

    fun org() {
        SkinManager.getInstance().loadSkin(null)
    }
}