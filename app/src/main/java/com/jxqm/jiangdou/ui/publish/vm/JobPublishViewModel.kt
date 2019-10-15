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

    fun publishJob(mapFilePath: String, paramsMap: MutableMap<String,String>) {
        mRepository.publishJob(mapFilePath, paramsMap)
    }

    /**
     * 更新发布的
     */
    fun updatePublishJob(mapFilePath: String, paramsMap: MutableMap<String,String>){
        mRepository.updatePublishJob(mapFilePath, paramsMap)
    }
}