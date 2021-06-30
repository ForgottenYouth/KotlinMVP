/**
 * FileName: SkinLayoutInflater
 * Author: shiwenliang
 * Date: 2021/6/18 16:00
 * Description:
 */
package com.leon.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class SkinLayoutInflater extends LayoutInflater {
    protected SkinLayoutInflater(Context context) {
        super(context);
    }

    protected SkinLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return null;
    }

    @Override
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return super.onCreateView(parent, name, attrs);
    }

}
