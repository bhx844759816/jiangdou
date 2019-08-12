package com.jxqm.jiangdou.ui.login.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.login.vm.repository.LoginRepository

/**
 * Created By bhx On 2019/8/6 0006 10:05
 */
class LoginViewModel : BaseViewModel<LoginRepository>() {
    /**
     * 调用登录接口
     */
    fun doLogin(phone: String) {

    }

    /**
     * 第三方登录
     */
    fun doThirdLogin(type: Int) {

    }
}