package com.jxqm.jiangdou.ui.home.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer

/**
 * Created by Administrator on 2019/8/20.
 */
class MyRepository : BaseEventRepository() {

    fun getAccountBalance() {
        addDisposable(
            apiService.getAccountBalance().compose(applySchedulers())
                .subscribe({
                    sendData(
                        Constants.EVENT_KEY_MAIN_MY,
                        Constants.TAG_GET_ACCOUNT_BALANCE_SUCCESS,
                        it.data
                    )
                }, {

                })
        )
    }
}