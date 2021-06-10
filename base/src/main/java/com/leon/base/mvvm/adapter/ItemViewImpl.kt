/**
 * FileName: ItemViewImpl
 * Author: shiwenliang
 * Date: 2021/5/25 13:57
 * Description:
 */
package com.leon.base.mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class ItemViewImpl<VB : ViewDataBinding?, M : ItemModel?>
    (context: Context?, parent: ViewGroup?) : ViewModel() {
    var binding: VB

    init {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), parent, false)
    }

    protected abstract fun getLayoutId(): Int

    abstract fun setData(position: Int, itemData: M)

}
