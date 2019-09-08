package com.jxqm.jiangdou.ui.login.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created By bhx On 2019/8/6 0006 10:08
 */
class LoginRepository : BaseEventRepository() {

    fun sendSmsCode(params: Map<String, String>) {
        apiService.sendSmsCode(params).action {
            LogUtils.i("获取验证码成功")
        }
    }
}