/**
 * FileName: FixedDataDisableScrollRecyclerView
 * Author: shiwenliang
 * Date: 2021/7/6 16:00
 * Description: 自动变大且不可以滚动的RecycleView
 */
package com.leon.customui.fixeddatadisablescrollrecycleview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FixedDataDisableScrollRecyclerView extends RecyclerView{
    public FixedDataDisableScrollRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public FixedDataDisableScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FixedDataDisableScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FixedDataDisableScrollRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        FixedDataDisableAdaper bannerAdapter = new FixedDataDisableAdaper((ArrayList<String>) getBanner());
        setAdapter(bannerAdapter);
    }

    private List<String> getBanner() {
        List<String> data = new ArrayList<>();
        data.add("ParentView item 0");
        data.add("ParentView item 1");
        data.add("ParentView item 2");
        data.add("ParentView item 3");
        data.add("ParentView item 4");
        data.add("ParentView item 5");
        data.add("ParentView item 6");
        data.add("ParentView item 7");
        data.add("ParentView item 8");
        data.add("ParentView item 9");
        data.add("ParentView item 10");
        data.add("ParentView item 11");
        data.add("ParentView item 12");
        data.add("ParentView item 13");
        data.add("ParentView item 14");
        data.add("ParentView item 15");
        data.add("ParentView item 16");
        data.add("ParentView item 17");
        data.add("ParentView item 18");
        data.add("ParentView item 19");
        data.add("ParentView item 20");
        return data;
    }

}
