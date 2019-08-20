package com.jxqm.jiangdou.ui.home.view

import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.vm.HomeViewModel

/**
 * 首页Fragment
 * Created by Administrator on 2019/8/20.
 */
class HomeFragment : BaseMVVMFragment<HomeViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_HOME
}