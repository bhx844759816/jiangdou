package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.http.upload.Params
import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.ComplainDetailsRepository
import java.io.File

class ComplainDetailsViewModel : BaseViewModel<ComplainDetailsRepository>() {

    fun complaintJob(params: Map<String, Any>, files: List<File>) {
        mRepository.complaintJob(files, params)
    }
}