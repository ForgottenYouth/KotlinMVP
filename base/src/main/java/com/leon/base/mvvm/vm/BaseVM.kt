/**
 * FileName: BaseVM
 * Author: shiwenliang
 * Date: 2021/5/25 15:27
 * Description:
 */
package com.leon.base.mvvm.vm

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.leon.base.BaseApplication
import com.leon.base.mvvm.m.IModel
import com.leon.base.mvvm.v.IView

abstract class BaseVM<V : IView?, M : IModel?>(application: BaseApplication) :
    AndroidViewModel(application),
    IViewModel {

    var loadingData = MutableLiveData<Boolean>()

    @JvmName("getLoadingData1")
    fun getLoadingData(): MutableLiveData<Boolean> {
        return loadingData
    }


}