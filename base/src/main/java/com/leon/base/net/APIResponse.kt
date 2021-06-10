/**
 * FileName: APIResponse
 * Author: shiwenliang
 * Date: 2021/5/21 15:09
 * Description:
 */
package com.leon.base.net

import android.content.Context
import com.leon.base.dialog.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class APIResponse<T>(val context: Context) : Observer<ResponseWrapper<T>> {
    private var isShow: Boolean = true

    constructor(context: Context, isShow: Boolean = false) : this(context) {
        this.isShow = isShow
    }

    abstract fun success(data: T)
    abstract fun failure(errorMessage: String?)

    override fun onSubscribe(d: Disposable) {
        if (isShow) {
            LoadingDialog.show(context)
        }
    }

    override fun onNext(t: ResponseWrapper<T>) {
        if (t.data == null) {
            failure(t.errorMsg)
        } else {
            success(t.data)
        }
    }

    override fun onComplete() {
        LoadingDialog.cancel()
    }

    override fun onError(e: Throwable) {
        LoadingDialog.cancel()
        failure(e.message)
    }
}