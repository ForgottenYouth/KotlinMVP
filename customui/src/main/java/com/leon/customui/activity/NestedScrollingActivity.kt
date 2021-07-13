/**
 * FileName: NestedScrollingActivity
 * Author: shiwenliang
 * Date: 2021/7/5 17:51
 * Description:
 */
package com.leon.customui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.leon.customui.R
import java.util.*

class NestedScrollingActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nestedscroll_layout)

        viewPager = findViewById(R.id.viewpager_view)
        val pagerAdapter = ViewPagerAdapter(this, getPageFragments())
        viewPager.adapter = pagerAdapter

        val labels = arrayOf("linear", "scroll", "recycler")
        TabLayoutMediator(
            findViewById(R.id.tablayout), viewPager
        ) { tab, position -> tab.text = labels[position] }.attach()
    }

    private fun getPageFragments(): List<Fragment>? {
        val data: MutableList<Fragment> = ArrayList()
        data.add(RecyclerViewFragment())
        data.add(RecyclerViewFragment())
        data.add(RecyclerViewFragment())
        return data
    }
}