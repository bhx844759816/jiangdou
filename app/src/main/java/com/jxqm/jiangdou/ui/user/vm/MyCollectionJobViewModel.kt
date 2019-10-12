package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.MyCollectionJobRepository

class MyCollectionJobViewModel : BaseViewModel<MyCollectionJobRepository>(){
    private var pageSize = 10
    private var pageNo = 1

    fun  getCollectionList(isRefresh:Boolean){
        if(isRefresh){
            pageNo = 1
        }
        mRepository.getCollectionList(pageNo,pageSize){
            pageNo++
        }
    }

    fun cancelCollection(idList:List<Int>){
        mRepository.cancelCollectionList(idList)
    }
}