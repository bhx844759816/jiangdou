package com.jxqm.jiangdou.ui.attestation.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.*
import com.jxqm.jiangdou.model.AttestationStatusModel
import io.reactivex.Observable

/**
 * Created By bhx On 2019/9/12 0012 15:30
 */
class CompanyAttestationRepository : BaseEventRepository() {
    fun getAttestationStatus() {
        addDisposable(
            apiService.getAttestationStatus()
                .compose(applySchedulers())
                .subscribe()
        )
    }

    /**
     * 获取所有的Item
     */
    fun getAttestationData() {
        addDisposable(
            Observable.concat(
                apiService.getAttestationStatus(),
                apiService.getCompanyType(),
                apiService.getCompanyPeople(),
                apiService.getCompanyJobType()
            ).compose(applySchedulersForLoadingDialog())
                .subscribe({
                    if (it.code == "0") {
                        //获取数据成功
                        if (it.data is AttestationStatusModel) {
                            //认证信息
                            sendData(
                                Constants.EVENT_KEY_COMPANY_ATTESTATION,
                                Constants.TAG_GET_COMPANY_ATTESTATION_STATUS,
                                it.data
                            )
                        }
                        if (it.data is List<*>) {
                            //获取所属行业，企业类型，人员规模数据
                            sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION,
                                Constants.TAG_GET_COMPANY_ITEM_RESULT, it.data)
                        }
                    }
                }, {
                    LogUtils.i("getAttestationData error:${it.localizedMessage}")
                })
        )
    }
//    .compose(handleResultForLoadingDialog())
//    .filter {
//        false
//    }
//    .flatMap {
//        //获取企业认证状态
//        //如果已提交获取认证详细信息
//        apiService.getAttestationDetails()
//    }
//    .compose(handleResult())
//    .subscribe {
//        LogUtils.i("获取企业认证状态")
//    }
//
//    fun getCompanyType() {
//        addDisposable(
//            apiService.getCompanyType().action {
//
//            }
//        )
//    }
//
//    fun getCompanyPeople() {
//        addDisposable(
//            apiService.getCompanyPeople().action {
//                sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION, Constants.TAG_GET_COMPANY_PEOPLE_RESULT, it)
//            }
//        )
//    }
//
//
//    fun getCompanyJobType() {
//        addDisposable(
//            apiService.getCompanyJobType().action {
//                sendData(Constants.EVENT_KEY_COMPANY_ATTESTATION, Constants.TAG_GET_COMPANY_JOB_TYPE_RESULT, it)
//            }
//        )
//    }
}