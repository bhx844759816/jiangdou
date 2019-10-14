package com.jxqm.jiangdou.ui.employee.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.JobEmployeeBaseModel
import com.jxqm.jiangdou.model.JobEmployeeTitleModel

/**
 * 雇员 - 已录用
 * Created By bhx On 2019/9/24 0024 16:08
 */
class EmployeeEmploymentRepository : BaseEventRepository() {

    /**
     * 获取雇员 - 已录用列表
     */
    fun getEmployeeOfferList() {
        val jobOfferWrapList = mutableListOf<JobEmployeeBaseModel>()
        addDisposable(
            apiService.getEmployeeOfferList().flatMap {
                if (it.code == "0") {
                    it.data.forEach { jobEmployeeModel ->
                        jobOfferWrapList.add(jobEmployeeModel)
                    }
                }
                return@flatMap apiService.getEmployeeInvalidList().compose(applySchedulers())
            }.compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.isNotEmpty()) {
                            jobOfferWrapList.add(JobEmployeeTitleModel())
                            for (jobSignModel in it.data) {
                                jobOfferWrapList.add(jobSignModel)
                            }
                        }
                    }
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_EMPLOYMENT,
                        Constants.TAG_GET_EMPLOYEE_EMPLOYMENT_LIST_SUCCESS,
                        jobOfferWrapList
                    )
                }, {
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_EMPLOYMENT,
                        Constants.TAG_GET_EMPLOYEE_EMPLOYMENT_LIST_ERROR,
                        it.localizedMessage
                    )
                })
        )

    }

    /**
     * 接受offer
     */
    fun acceptOffer(offerId: Int) {
        addDisposable(
            apiService.acceptOffer(offerId)
                .action {
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_EMPLOYMENT,
                        Constants.TAG_ACCEPT_REFUSE_OFFER_SUCCESS, true
                    )
//                    TAG_ACCEPT_REFUSE_OFFER_SUCCESS
                }
        )
    }

    /**
     * 拒绝Offer
     */
    fun refuseOffer(offerId: Int) {
        addDisposable(
            apiService.refuseOffer(offerId)
                .action {
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_EMPLOYMENT,
                        Constants.TAG_ACCEPT_REFUSE_OFFER_SUCCESS, true
                    )
                }
        )
    }

    /**
     * 清空失效
     */
    fun clearInvalidOffer() {
        addDisposable(
            apiService.clearInvalidOffer()
                .action {
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_EMPLOYMENT,
                        Constants.TAG_ACCEPT_REFUSE_OFFER_SUCCESS, true
                    )
                }
        )
    }
}