/**
 * FileName: CollectAdapter
 * Author: shiwenliang
 * Date: 2021/6/1 16:16
 * Description:
 */
package com.leon.main.fragment

import android.content.Context
import com.leon.base.adapter.BaseAdapter
import com.leon.base.adapter.BaseHolder
import com.leon.base.database.Student
import com.leon.main.R
import com.leon.main.databinding.ListitemCollectLayoutBinding

class CollectAdapter(context: Context) :
    BaseAdapter<ListitemCollectLayoutBinding, BaseHolder, Student>(
        context, R.layout.listitem_collect_layout
    ) {
    override fun convert(position: Int, binding: ListitemCollectLayoutBinding, bean: Student) {
        binding.viewmodel = bean
    }
}