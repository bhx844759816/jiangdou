package com.jxqm.jiangdou.ui.home.view

import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.vm.WorkViewModel

/**
 * 工作台界面
 * Created by Administrator on 2019/8/20.
 */
class WorkFragment : BaseMVVMFragment<WorkViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_work

    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_WORK
}