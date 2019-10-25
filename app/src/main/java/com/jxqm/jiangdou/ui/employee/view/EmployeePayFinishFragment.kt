package com.jxqm.jiangdou.ui.employee.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.listener.OnCompatRetryListener
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.JobEmployeeModel
import com.jxqm.jiangdou.ui.employee.adapter.StatementsWorkAdapter
import com.jxqm.jiangdou.ui.employee.vm.EmployeePayFinishViewModel
import kotlinx.android.synthetic.main.fragment_employee_work_list.*

/**
 * 雇员 - 已结算
 * Created By bhx On 2019/9/24 0024 16:00
 */
class EmployeePayFinishFragment : BaseMVVMFragment<EmployeePayFinishViewModel>() {
    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mAdapter: StatementsWorkAdapter
    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOYEE_PAY_FINISH
    private val mJobModelList = mutableListOf<JobEmployeeModel>()

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //获取数据成功
        registerObserver(
            Constants.TAG_GET_EMPLOYEE_PAY_FINISH_LIST_SUCCESS,
            List::class.java
        ).observe(this, Observer {
            val list = it as List<JobEmployeeModel>
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
        //获取数据失败
        registerObserver(
            Constants.TAG_GET_EMPLOYEE_PAY_FINISH_LIST_ERROR,
            String::class.java
        ).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })
        //拒绝和接受
        registerObserver(Constants.TAG_REFUSE_ACCEPT_SETTLE_FINISH, Boolean::class.java).observe(
            this,
            Observer {
                mViewModel.getEmployeeSettleList()
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        mAdapter = StatementsWorkAdapter(mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setEnableLoadMore(false)
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getEmployeeSettleList()
        }
        //接受
        mAdapter.mAcceptCallBack = {
            mViewModel.acceptSettle(it)
        }
        //拒绝
        mAdapter.mRefuseCallBack = {
            mViewModel.refuseSettle(it)
        }
        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                mViewModel.getEmployeeSettleList()
            }
    }

    override fun onFirstUserVisible() {
        mViewModel.getEmployeeSettleList()
    }

    fun doSearch(searchKey: String) {
        mUiStatusController.changeUiStatus(UiStatus.LOADING)
        mViewModel.getEmployeeSettleList()
    }

}