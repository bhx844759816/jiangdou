package com.jxqm.jiangdou.ui.employee.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.JobEmployeeBaseModel
import com.jxqm.jiangdou.model.JobEmployeeTitleModel

/**
 * 雇员 - 已报名
 * Created By bhx On 2019/9/24 0024 16:02
 */
class EmployeeSignUpRepository : BaseEventRepository() {
    fun getSignList() {
        LogUtils.i("getSignList request")
        val jobSignWrapList = mutableListOf<JobEmployeeBaseModel>()
        addDisposable(
            apiService.getEmployeeSignList().flatMap {
                if (it.code == "0") {
                    for (jobSignModel in it.data) {
                        jobSignWrapList.add(jobSignModel)
                    }
                }
                return@flatMap apiService.getEmployeeClosedSignList().compose(applySchedulers())
            }.compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.isNotEmpty()) {
                            jobSignWrapList.add(JobEmployeeTitleModel())
                            for (jobSignModel in it.data) {
                                jobSignWrapList.add(jobSignModel)
                            }
                        }
                    }
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_SIGN_UP,
                        Constants.TAG_GET_EMPLOYEE_SIGN_LIST_SUCCESS,
                        jobSignWrapList
                    )
                }, {
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_SIGN_UP,
                        Constants.TAG_GET_EMPLOYEE_EMPLOYMENT_LIST_ERROR,
                        it.localizedMessage
                    )
                }, {

                })

        )


//        addDisposable(
//            Observable.concat(
//                apiService.getEmployeeSignList(),
//                apiService.getEmployeeClosedSignList()
//            ).compose(applySchedulers())
//                .subscribe({
//                    if (it.code == "0") {
//                        it.data.map { jobSignModel ->
//                            jobSignModel.jobStatus = jobStatus
//                            jobSignList.add(jobSignModel)
//                            val jobSignModelWrap = JobSignModelWrap()
//                        }
//                        jobStatus++
//                    } else {
//                        sendData(
//                            Constants.EVENT_KEY_EMPLOYEE_SIGN_UP,
//                            Constants.TAG_GET_EMPLOYEE_SIGN_LIST_ERROR,
//                            it.message
//                        )
//                    }
//                }, {
//                    sendData(
//                        Constants.EVENT_KEY_EMPLOYEE_SIGN_UP,
//                        Constants.TAG_GET_EMPLOYEE_SIGN_LIST_ERROR,
//                        it.localizedMessage
//                    )
//                }, {
//                    sendData(
//                        Constants.EVENT_KEY_EMPLOYEE_SIGN_UP,
//                        Constants.TAG_GET_EMPLOYEE_SIGN_LIST_SUCCESS,
//                        jobSignList
//                    )
//                })
//
//        )

    }
}