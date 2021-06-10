/**
 * FileName: HttpExceptionUtils
 * Author: shiwenliang
 * Date: 2021/5/25 16:43
 * Description:
 */
package com.leon.common.utils

import android.os.Message
import com.google.gson.JsonParseException
import retrofit2.HttpException

class HttpExceptionUtils {
    companion object {
        val UNAUTHORIZED = 401
        val FORBIDDEN = 403
        val NOT_FOUND = 404
        val REQUEST_TIMEOUT = 408
        val INTERNAL_SERVER_ERROR = 500
        val BAD_GATEWAY = 502
        val SERVICE_UNAVAILABLE = 503
        val GATEWAY_TIMEOUT = 504

        fun handleException(e: Throwable): ResponseThrowable {
            e.printStackTrace()
            var exception: ResponseThrowable= ResponseThrowable(e,ERROR.UNKNOWN)
            when (e) {
                is HttpException -> {
                    exception = ResponseThrowable(e, ERROR.HTTP_ERROR)
                    exception.msg = "网络错误"
                    return exception
                }
                is ServerException -> {
                    exception = ResponseThrowable(e, e.code)
                    exception.msg = e.msg.toString()
                    return exception
                }
                is JsonParseException -> {

                }

                else -> {

                }
            }

            return exception!!
        }
    }
}

class ResponseThrowable(throwable: Throwable, var code: Int) : Exception(throwable) {
    var msg: String = ""
}

class ServerException(code: Int, msg: Message) : RuntimeException() {
    var code = code
    var msg = msg
}

object ERROR {
    /**
     * 未知错误
     */
    val UNKNOWN = 1000

    /**
     * 解析错误
     */
    val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    val NETWORD_ERROR = 1002

    /**
     * 协议出错
     */
    val HTTP_ERROR = 1003

    /**
     * 证书出错
     */
    val SSL_ERROR = 1005

    /**
     * 连接超时
     */
    val TIMEOUT_ERROR = 1006
}