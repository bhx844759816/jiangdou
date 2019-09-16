package com.jxqm.jiangdou.ui.attestation.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.*
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created By bhx On 2019/9/12 0012 15:30
 */
class CompanyAttestationRepository : BaseEventRepository() {
    fun getAttstationStatus() {
        addDisposable(
            apiService.getAttestationStatus()
                .compose(handleResultForLoadingDialog())
                .filter {
                    false
                }
                .flatMap {
                    //获取企业认证状态
                    //如果已提交获取认证详细信息
                    apiService.getAttestationDetails()
                }
                .compose(handleResult())
                .subscribe {

                }
        )
    }


    fun getCompanyType() {
        addDisposable(
            apiService.getCompanyType().action {
                sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION, Constants.TAG_GET_COMPANY_TYPE_RESULT, it)
            }
        )
    }

    fun getCompanyPeople() {
        addDisposable(
            apiService.getCompanyPeople().action {
                sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION, Constants.TAG_GET_COMPANY_PEOPLE_RESULT, it)
            }
        )
    }


    fun getCompanyJobType() {
        addDisposable(
            apiService.getCompanyJobType().action {
                sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION, Constants.TAG_GET_COMPANY_JOB_TYPE_RESULT, it)
            }
        )
    }
}