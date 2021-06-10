package com.leon.base.mvvm.v

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.leon.base.mvvm.m.IModel
import com.leon.base.mvvm.vm.BaseVM
import java.lang.reflect.ParameterizedType

/**
 * @date 12/6/20
 * @description
 */
abstract class BaseMVVMActivity<VM : BaseVM<out IView?, out IModel?>?, VB : ViewDataBinding?> :
    BaseActivity<VB>(), IView {
    //    protected LoadingDialog mLoadingDialog;
    protected var viewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val pt = javaClass.genericSuperclass as ParameterizedType
        val cls = pt.actualTypeArguments[0] as Class<VM>
        viewModel = ViewModelProvider(this)[cls]
        Log.i("BaseMVVMActivity", "onCreate: $this")
        viewModel!!.bindView(this)
        super.onCreate(savedInstanceState)
        viewModel!!.loadingData.observe(this, { aBoolean: Boolean? -> })
    }

    override fun showToast(str: String?) {
        super.showToast(str)
    }

    override fun showToast(stringId: Int) {
        super.showToast(stringId)
    }
}