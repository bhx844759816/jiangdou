package com.jxqm.jiangdou.ui.employer.view

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
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordEmploymentAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordEmploymentViewModel
import kotlinx.android.synthetic.main.fragment_employ_record_employment.*
import kotlinx.android.synthetic.main.fragment_employ_record_employment.recyclerView
import kotlinx.android.synthetic.main.fragment_employ_record_employment.swipeRefreshLayout

/**
 * 雇佣记录 - 已录用
 * Created By bhx On 2019/9/3 0003 08:58
 */
class EmployRecordEmploymentFragment : BaseMVVMFragment<EmployRecordEmploymentViewModel>() {
    private lateinit var mUiStatusController: UiStatusController
    private var isRefresh = true
    private var jobId: String? = null
    private var mStatus = 0 //状态
    private lateinit var mAdapter: EmployRecordEmploymentAdapter
    private val mEmployeeResumeModelList = mutableListOf<EmployeeResumeModel>()
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_employment
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordEmploymentAdapter(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setEnableLoadMore(false)
        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                isRefresh = true
                getData()
            }
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            getData()
        }
        //上拉加载
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            getData()
        }
        //RadioGroup选择切换
        rgSelectState.setOnCheckedChangeListener { _, id ->
            mStatus = when (id) {
                R.id.rbInvite -> {
                    0
                }
                R.id.rbAccept -> {
                    1
                }
                R.id.rbRefuse -> {
                    2
                }
                R.id.rbNoReply -> {
                    3
                }
                else -> 0
            }
            mAdapter.status = mStatus
            mUiStatusController.changeUiStatus(UiStatus.LOADING)
            isRefresh = true
            getData()
        }
        //重发
        mAdapter.mRepeatOfferCallBack = {
            mViewModel.repeatOffer(it)
        }
        //撤回
        mAdapter.mWithdrawOfferCallBack = {
            mViewModel.withdrawOffer(it)
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        jobId = arguments?.getString("jobId")
        //获取数据成功
        registerObserver(Constants.TAG_GET_EMPLOYEE_ACCEPT_LIST_SUCCESS, List::class.java).observe(
            this,
            Observer {
                val list = it as List<EmployeeResumeModel>
                if (isRefresh) {
                    if (list.isNullOrEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                        if (list.size >= 10) {
                            swipeRefreshLayout.setEnableLoadMore(true)
                        } else {
                            swipeRefreshLayout.setEnableLoadMore(false)
                        }
                    }
                    mEmployeeResumeModelList.clear()
                    mEmployeeResumeModelList.addAll(list)
                    mAdapter.setDataList(mEmployeeResumeModelList)
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.finishRefresh()
                    }
                    swipeRefreshLayout.resetNoMoreData()
                } else {
                    if (list.isEmpty()) {
                        swipeRefreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        swipeRefreshLayout.finishLoadMore()
                        mEmployeeResumeModelList.addAll(list)
                        mAdapter.setDataList(mEmployeeResumeModelList)
                    }
                }

            })
        //获取数据失败
        registerObserver(Constants.TAG_GET_EMPLOYEE_ACCEPT_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                if(mEmployeeResumeModelList.isEmpty()){
                    mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
                    if (swipeRefreshLayout.isRefreshing){
                        swipeRefreshLayout.finishRefresh()
                        swipeRefreshLayout.finishLoadMore()
                    }
                }
            })
        //撤回录用或者重发offer的完成时刷新列表数据
        registerObserver(
            Constants.TAG_EMPLOY_RECORD_REPEAT_WITHDRAW_FINISH,
            Boolean::class.java
        ).observe(this,
            Observer {
                isRefresh = true
                getData()
            })
    }

    override fun onFirstUserVisible() {
        getData()
    }

    private fun getData() {
        jobId?.let {
            mViewModel.getEmployListByStatus(it, isRefresh, mStatus)
        }
    }

    companion object {
        fun newInstance(jobId: String): EmployRecordEmploymentFragment {
            val fragment = EmployRecordEmploymentFragment()
            val bundle = Bundle()
            bundle.putString("jobId", jobId)
            fragment.arguments = bundle
            return fragment
        }
    }
}