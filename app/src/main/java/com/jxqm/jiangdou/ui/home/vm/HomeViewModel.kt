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
    fun getHomeData() {
        mRepository.getHomeData(pageNo, pageSize) {
            pageNo++
        }
    }

    /**
     * 获取推荐列表
     * @param isRefresh 是否是刷新加载
     */
    fun getRecommend(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getHomeRecommend(pageNo, pageSize) {
            pageNo++
        }
    }
}