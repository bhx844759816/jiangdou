package com.jxqm.jiangdou.ui.employee.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.JobEmployeeBaseModel
import com.jxqm.jiangdou.model.JobSignModelBase
import com.jxqm.jiangdou.ui.employee.adapter.EmployWorkListAdapter
import com.jxqm.jiangdou.ui.employee.adapter.SignUpWorkListAdapter
import com.jxqm.jiangdou.ui.employee.vm.EmployeeEmploymentViewModel
import kotlinx.android.synthetic.main.fragment_employee_work_list.*

/**
 * 雇员 - 已录用
 * Created By bhx On 2019/9/24 0024 15:56
 */
class EmployeeEmploymentFragment : BaseMVVMFragment<EmployeeEmploymentViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOYEE_EMPLOYMENT
    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mAdapter: EmployWorkListAdapter
    private val mJobModelList = mutableListOf<JobEmployeeBaseModel>()

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        registerObserver(Constants.TAG_GET_EMPLOYEE_EMPLOYMENT_LIST_SUCCESS, List::class.java).observe(this, Observer {
            val list = it as List<JobEmployeeBaseModel>
            mJobModelList.clear()
            mJobModelList.addAll(list)
            if (mJobModelList.isEmpty()) {
                mUiStatusController.changeUiStatus(UiStatus.EMPTY)
            } else {
                mUiStatusController.changeUiStatus(UiStatus.CONTENT)
            }
            mAdapter.setDataList(mJobModelList)
            if (swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.finishRefresh()
            }

        })
        //获取已报名列表失败
        registerObserver(Constants.TAG_GET_EMPLOYEE_EMPLOYMENT_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        mAdapter = EmployWorkListAdapter(mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getEmployeeOfferList()
        }
    }

    override fun onFirstUserVisible() {
        mViewModel.getEmployeeOfferList()
    }
}