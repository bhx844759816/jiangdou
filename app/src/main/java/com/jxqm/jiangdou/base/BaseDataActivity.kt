package com.jxqm.jiangdou.base

import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.mvvm.BaseViewModel

/**
 * 公共类
 * Created By bhx On 2019/8/6 0006 09:38
 */
abstract class BaseDataActivity<T : BaseViewModel<*>> : BaseMVVMActivity<T>() {

    override fun initView() {
        super.initView()

    }



}