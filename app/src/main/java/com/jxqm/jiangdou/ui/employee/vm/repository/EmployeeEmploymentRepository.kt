package com.jxqm.jiangdou.ui.employee.vm.repository

import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.EmployeeWorkBaseItem
import com.jxqm.jiangdou.model.JobEmployeeBaseModel
import io.reactivex.functions.Consumer

/**
 * 雇员 - 已录用
 * Created By bhx On 2019/9/24 0024 16:08
 */
class EmployeeEmploymentRepository : BaseEventRepository() {

    /**
     * 获取雇员 - 已录用列表
     */
    fun getEmployeeOfferList() {
        val jobSignWrapList = mutableListOf<JobEmployeeBaseModel>()

        addDisposable(
            apiService.getEmployeeOfferList().flatMap {
                if (it.code == "0") {
                    it.data.forEach { jobEmployeeModel ->
                        jobSignWrapList.add(jobEmployeeModel)
                    }
                }
                return@flatMap apiService.getEmployeeInvalidList().compose(applySchedulers())
            }.compose(applySchedulers())
                .subscribe({
                    if(it.code == "0"){

                    }
                }, {

                })
        )

    }
}