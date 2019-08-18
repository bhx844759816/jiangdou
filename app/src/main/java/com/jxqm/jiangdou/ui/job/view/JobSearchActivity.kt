package com.jxqm.jiangdou.ui.job.view

import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.job.vm.JobSearchViewModel

/**
 * 工作搜索界面
 * Created by Administrator on 2019/8/17.
 */
class JobSearchActivity : BaseDataActivity<JobSearchViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_job_search

    override fun getEventKey(): Any = Constants.EVENT_KEY_JOB_SEARCH
}