package com.leon.base.mvvm.adapter;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wulei
 * @date 2020/12/7, 007
 * @description
 */
public class SimpleHolder extends RecyclerView.ViewHolder {

    public final ViewDataBinding binding;

    public SimpleHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
