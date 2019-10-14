package com.jxqm.jiangdou.ui.job.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.*
import com.jxqm.jiangdou.model.AttestationStatusModel
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created By bhx On 2019/9/27 0027 10:45
 */
class JobDetailsRepository : BaseEventRepository() {
    /**
     * 报名工作
     */
    fun signUpJob(jobId: String) {
        addDisposable(
            apiService.singUpJob(jobId.toLong())
                .compose(applySchedulersForLoadingDialog())
                .subscribe({
                    if (it.code == "0") {
                        sendData(
                            Constants.EVENT_JOB_DETAILS,
                            Constants.TAG_SIGN_UP_JOB_SUCCESS,
                            true
                        )
                    } else if (it.code == "500501") { //简历未存在
                        sendData(
                            Constants.EVENT_JOB_DETAILS,
                            Constants.TAG_SIGN_UP_RESUME_NOT_EXIST,
                            false
                        )
                    }
                }, {

                })
        )
    }

    /**
     * 获取兼职详情
     */
    fun getJobDetails(jobId: String) {
        addDisposable(
            apiService.getJobDetails(jobId)
                .compose(applySchedulersForLoadingDialog())
                .flatMap {
                    if (it.code == "0") {
                        sendData(
                            Constants.EVENT_JOB_DETAILS,
                            Constants.TAG_GET_JOB_DETAILS_SUCCESS,
                            it.data
                        )
                        return@flatMap apiService.getEmployerDetails(it.data.employerId)
                            .compose(applySchedulers())
                    }
                    return@flatMap Observable.just(it)
                }.subscribe {
                    if (it.code == "0" && it.data is AttestationStatusModel) {
                        sendData(
                            Constants.EVENT_JOB_DETAILS,
                            Constants.TAG_GET_EMPLOYER_DETAILS_SUCCESS,
                            it.data
                        )
                    }
                }

        )
    }

    /**
     * 删除发布的职位
     */
    fun deletePublishJob(jobId: String) {
        addDisposable(
            apiService.deleteJobById(jobId)
                .action {
                    sendData(
                        Constants.EVENT_JOB_DETAILS,
                        Constants.TAG_DELETE_WAIT_PUBLISH_JOB_SUCCESS, jobId
                    )
                }
        )
    }

    /**
     * 收藏职位
     */
    fun collectionJob(jobId: String) {
        addDisposable(
            apiService.collectionJob(jobId.toLong())
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        sendData(
                            Constants.EVENT_JOB_DETAILS,
                            Constants.TAG_COLLECTION_STATUS_CHANGE,
                            true
                        )
                    }
                }, {

                })
        )
    }

    /**
     * 取消收藏职位
     */
    fun cancelCollectionJob(jobId: String) {
        addDisposable(
            apiService.cancelCollectionJob(jobId.toLong()).compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        sendData(
                            Constants.EVENT_JOB_DETAILS,
                            Constants.TAG_COLLECTION_STATUS_CHANGE,
                            false
                        )
                    }
                }, {

                })
        )
    }
}