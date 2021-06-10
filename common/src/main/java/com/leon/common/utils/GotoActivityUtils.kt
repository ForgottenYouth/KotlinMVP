package com.leon.common.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

object GotoActivityUtils {
    fun gotoActivity(
        activityClass: Class<*>,
        bundle: Bundle?,
        activity: Activity,
        requstCode: Int = -1
    ) {
        var intent = Intent(activity, activityClass)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        if (requstCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requstCode)
        }
    }
}