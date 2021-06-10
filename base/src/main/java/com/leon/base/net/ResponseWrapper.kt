package com.leon.base.net

/**
 * TODO 包装返回的数据格式
 */
data class ResponseWrapper<T>(val data: T, val errorCode: Int, val errorMsg: String)
