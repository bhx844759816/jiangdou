package com.jxqm.jiangdou.ui.home.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.home.vm.repository.HomeRepository

/**
 * 首页
 * Created by Administrator on 2019/8/20.
 */
class HomeViewModel : BaseViewModel<HomeRepository>() {
    var pageNo = 1
    val pageSize = 10
    //获取首页组合数据
    fun getHomeData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getHomeData(pageNo, pageSize) {
            pageNo++
        }
    }

    /**
     * 下拉刷新
     */
    fun getHomeDataRefresh() {
        pageNo = 1
        mRepository.getHomeDataRefresh(pageNo, pageSize) {
            pageNo++
        }
    }

    /**
     * 上拉加载更多
     */
    fun getHomeDataLoadMore() {
        mRepository.getHomeDataLoadMore(pageNo, pageSize) {
            pageNo++
        }
    }

}