package com.leon.base.mvvm.v

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.leon.base.mvvm.m.IModel
import com.leon.base.mvvm.vm.BaseVM
import java.lang.reflect.ParameterizedType

/**
 * @date 2020/12/7, 007
 * @description
 */
abstract class BaseMVVMFragment<VM : BaseVM<out IView?, out IModel?>?, VB : ViewDataBinding?> :
    BaseFragment<VB>(), IView {
    protected var viewModel: VM? = null
    @CallSuper
    override fun initView() {
        val pt = javaClass.genericSuperclass as ParameterizedType
        val cls = pt.actualTypeArguments[0] as Class<VM>
        viewModel = ViewModelProvider(this)[cls]
        viewModel!!.bindView(this)
    }

    override fun showToast(str: String?) {
        super.showToast(str)
    }

    override fun showToast(stringId: Int) {
        super.showToast(stringId)
    }
}