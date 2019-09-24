package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.MyResumeRepository
import java.io.File

/**
 * Created By bhx On 2019/8/19 0019 14:07
 */
class MyResumeViewModel : BaseViewModel<MyResumeRepository>() {

    /**
     * 获取学历列表
     */
    fun getEduList() {
        mRepository.getEduList()
    }

    /**
     *获取用户简历
     */
    fun getUserResume() {
        mRepository.getUserResume()
    }

    /**
     * 上传用户简历
     */
    fun uploadUserResume(paramsMap: Map<String, String>, fileMaps: Map<String, File>, fileList: List<File>) {
        mRepository.upLoadResume(paramsMap, fileMaps, fileList)
    }
}