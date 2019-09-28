package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.EmployRecordEmploymentItem
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordEmploymentAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordEmploymentViewModel
import kotlinx.android.synthetic.main.fragment_employ_record_employment.*

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
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordEmploymentAdapter(mContext)
        recyclerView.adapter = mAdapter
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
            mUiStatusController.changeUiStatus(UiStatus.LOADING)
            isRefresh = true
            getData()
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        jobId = arguments?.getString("jobId")
        //获取数据成功
        registerObserver(Constants.TAG_GET_EMPLOYEE_ACCEPT_LIST_SUCCESS, List::class.java).observe(this, Observer {
            val list = it as List<EmployeeResumeModel>
            if (isRefresh) {
                if (list.isNullOrEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                } else {
                    mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                }
                mEmployeeResumeModelList.clear()
                mEmployeeResumeModelList.addAll(list)
                mAdapter.setDataList(mEmployeeResumeModelList)
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.finishRefresh()
                }
            } else {
                swipeRefreshLayout.finishLoadMore()
                if (list.isEmpty()) {
                    swipeRefreshLayout.setNoMoreData(true)
                } else {
                    mEmployeeResumeModelList.addAll(list)
                    mAdapter.setDataList(mEmployeeResumeModelList)
                }
            }

        })
        //获取数据失败
        registerObserver(Constants.TAG_GET_EMPLOYEE_ACCEPT_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
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