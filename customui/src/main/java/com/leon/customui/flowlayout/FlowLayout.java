/**
 * FileName: FlowLayout
 * Author: shiwenliang
 * Date: 2021/6/17 10:11
 * Description:
 */
package com.leon.customui.flowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item横向间距

    private List<List<View>> allLines = new ArrayList<>(); // 记录所有的行，一行一行的存储，用于layout
    List<Integer> lineHeights = new ArrayList<>(); // 记录每一行的行高，用于layout

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * TODO java 创建对象使用构造
     */
    public FlowLayout(Context context) {
        super(context);
    }

    /**
     * TODO xml使用的构造函数
     */
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * TODO 主题style的构造
     */
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * TODO 自定义属性
     */
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void clearParams() {
        allLines.clear();
        lineHeights.clear();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();
        int curL = getPaddingLeft();
        int curT = getPaddingTop();
        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);
            int lineHeight = lineHeights.get(i);
            for (int i1 = 0; i1 < lineViews.size(); i1++) {
                View childView = lineViews.get(i1);
                int left = curL;
                int top = curT;
                int right = left + childView.getMeasuredWidth();
                int bottom = top + childView.getMeasuredHeight();
                childView.layout(left, top, right, bottom);
                curL = right + mHorizontalSpacing;
            }
            curT = curT + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        clearParams();
        //父亲的信息
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);  //解析的父亲给我的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec); //解析的父亲给我的高度

        List<View> lineViews = new ArrayList<>(); //保存一行中的所有的view
        int lineWidthUsed = 0; //记录这行已经使用了多宽的size
        int lineHeight = 0; // 一行的行高

        int parentNeededWidth = 0;  // measure过程中，子View要求的父ViewGroup的宽
        int parentNeededHeight = 0; // measure过程中，子View要求的父ViewGroup的高

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                LayoutParams childLP = childView.getLayoutParams();

                //测量child
                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLP.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLP.height);
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                //获取child的width ,height
                int childMeasureWidth = childView.getMeasuredWidth();
                int childMeasureHeight = childView.getMeasuredHeight();

                //换行处理
                if (childMeasureWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;

                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);

                    lineViews = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }

                //将child添加到一行视图数组中去
                lineViews.add(childView);
                lineWidthUsed += childMeasureWidth + mHorizontalSpacing;
                lineHeight = Math.max(lineHeight, childMeasureHeight);

                if (i == childCount - 1) {
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;

                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                }
            }

        }

        //配置自己的宽高
        int width_mode = MeasureSpec.getMode(parentNeededWidth);
        int height_mode = MeasureSpec.getMode(parentNeededHeight);

        int realParentWidth = width_mode == MeasureSpec.EXACTLY ? selfWidth : parentNeededWidth;
        int realParentHeigt = height_mode == MeasureSpec.EXACTLY ? selfHeight : parentNeededHeight;
        setMeasuredDimension(realParentWidth, realParentHeigt);
    }
}
