package com.jxqm.jiangdou.ui.job.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created by Administrator on 2019/8/17.
 */
class JobSearchRepository : BaseEventRepository() {
    /**
     * 获取热门搜索列表
     */
    fun getHotSearchList() {
        addDisposable(
            apiService.getHotSearchList().action {
               sendData(Constants.EVENT_KEY_JOB_SEARCH,Constants.GET_HOT_SEARCH_LIST_SUCCESS,it)
            }
        )
    }
}