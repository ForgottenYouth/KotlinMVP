/**
 * FileName: RegistActivity
 * Author: shiwenliang
 * Date: 2021/5/26 10:09
 * Description:注册
 */
package com.leon.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.leon.base.config.RoutePath
import com.leon.base.mvp.MVPBaseActivity
import com.leon.common.utils.toast
import com.leon.login.databinding.ActivityRegistLayoutBinding
import com.leon.login.entity.RegistResponse
import com.leon.login.registmvp.impl.RegistModuleImpl
import com.leon.login.registmvp.impl.RegistPresenterImpl
import com.leon.login.registmvp.inter.IRegistView
import kotlinx.android.synthetic.main.activity_regist_layout.*

@Route(path = RoutePath.REGIST)
class RegistActivity :
    MVPBaseActivity<ActivityRegistLayoutBinding, RegistModuleImpl, IRegistView, RegistPresenterImpl>(),
    IRegistView {

    override fun initView() {
        binding.userRegisterBt.setOnClickListener {
            presenter?.registAction(
                this@RegistActivity, binding.userPhoneEt.text.toString(),
                binding.userPasswordEt.text.toString(), binding.userPasswordEt.text.toString()
            )
        }

    }

    override fun getLayoutId(): Int = R.layout.activity_regist_layout


    override fun registSuccess(result: RegistResponse?) {
        this.toast("注册成功😀")
        this.finish()
    }

    override fun registFailure(error: String) {
        this.toast(error)
    }

    override fun createP(): RegistPresenterImpl = RegistPresenterImpl(this)
}