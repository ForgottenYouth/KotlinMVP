/**
 * FileName: CollectFragment
 * Author: shiwenliang
 * Date: 2021/6/1 10:55
 * Description:
 */
package com.leon.main.fragment

import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.leon.base.mvp.IMVPBaseFragment
import com.leon.main.R
import com.leon.main.collectmvp.impl.CollectModuleImpl
import com.leon.main.collectmvp.impl.CollectionPresneterImpl
import com.leon.main.collectmvp.inter.ICollectView
import com.leon.base.database.Student
import com.leon.common.utils.toast
import com.leon.main.databinding.FragmentCollectLayoutBinding
import com.leon.skinlib.SkinManager
import kotlinx.android.synthetic.main.fragment_collect_layout.*

class CollectFragment :
    IMVPBaseFragment<FragmentCollectLayoutBinding, CollectModuleImpl, ICollectView, CollectionPresneterImpl>(),
    ICollectView {

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.person_menu, menu)
    }

    override fun getLayoutId(): Int = R.layout.fragment_collect_layout

    override fun createP(): CollectionPresneterImpl? = CollectionPresneterImpl(this)

    override fun showResultSuccess(result: List<Student>?) {
        recyclerView.layoutManager = LinearLayoutManager(CollectFragment@ this.requireActivity())
        var adapter = CollectAdapter(CollectFragment@ this.requireContext())
        adapter.dataList = result
        recyclerView.adapter = adapter
    }

    override fun showResult(result: String) {
        this.activity?.toast(result)
    }

    override fun initViews() {
        binding?.apply {
            addData.setOnClickListener {
                var student = Student("name", 10)
                presenter?.insert(student)
            }

            clearData.setOnClickListener {
//                presenter?.deleteAll()
                SkinManager.getInstance().loadSkin("/data/data/com.leon.kotlinretrofitdemo/goldskinapk-debug.apk")
            }
        }
    }

    override fun loadData() {
        presenter?.queryAll()
    }

}