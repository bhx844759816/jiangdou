package com.jxqm.jiangdou.ui.job.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.job.vm.repository.CompanyListRepository

/**
 * 搜索公司列表
 * Created by Administrator on 2019/8/17.
 */
class CompanyListViewModel : BaseViewModel<CompanyListRepository>() {
    private var pageNo: Int = 1
    private var pageSize: Int = Constants.PAGE_SIZE

    fun getSearchCompanyList(searchKey: String, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getSearchCompanyList(searchKey, pageNo, pageSize) {
            pageNo++
        }
    }
}