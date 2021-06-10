/**
 * FileName: CollectView
 * Author: shiwenliang
 * Date: 2021/6/1 11:01
 * Description:
 */
package com.leon.main.collectmvp.inter

import com.leon.base.mvp.IMVPBaseView
import com.leon.base.database.Student

interface ICollectView : IMVPBaseView {

    fun showResultSuccess(result: List<Student>?)

    fun showResult(result: String)
}