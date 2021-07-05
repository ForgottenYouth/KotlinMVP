/**
 * FileName: LoginActivity
 * Author: shiwenliang
 * Date: 2021/5/21 14:33
 * Description:
 */
package com.leon.login

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.leon.base.config.RoutePath
import com.leon.base.mvp.MVPBaseActivity
import com.leon.common.utils.GotoActivityUtils
import com.leon.common.utils.toast
import com.leon.login.databinding.ActivityUserLoginBinding
import com.leon.login.entity.LoginResponse
import com.leon.login.loginmvp.impl.LoginModuleImpl
import com.leon.login.loginmvp.impl.LoginPresenterImpl
import com.leon.login.loginmvp.inter.ILoginView
import kotlinx.android.synthetic.main.activity_user_login.*

@Route(path = RoutePath.LOGIN)
class LoginActivity :
    MVPBaseActivity<ActivityUserLoginBinding, LoginModuleImpl, ILoginView, LoginPresenterImpl>(),
    ILoginView {
    private val onCLickLister = View.OnClickListener { view ->
        when (view.id) {
            // 登录
            R.id.user_login_bt -> {
                loginAction()
            }
            // 注册
            R.id.user_register_tv -> {
                registAction()
            }
            R.id.forgot_pwd -> {
                forgetPwdAction()
            }
            R.id.phone_quick_login -> {
                phoneQuickLoginAction()
            }

            // else->
        }
    }

    private fun phoneQuickLoginAction() {
    }

    private fun forgetPwdAction() {
    }

    private fun registAction() {
        GotoActivityUtils.gotoActivity(RegistActivity::class.java, null, LoginActivity@ this)
    }

    fun loginAction() {
//        presenter?.loginAction(
//            this@LoginActivity,
//            binding.userPhoneEt.text.toString(),
//            user_password_et.text.toString()
//        )
        ARouter.getInstance().build(RoutePath.MAIN).navigation()
    }

    override fun loginSuccess(result: LoginResponse?) {
        this.toast("登录成功😀")
        ARouter.getInstance().build(RoutePath.MAIN).navigation()
    }

    override fun loginFailure(message: String?) {
        this.toast("登录失败 ~ 呜呜呜")
    }


    override fun initView() {
        user_login_bt.setOnClickListener(onCLickLister)
        user_register_tv.setOnClickListener(onCLickLister)
    }

    override fun getLayoutId(): Int = R.layout.activity_user_login

    override fun createP(): LoginPresenterImpl = LoginPresenterImpl(this)
}