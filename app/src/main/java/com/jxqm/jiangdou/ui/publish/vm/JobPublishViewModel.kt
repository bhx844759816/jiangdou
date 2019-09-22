package com.jxqm.jiangdou.ui.publish.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.publish.vm.repository.JobPublishRepository

/**
 * Created by Administrator on 2019/9/14.
 */
class JobPublishViewModel : BaseViewModel<JobPublishRepository>() {

    /**
     * 获取认证信息
     */
    fun getAttestationDetails() {
        mRepository.getAttestationDetails()
    }

    fun publishJob(mapFilePath: String, jobDetailsModel: JobDetailsModel) {
        mRepository.publishJob(mapFilePath, jobDetailsModel)
    }
}