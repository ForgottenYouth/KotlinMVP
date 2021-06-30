/**
 * FileName: SkinActivity
 * Author: shiwenliang
 * Date: 2021/6/18 14:24
 * Description:
 */
package com.leon.skin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.leon.base.config.RoutePath
import com.leon.customui.R

@Route(path = RoutePath.SKIN)
class SkinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.skin_main)
    }
}