/**
 * FileName: BaseHolder
 * Author: shiwenliang
 * Date: 2021/6/1 16:20
 * Description:
 */
package com.leon.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseHolder(var binding: ViewDataBinding) :RecyclerView.ViewHolder(binding.root) {
}