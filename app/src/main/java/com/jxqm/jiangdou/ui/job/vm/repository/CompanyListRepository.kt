package com.jxqm.jiangdou.ui.job.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers

/**
 * Created by Administrator on 2019/8/17.
 */
class CompanyListRepository : BaseEventRepository() {

    fun getSearchCompanyList(searchKey: String, pageNo: Int, pageSize: Int) {
        addDisposable(
            apiService.getSearchCompanyList(pageNo, pageSize, searchKey).compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {

                        sendData(
                            Constants.EVENT_KEY_SEARCH_COMPANY_LIST,
                            Constants.TAG_GET_SEARCH_COMPANY_LIST_SUCCESS,
                            it.data.records
                        )
                    } else {
                        sendData(
                            Constants.EVENT_KEY_SEARCH_COMPANY_LIST,
                            Constants.TAG_GET_SEARCH_COMPANY_LIST_ERROR,
                            it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_SEARCH_COMPANY_LIST,
                        Constants.TAG_GET_SEARCH_COMPANY_LIST_ERROR,
                        it.localizedMessage
                    )
                })
        )
    }
}