/**
 * FileName: CustomUIMainActivity
 * Author: shiwenliang
 * Date: 2021/6/17 10:14
 * Description:
 */
package com.leon.customui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.leon.base.config.RoutePath
import com.leon.customui.R

@Route(path = RoutePath.CUSTOMUI)
class CustomUIMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customui_main)
    }
}