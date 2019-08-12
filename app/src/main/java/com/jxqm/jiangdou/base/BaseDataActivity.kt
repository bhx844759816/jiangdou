package com.jxqm.jiangdou.base

import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.mvvm.BaseViewModel
import com.bhx.common.utils.StatusBarUtil

/**
 * 公共类
 * Created By bhx On 2019/8/6 0006 09:38
 */
abstract class BaseDataActivity<T : BaseViewModel<*>> : BaseMVVMActivity<T>() {

    override fun initView() {
        super.initView()
        StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this) //设置白底黑字
    }

}