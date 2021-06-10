package com.wlyc.mvvm.vm

import androidx.lifecycle.viewModelScope
import com.leon.base.BaseApplication
import com.leon.base.mvvm.m.IModel
import com.leon.base.mvvm.v.IView
import com.leon.base.mvvm.vm.BaseVM
import com.leon.base.mvvm.vm.IViewModel
import com.leon.base.net.ResponseWrapper
import com.leon.common.utils.HttpExceptionUtils
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

/**
 * @author Created by wulei
 * @date 2021/1/30, 030
 * @description
 */
open class CoroutineViewModel<V : IView, M : IModel> : BaseVM<V, M>(BaseApplication.getInstance()),
    IViewModel {

    private lateinit var weakReference: WeakReference<V>
    protected val model: M

    init {
        val pt = this.javaClass.genericSuperclass as ParameterizedType
        val clazzM: Class<M> = pt.actualTypeArguments[1] as Class<M>
        model = clazzM.newInstance()
    }

    @Suppress("UNCHECKED_CAST")
    override fun bindView(view: IView) {
        weakReference = WeakReference(view as V)
    }

    private fun showToast(str: String?) = launchMain {
        str?.let { weakReference.get()!!.showToast(it) }
    }


    /**
     * @param tryBlock 传入请求的代码块
     * @param successBlock 成功代码块
     * @param failBlock 失败代码块（可不传，采用默认处理）
     * @param finallyBlock 最终处理代码块（可不传）
     * @param isChain 链式调用（为了防止链式调用时暂时别dismiss loading）
     */
    protected fun <T> launchRequest(
        tryBlock: suspend CoroutineScope.() -> ResponseWrapper<T>?,
        successBlock: suspend CoroutineScope.(T?) -> Unit,
        failBlock: (suspend CoroutineScope.(String?) -> Unit) = {
            launch {
                if (showTips) {
                    showToast(it)
                }
            }
        },
        finallyBlock: (suspend CoroutineScope.() -> Unit)? = null,
        showTips: Boolean = true,
    ) {
        launchOnIO {
            tryCatch(tryBlock, successBlock, failBlock, finallyBlock, showTips)
        }
    }

    fun launchMain(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(Dispatchers.Main, block = block)

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) { block() }

    fun launchOnDefault(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(Dispatchers.Default) { block() }

    /**
     * @param tryBlock 传入请求的代码块
     * @param successBlock 成功代码块
     * @param failBlock 失败代码块
     * @param finallyBlock 最终处理代码块
     * @param isChain 链式调用（为了防止链式调用时暂时别dismiss loading）
     */
    private suspend fun <T> tryCatch(
        tryBlock: suspend CoroutineScope.() -> ResponseWrapper<T>?,
        successBlock: suspend CoroutineScope.(T?) -> Unit,
        failBlock: suspend CoroutineScope.(String?) -> Unit,
        finallyBlock: (suspend CoroutineScope.() -> Unit)? = null,
        showTips: Boolean,
    ) {
        coroutineScope {
            try {
                val response = tryBlock()
                callResponse(response, {
                    response?.let {
                        if (response.data == null) {
                            if (showTips) showToast(response?.errorMsg)
                        }
                        launchMain {
                            successBlock(response.data)
                        }
                    }
                }, {
                    launchMain {
                        failBlock(response?.errorMsg)
                    }
                    if (showTips) showToast(response?.errorMsg)
                })
            } catch (e: Throwable) {
                launchMain {
                    failBlock(HttpExceptionUtils.handleException(e).message)
                }
                if (showTips) showToast(e.message)
            } finally {
                launchMain {
                    finallyBlock?.let { it() }
                }
            }
        }
    }

    private suspend fun <T> callResponse(
        response: ResponseWrapper<T>?,
        successBlock: suspend CoroutineScope.() -> Unit,
        failBlock: suspend CoroutineScope.() -> Unit,
    ) {
        coroutineScope {
            when {
                response == null -> failBlock()
                response.errorCode == 900 || response.errorCode == 0 -> successBlock()
                else -> failBlock()
            }
        }
    }
}