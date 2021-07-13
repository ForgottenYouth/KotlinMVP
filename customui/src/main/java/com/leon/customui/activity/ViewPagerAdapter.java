/**
 * FileName: ViewPagerAdapter
 * Author: shiwenliang
 * Date: 2021/7/12 16:18
 * Description:
 */
package com.leon.customui.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> data;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
