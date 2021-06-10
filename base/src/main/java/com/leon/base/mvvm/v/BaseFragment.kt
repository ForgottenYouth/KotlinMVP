package com.leon.base.mvvm.v

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding?> : Fragment() {
    private var isViewCreated = false
    private var currentVisibleState = false
    private var isFirstVisible = true
    protected var binding: VB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        initView()
        initData()
        if (!isHidden && userVisibleHint) {
            dispatchVisibleState(true)
        }
        binding?.getRoot()?.isClickable = true
        return binding?.getRoot()
    }

    override fun onResume() {
        super.onResume()
        Log.i(this.javaClass.simpleName, "onResume: ")
        //因为onResume可能会在跳转Activity的时候反复执行，但是不是每一次都需要执行true分发
        //存在一个情况。ViewPager+Fragment ，当tab1 滑到 tab2 时， tab3 会执行完整的生命周期 onCreate-onCreateView-onViewCreated-onStart-onResume 但是此时tab3并不是可见的，
        // 没有必要执行true分发
        if (!isFirstVisible) { //只有在不是第一次可见的时候，才进入逻辑,由于isFirstVisible默认是true，所以，第一次进入onResume不会执行true分发
            if (!isHidden && !currentVisibleState && userVisibleHint) //没有隐藏，当前状态为不可见，系统的可见hint为true 同时满足
            // 这个会发生在 Activity1 中 是ViewPager+Fragment时，如果从某个Fragment跳转到activity2，再跳回Activity1，那么 Activity1中的多个Fragment会同时执行onResume，
            //但是不会所有的fragment都是可见的，所以我只需要对可见的Fragment进行true分发
                dispatchVisibleState(true)
        }
    }

    override fun onPause() {
        super.onPause()
        if (currentVisibleState && userVisibleHint) {
            dispatchVisibleState(false)
        }
    }

    override fun onDestroyView() {
        Log.i(this.javaClass.simpleName, "onDestroyView: ")
        super.onDestroyView()
        reset()
    }

    private fun reset() {
        isViewCreated = false
        isFirstVisible = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isViewCreated) {
            if (currentVisibleState && !isVisibleToUser) {
                dispatchVisibleState(false)
            } else if (!currentVisibleState && isVisibleToUser) {
                dispatchVisibleState(true)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        dispatchVisibleState(!hidden)
    }

    private fun dispatchVisibleState(isVisible: Boolean) {
        if (isVisible && isParentInvisible) {
            return
        }
        if (isVisible == currentVisibleState) {
            return
        }
        currentVisibleState = isVisible
        if (isVisible) {
            if (isFirstVisible) {
                isFirstVisible = false
                onFragmentFirstVisible()
            }
            onFragmentResume()
            dispatchChildVisibilityState(true)
        } else {
            onFragmentPause()
            dispatchChildVisibilityState(false)
        }
    }

    // 默认可见
    private val isParentInvisible: Boolean
        private get() {
            val parent = parentFragment
            if (parent is BaseFragment<*>) {
                return !parent.currentVisibleState
            }
            return false // 默认可见
        }

    private fun dispatchChildVisibilityState(isVisible: Boolean) {
        val fragmentManager = childFragmentManager
        val list = fragmentManager.fragments
        if (list != null) {
            for (fg in list) {
                if (fg is BaseFragment<*>
                    && !fg.isHidden() && fg.getUserVisibleHint()
                ) { //  判断可见要双重判定，isHidden和getUserVisibleHint
                    fg.dispatchVisibleState(isVisible)
                }
            }
        }
    }

    protected fun onFragmentPause() {
        Log.i(this.javaClass.simpleName, "onFragmentPause: ")
    }

    protected fun onFragmentResume() {
        Log.i(this.javaClass.simpleName, "onFragmentResume: ")
    }

    protected fun onFragmentFirstVisible() {
        Log.i(this.javaClass.simpleName, "onFragmentFirstVisible: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(this.javaClass.simpleName, "onViewCreated: ")
    }

    protected abstract val layoutResId: Int
    protected abstract fun initView()
    protected abstract fun initData()
    open fun showToast(str: String?) {
//        Toast.makeText(BaseApplication.getInstance(), str, Toast.LENGTH_SHORT).show();
    }

    open fun showToast(stringId: Int) {
        showToast(resources.getString(stringId))
    }
}