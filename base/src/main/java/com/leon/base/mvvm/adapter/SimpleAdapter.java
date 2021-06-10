package com.leon.base.mvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2020/12/7, 007
 * @description
 */
public abstract class SimpleAdapter<VB extends ViewDataBinding, T> extends RecyclerView.Adapter<SimpleHolder> {
    protected Context context;
    @IdRes
    private int layout;

    public List<T> getDataList() {
        return dataList;
    }

    protected List<T> dataList = new ArrayList<>();

    protected OnItemClickListener<T> itemClickListener;
    private OnItemLongClickListener<T> itemLongClickListener;

    public SimpleAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setData(List<T> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public final SimpleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleHolder(DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false));
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        } else {
            return dataList.size();
        }
    }

    @Override
    public final void onBindViewHolder(@NonNull SimpleHolder holder, int position) {
        if (dataList.size() > 0) {
//            convert(position, (VB) holder.binding, dataList.get(position));
//            holder.binding.getRoot().setOnClickListener(v -> {
//                if (itemClickListener != null) {
//                    itemClickListener.clickItem(position, dataList.get(position));
//                }
//            });
//            holder.binding.getRoot().setOnLongClickListener(v -> {
//                if (itemLongClickListener != null) {
//                    itemLongClickListener.longclickItem(position, dataList.get(position));
//                }
//                return true;
//            });
        }
    }

    protected abstract void convert(int position, VB binding, T t);

    public interface OnItemClickListener<T> {
        void clickItem(int position, T t);
    }

    public interface OnItemLongClickListener<T> {
        void longclickItem(int position, T t);
    }

    public SimpleAdapter<VB, T> setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    public SimpleAdapter<VB, T> setItemLongClickListener(OnItemLongClickListener<T> itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
        return this;
    }
}
