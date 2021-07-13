/**
 * FileName: FixedDataDisableAdaper
 * Author: shiwenliang
 * Date: 2021/7/12 15:15
 * Description: 给不可自己滑动的recyclerview的适配器
 */
package com.leon.customui.fixeddatadisablescrollrecycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leon.customui.R

class FixedDataDisableAdaper(private val dataList: ArrayList<String>) :
    RecyclerView.Adapter<FixedDataDisableAdaper.ViewHolder>() {

    class ViewHolder : RecyclerView.ViewHolder {
        var item: TextView = itemView.findViewById(R.id.itemtext)

        constructor(itemView: View) : super(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_disablescroll_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.text = dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}