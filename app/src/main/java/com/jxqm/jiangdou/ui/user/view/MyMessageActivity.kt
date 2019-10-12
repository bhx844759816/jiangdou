package com.jxqm.jiangdou.ui.user.view

import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.user.vm.MyMessageViewModel

/**
 * 我的消息
 */
class MyMessageActivity : BaseDataActivity<MyMessageViewModel>(){
    override fun getLayoutId(): Int= R.layout.activity_my_message

    override fun getEventKey(): Any  = Constants

}