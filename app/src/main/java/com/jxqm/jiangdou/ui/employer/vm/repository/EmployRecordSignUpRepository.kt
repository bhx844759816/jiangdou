package com.jxqm.jiangdou.ui.employer.vm.repository

import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import okhttp3.RequestBody

/**
 * 雇佣记录 - 已报名
 * Created By bhx On 2019/9/24 0024 15:08
 */
class EmployRecordSignUpRepository : BaseEventRepository() {
    /**
     * 获取已报名的员工
     */
    fun getSignUpEmployee(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getSignUpEmployeeList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    LogUtils.i("getSignUpEmployee$it")
                    if (it.code == "0") {
                        if (it.data.records.isNotEmpty()) { //当前列表超过10条数据
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_EMPLOY_RECORD_SIGN_UP,
                            Constants.TAG_GET_EMPLOYEE_LIST_SUCCESS,
                            it.data.records
                        )
                    } else {
                        sendData(
                            Constants.EVENT_KEY_EMPLOY_RECORD_SIGN_UP,
                            Constants.TAG_GET_EMPLOYEE_LIST_ERROR,
                            it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_EMPLOY_RECORD_SIGN_UP,
                        Constants.TAG_GET_EMPLOYEE_LIST_ERROR,
                        it.localizedMessage!!
                    )
                })
        )
    }

    /**
     * 录用
     */
    fun acceptResume(ids: ArrayList<Long>) {
        val params = mutableMapOf("ids" to ids)
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonString)
        addDisposable(
            apiService.acceptResume(body)
                .action {
                    sendData(
                        Constants.EVENT_KEY_EMPLOY_RECORD_SIGN_UP,
                        Constants.TAG_ACCEPT_OR_REFUSED_RESUME_SUCCESS,
                        true
                    )
                }
        )
    }

    fun regectedResume(ids: ArrayList<Long>){
        val params = mutableMapOf("ids" to ids)
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonString)
        addDisposable(
            apiService.regectedResume(body)
                .action {
                    sendData(
                        Constants.EVENT_KEY_EMPLOY_RECORD_SIGN_UP,
                        Constants.TAG_ACCEPT_OR_REFUSED_RESUME_SUCCESS,
                        true
                    )
                }
        )
    }
}