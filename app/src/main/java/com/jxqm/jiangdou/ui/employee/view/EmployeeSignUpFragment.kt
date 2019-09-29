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
import com.jxqm.jiangdou.model.JobSignModel
import com.jxqm.jiangdou.model.JobSignModelBase
import com.jxqm.jiangdou.ui.employee.adapter.SignUpWorkListAdapter
import com.jxqm.jiangdou.ui.employee.vm.EmployeeSignUpViewModel
import kotlinx.android.synthetic.main.fragment_end_sign_up_publish_layout.*

/**
 * 雇员界面 - 已报名
 * Created By bhx On 2019/9/24 0024 15:53
 */
class EmployeeSignUpFragment : BaseMVVMFragment<EmployeeSignUpViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOYEE_SIGN_UP
    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mAdapter: SignUpWorkListAdapter
    private val mJobSingModelList = mutableListOf<JobSignModelBase>()

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //获取已报名列表成功
        registerObserver(Constants.TAG_GET_EMPLOYEE_SIGN_LIST_SUCCESS, List::class.java).observe(this, Observer {
            val list = it as List<JobSignModelBase>
            mJobSingModelList.clear()
            mJobSingModelList.addAll(list)
            if (mJobSingModelList.isEmpty()) {
                mUiStatusController.changeUiStatus(UiStatus.EMPTY)
            } else {
                mUiStatusController.changeUiStatus(UiStatus.CONTENT)
            }
            mAdapter.setDataList(mJobSingModelList)
        })
        //获取已报名列表失败
        registerObserver(Constants.TAG_GET_EMPLOYEE_SIGN_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })

    }

    override fun onFirstUserVisible() {
        mViewModel.getSignList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        mAdapter = SignUpWorkListAdapter(mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter

    }
}