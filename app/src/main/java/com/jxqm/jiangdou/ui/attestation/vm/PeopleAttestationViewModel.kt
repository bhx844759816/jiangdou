package com.jxqm.jiangdou.ui.attestation.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.attestation.vm.repository.PeopleAttestationRepository
import java.io.File

/**
 * Created By bhx On 2019/8/9 0009 11:59
 */

class PeopleAttestationViewModel : BaseViewModel<PeopleAttestationRepository>() {

    /**
     * 提交文件和参数
     */
    fun submit(fileMaps: MutableMap<String, File>, paramsMaps: MutableMap<String, String>) {
        mRepository.submit(fileMaps, paramsMaps)
    }
}