/**
 * FileName: BaseHolder
 * Author: shiwenliang
 * Date: 2021/5/25 13:54
 * Description:
 */
package com.leon.base.mvvm.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseHolder<VB : ViewDataBinding?, M : ItemModel?>(itemView: ItemViewImpl<VB, M>) :
    RecyclerView.ViewHolder(itemView.binding!!.getRoot()) {

    private val childView: ItemViewImpl<VB, M> = itemView

    fun bindData(position: Int, aaa: M) {
        childView.setData(position, aaa)
    }

}

