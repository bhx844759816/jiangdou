package com.jxqm.jiangdou.ui.attestation.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.http.handleResult
import io.reactivex.functions.Consumer

/**
 * Created By bhx On 2019/9/12 0012 15:30
 */
class CompanyAttestationRepository : BaseEventRepository() {
    fun getCompanyType() {
        addDisposable(
            apiService.getCompanyType()
                .compose(handleResult())
                .subscribe {
                   sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION,Constants.TAG_GET_COMPANY_TYPE_RESULT,it)
                }

        )
    }

    fun getCompanyPeople() {
        addDisposable(
            apiService.getCompanyPeople()
                .compose(handleResult())
                .subscribe {
                    sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION,Constants.TAG_GET_COMPANY_PEOPLE_RESULT,it)
                }
        )
    }


    fun getCompanyJobType() {
        addDisposable(
            apiService.getCompanyJobType()
                .compose(handleResult())
                .subscribe {
                    sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION,Constants.TAG_GET_COMPANY_JOB_TYPE_RESULT,it)
                }
        )
    }
}