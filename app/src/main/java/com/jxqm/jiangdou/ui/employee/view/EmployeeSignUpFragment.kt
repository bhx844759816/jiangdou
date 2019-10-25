package com.jxqm.jiangdou.ui.employee.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.event.LiveBus
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.LogUtils
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.controller.IUiStatusController
import com.fengchen.uistatus.listener.OnCompatRetryListener
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.JobEmployeeBaseModel
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
    private val mJobSingModelList = mutableListOf<JobEmployeeBaseModel>()

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //获取已报名列表成功
        registerObserver(Constants.TAG_GET_EMPLOYEE_SIGN_LIST_SUCCESS, List::class.java).observe(
            this,
            Observer {
                val list = it as List<JobEmployeeBaseModel>
                mJobSingModelList.clear()
                mJobSingModelList.addAll(list)
                if (mJobSingModelList.isEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                } else {
                    mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                }
                mAdapter.setDataList(mJobSingModelList)
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.finishRefresh()
                }

            })
        //获取已报名列表失败
        registerObserver(Constants.TAG_GET_EMPLOYEE_SIGN_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
            })

        registerObserver(Constants.TAG_CLEAR_EMPLOYEE_SIGN_CLOSE_LIST, Boolean::class.java).observe(
            this,
            Observer {
                mViewModel.getSignList()
            })

        registerObserver(
            Constants.TAG_MAIN_MY_LOGIN_SUCCESS,
            Boolean::class.java
        ).observe(
            this,
            Observer {
                LogUtils.i("loading 登录成功,重新获取数据")
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                mViewModel.getSignList()
            })
        //报名成功 - 跳转到工作台 - 刷新列表
        registerObserver(Constants.TAG_SING_UP_SUCCESS_REFRESH_LIST, Boolean::class.java).observe(
            this,
            Observer {
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                mViewModel.getSignList()
            })
    }

    override fun onFirstUserVisible() {
        mViewModel.getSignList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        mAdapter = SignUpWorkListAdapter(mContext)
        swipeRefreshLayout.setEnableLoadMore(false)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getSignList()
        }
        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                mViewModel.getSignList()
            }
        mAdapter.clearCloseJobCallBack = {
            mViewModel.clearCloseJob()
        }
    }

    /**
     *
     */
    fun doSearch(searchKey: String) {
        mUiStatusController.changeUiStatus(UiStatus.LOADING)
        mViewModel.getSignList()
    }
}