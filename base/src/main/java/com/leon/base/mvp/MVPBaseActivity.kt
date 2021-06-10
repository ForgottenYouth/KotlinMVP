/**
 * FileName: BaseActivity
 * Author: shiwenliang
 * Date: 2021/5/27 14:42
 * Description:
 */
package com.leon.base.mvp

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType

abstract class MVPBaseActivity<VB : ViewDataBinding, M : IMVPBaseModule, V : IMVPBaseView, P : IMVPBasePresenter<V, M>> :
    AppCompatActivity() {
    var presenter: P? = null
    lateinit var binding: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        presenter = createP()
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        recycle()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

    abstract fun createP(): P

    private fun recycle() {
        presenter?.unAttachView()
    }
}