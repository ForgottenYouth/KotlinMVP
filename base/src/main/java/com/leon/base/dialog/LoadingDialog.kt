package com.leon.base.dialog

import android.app.Dialog
import android.content.Context
import com.leon.base.R

object LoadingDialog {

    private var dialog: Dialog? = null

    fun show(context: Context) {
        cancel()
        dialog = Dialog(context)
        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    fun cancel() {
        dialog?.dismiss()
    }
}