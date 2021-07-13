/**
 * FileName: NestedScrollLayout
 * Author: shiwenliang
 * Date: 2021/7/6 15:45
 * Description:自定义内嵌滚动的布局
 */
package com.leon.customui.nestedscrolllayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class NestedScrollLayout extends NestedScrollView {
    private FlingHelper mFlingHelper;
    private boolean isStartFling;

    int totalDy = 0;
    private int velocityY;
    private View topView;
    private ViewGroup contentView;

    public NestedScrollLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public NestedScrollLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFlingHelper = new FlingHelper(getContext());
        setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false;
                }
                if (scrollY == 0) {
                }
                if (scrollY == (getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    dispatchChildFling();
                }
                //在RecyclerView fling情况下，记录当前RecyclerView在y轴的偏移
                totalDy += scrollY - oldScrollY;
            }
        });
    }

    private void dispatchChildFling() {
        if (velocityY != 0) {
            Double splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY);
            if (splineFlingDistance > totalDy) {
                childFling(mFlingHelper.getVelocityByDistance(splineFlingDistance - Double.valueOf(totalDy)));
            }
        }
        totalDy = 0;
        velocityY = 0;
    }

    private void childFling(int velY) {
        RecyclerView childRecyclerView = getChildRecyclerView(contentView);
        if (childRecyclerView != null) {
            childRecyclerView.fling(0, velY);
        }
    }

    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RecyclerView ) {
                return (RecyclerView) viewGroup.getChildAt(i);
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                ViewGroup childRecyclerView = getChildRecyclerView((ViewGroup) viewGroup.getChildAt(i));
                if (childRecyclerView instanceof RecyclerView) {
                    return (RecyclerView) childRecyclerView;
                }
            }
            continue;
        }
        return null;
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY <= 0) {
            this.velocityY = 0;
        } else {
            isStartFling = true;
            this.velocityY = velocityY;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = ((ViewGroup) getChildAt(0)).getChildAt(0);
        contentView = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams lp= contentView.getLayoutParams();
        lp.height=getMeasuredHeight();
        contentView.setLayoutParams(lp);
    }
}
