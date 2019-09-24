package com.jxqm.jiangdou.ui.home.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulersForLoadingDialog
import com.jxqm.jiangdou.model.JobDetailsWrapModel
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.model.SwpierModel
import io.reactivex.Observable

/**
 * 主页面数据
 * Created by Administrator on 2019/8/20.
 */
class HomeRepository : BaseEventRepository() {
    /**
     * 获取首页轮播图
     */
    fun getHomeSwiper() {
        addDisposable(
            apiService.getHomeSwiper().action {

            }
        )
    }

    /**
     * 获取首页推荐列表
     */
    fun getHomeRecommend(pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getHomeRecommend(pageNo, pageSize)
                .action {
                    if (it.records.size < it.total) {
                        callBack.invoke()
                    }
                    sendData(
                        Constants.EVENT_KEY_MAIN_HOME,
                        Constants.TAG_GET_HOME_RECOMMEND_LIST, it.records
                    )
                }
        )
    }

    fun getHomeData(pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            Observable.concat(
                apiService.getHomeSwiper(),
                apiService.getHomeJobType(),
                apiService.getHomeRecommend(pageNo, pageSize)
            ).compose(applySchedulersForLoadingDialog())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data is JobDetailsWrapModel) {
                            val jobDetailsWrapModel = it.data as JobDetailsWrapModel
                            if (jobDetailsWrapModel.records.size < jobDetailsWrapModel.total) {
                                callBack.invoke()
                            }
                            sendData(
                                Constants.EVENT_KEY_MAIN_HOME,
                                Constants.TAG_GET_HOME_RECOMMEND_LIST, jobDetailsWrapModel.records
                            )
                        }

                        if (it.data is List<*>) {
                            val list = it.data as List<*>
                            val model = list[0]
                            if (model is JobTypeModel) {
                                //兼职类型
                                sendData(
                                    Constants.EVENT_KEY_MAIN_HOME,
                                    Constants.TAG_GET_HOME_SWIPER, it.data
                                )
                            } else if (model is SwpierModel) {
                                //轮播图
                                sendData(
                                    Constants.EVENT_KEY_MAIN_HOME,
                                    Constants.TAG_GET_HOME_JOB_TYPE, it.data
                                )
                            }
                        }
                    }
                }, {

                })
        )
    }
}