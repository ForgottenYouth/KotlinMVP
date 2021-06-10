/**
 * FileName: BaseAdapter
 * Author: shiwenliang
 * Date: 2021/6/1 16:29
 * Description:
 */
package com.leon.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

abstract class BaseAdapter<VB : ViewDataBinding, VH : BaseHolder, T : Serializable>(
    var context: Context,
    var layout: Int
) : RecyclerView.Adapter<VH>() {

    var viewDataBinding: VB? = null
    var dataList: List<T>? = null
    var itemClickCallback: ItemClickCallback<T>? = null
    var itemLongClickCallback: ItemLongClickCallback<T>? = null
    var itemSubViewClickCallBack: ItemSubViewClickCallBack<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        viewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false)
        return BaseHolder(viewDataBinding!!) as VH
    }

    override fun getItemCount(): Int {
        return if (dataList == null)
            0
        else {
            dataList!!.size
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (dataList?.size!! > 0) {

            convert(position, holder.binding as VB, dataList!![position])
            holder.binding.root.setOnClickListener {
                if (itemClickCallback != null) {
                    itemClickCallback?.clickItem(position, dataList!![position])
                }
            }

            holder.binding.root.setOnLongClickListener {
                if (itemLongClickCallback != null) {
                    itemLongClickCallback?.longClickItem(position, dataList!![position])
                }
                true
            }
        }
    }

    abstract fun convert(position: Int, binding: VB, bean: T)
}

interface ItemSubViewClickCallBack<T> {
    fun itemSubViewClick(viewId: Int, position: Int, bean: T)
}

interface ItemLongClickCallback<T> {
    fun longClickItem(position: Int, bean: T)
}

interface ItemClickCallback<T> {
    fun clickItem(position: Int, bean: T)
}
