package com.jxqm.jiangdou.ui.publish.view

import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.publish.vm.JobManagerViewModel

/**
 * 兼职管理界面
 * Created By bhx On 2019/8/13 0013 14:13
 */
class JobManagerActivity : BaseDataActivity<JobManagerViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_job_manager

    override fun getEventKey(): Any = Constants.EVENT_KEY_JOB_MANAGER
}