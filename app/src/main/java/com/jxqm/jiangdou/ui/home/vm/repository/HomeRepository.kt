package com.jxqm.jiangdou.ui.home.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.http.applySchedulersForLoadingDialog
import com.jxqm.jiangdou.model.JobDetailsWrapModel
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.model.SwpierModel
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * 主页面数据
 * Created by Administrator on 2019/8/20.
 */
class HomeRepository : BaseEventRepository() {

    /**
     * 获取首页推荐列表
     */
    fun getHomeRecommend(pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getHomeRecommend(pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        val records = it.data.records
                        if (records.size < it.data.total) {
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_MAIN_HOME,
                            Constants.TAG_GET_HOME_RECOMMEND_LIST, records
                        )
                    }

                }, {

                })
        )
    }

    /**
     * 获取首页第一次加载的所有数据
     * 轮播图
     * 兼职类型
     * 兼职帮助Item
     * 推荐列表
     */
    fun getHomeData(pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            Observable.concat(
                apiService.getHomeSwiper(),
                apiService.getHomeHotJobType(),
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
                                    Constants.TAG_GET_HOME_JOB_TYPE, it.data
                                )
                            } else if (model is SwpierModel) {
                                //轮播图
                                sendData(
                                    Constants.EVENT_KEY_MAIN_HOME,
                                    Constants.TAG_GET_HOME_SWIPER, it.data
                                )
                            }
                        }
                    }
                }, {

                })
        )
    }

    /**
     * 下拉刷新
     */
    fun getHomeDataRefresh(pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            Observable.concat(
                apiService.getHomeSwiper(),
                apiService.getHomeHotJobType(),
                apiService.getHomeRecommend(pageNo, pageSize)
            ).compose(applySchedulers())
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
                                    Constants.TAG_GET_HOME_JOB_TYPE, it.data
                                )
                            } else if (model is SwpierModel) {
                                //轮播图
                                sendData(
                                    Constants.EVENT_KEY_MAIN_HOME,
                                    Constants.TAG_GET_HOME_SWIPER, it.data
                                )
                            }
                        }
                    }
                }, {

                })
        )
    }

    /**
     * 上拉加载更多
     */
    fun getHomeDataLoadMore(pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getHomeRecommend(pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe {
                    if (it.code == "0") {
                        val jobDetailsWrapModel = it.data
                        if (jobDetailsWrapModel.records.size < jobDetailsWrapModel.total) {
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_MAIN_HOME,
                            Constants.TAG_GET_HOME_RECOMMEND_LIST, jobDetailsWrapModel.records
                        )
                    }
                }
        )
    }
}