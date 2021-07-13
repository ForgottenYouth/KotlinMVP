/**
 * FileName: CustomUIMainActivity
 * Author: shiwenliang
 * Date: 2021/6/17 10:14
 * Description:
 */
package com.leon.customui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.leon.base.config.RoutePath
import com.leon.customui.R
import com.leon.skin.SkinActivity

@Route(path = RoutePath.CUSTOMUI)
class CustomUIMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customui_main)
    }

    fun onclick(view: View) {
        when (view.id) {
            R.id.flow -> {
                startActivity(Intent(this, FlowActivity::class.java))
            }
            R.id.nestedscroll -> {
                startActivity(Intent(this, NestedScrollingActivity::class.java))
            }
            R.id.skin -> {
                startActivity(Intent(this, SkinActivity::class.java))
            }
            else -> {

            }
        }
    }
}