package com.jxqm.jiangdou.ui.attestation.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.attestation.vm.repository.CompanyAttestationRepository

/**
 * Created By bhx On 2019/9/12 0012 15:30
 */
class CompanyAttestationViewModel : BaseViewModel<CompanyAttestationRepository>() {

    fun getAttestationData() {
        //获取认证信息
        //包含认证所需的人员规模 企业。。。。
        mRepository.getAttestationData()
    }

    fun getAttestationStatus() {
        mRepository.getAttestationStatus()
    }
//
//    /**
//     * 获取企业类型
//     */
//    fun getCompanyType() {
//        mRepository.getCompanyType()
//    }
//
//    /**
//     * 获取人员规模
//     */
//    fun getCompanyPeople() {
//        mRepository.getCompanyPeople()
//    }
//
//    /**
//     * 获取所属行业
//     */
//    fun getCompanyJobType() {
//        mRepository.getCompanyJobType()
//    }
}