/**
 * FileName: IMVPBaseFragment
 * Author: shiwenliang
 * Date: 2021/6/1 10:57
 * Description: Fragment的抽象父类
 */
package com.leon.base.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class IMVPBaseFragment<VB : ViewDataBinding, M : IMVPBaseModule, V : IMVPBaseView, P : IMVPBasePresenter<V, M>> :
    Fragment() {
    var presenter: P? = null
    lateinit var binding: VB

    private lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createP()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        initViews()
        return binding.root
    }


    init {
        setHasOptionsMenu(true)
    }

    /**
     * TODO 加载视图
     */
    abstract fun initViews()

    /**
     * TODO 加载数据
     */
    abstract fun loadData()

    /**
     * TODO 获取布局id
     */
    abstract fun getLayoutId(): Int

    /**
     * TODO 创建p层的实例
     */
    abstract fun createP(): P?

    override fun onDestroy() {
        super.onDestroy()
        recycle()
    }

    fun hideActionBar() {
        mActivity?.supportActionBar?.hide()
    }

    fun showActionBar() {
        mActivity?.supportActionBar?.show()
    }

    private fun recycle() {
        presenter?.unAttachView()
    }
}