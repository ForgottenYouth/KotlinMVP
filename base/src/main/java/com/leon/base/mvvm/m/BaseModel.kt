/**
 * FileName: BaseModel
 * Author: shiwenliang
 * Date: 2021/5/25 13:54
 * Description:
 */
package com.leon.base.mvvm.m

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*

class BaseModel {
    protected fun getBody(params: Map<String?, Any?>?): RequestBody? {
        var params = params
        if (params == null) {
            params = HashMap()
        }
        return RequestBody.create(
            MediaType.parse("application/json;charset=UTF-8"),
            Gson().toJson(params)
        )
    }
}