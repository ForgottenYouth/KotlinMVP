/**
 * FileName: MainActivity
 * Author: shiwenliang
 * Date: 2021/6/1 10:43
 * Description:
 */
package com.leon.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.alibaba.android.arouter.facade.annotation.Route
import com.leon.base.config.RoutePath
import com.leon.main.R
import kotlinx.android.synthetic.main.activity_main_layout.*

@Route(path = RoutePath.MAIN)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_collect, R.id.navigation_personal
        ).build()

        NavigationUI.setupActionBarWithNavController(
            this,
            Navigation.findNavController(this, nav_host_fragment.id),
            appBarConfiguration
        )
        NavigationUI.setupWithNavController(
            nav_view,
            Navigation.findNavController(this, nav_host_fragment.id)
        )
    }
}