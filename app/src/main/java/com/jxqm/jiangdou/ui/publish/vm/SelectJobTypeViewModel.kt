package com.jxqm.jiangdou.ui.publish.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.publish.vm.repository.SelectJobTypeRepository

/**
 * Created By bhx On 2019/8/8 0008 09:10
 */
class SelectJobTypeViewModel : BaseViewModel<SelectJobTypeRepository>() {

    /**
     * 获取工作类型
     */
    fun getJobType() {
        mRepository.getJobType()
    }


}