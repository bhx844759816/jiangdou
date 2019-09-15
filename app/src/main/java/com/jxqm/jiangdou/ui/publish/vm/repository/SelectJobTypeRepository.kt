package com.jxqm.jiangdou.ui.publish.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created By bhx On 2019/8/8 0008 09:10
 */
class SelectJobTypeRepository : BaseEventRepository() {
    /**
     * 获取兼职类型
     */
    fun getJobType() {

        addDisposable(apiService.getJobType()
            .action {
                LogUtils.i("getJobType$it")
                val hotList = it.filter { jobType ->
                    jobType.hot
                }
                val moreList = it.filter { jobType ->
                    !jobType.hot
                }
                //发送热门类型,
                sendData(Constants.EVENT_KEY_SELECT_JOB_TYPE,Constants.TAG_SELECT_JOB_TYPE_HOT,hotList)

                //发送更多类型
                sendData(Constants.EVENT_KEY_SELECT_JOB_TYPE,Constants.TAG_SELECT_JOB_TYPE_MORE,moreList)
            })
    }
}