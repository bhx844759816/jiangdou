package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.EmployRecordWaitPayItem
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordWaitPayAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordWaitPayViewModel
import kotlinx.android.synthetic.main.fragment_employ_record_wait_pay.*

/**
 * 雇佣记录 - 待结算
 * Created By bhx On 2019/9/3 0003 09:00
 */
class EmployRecordWaitPayFragment : BaseMVVMFragment<EmployRecordWaitPayViewModel>() {
    private lateinit var mUiStatusController: UiStatusController
    private var isRefresh = true
    private var jobId: String? = null
    private val mEmployeeResumeModelList = mutableListOf<EmployeeResumeModel>()
    private lateinit var mAdapter: EmployRecordWaitPayAdapter
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_wait_pay
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD_WAIT_PAY


    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        jobId = arguments?.getString("jobId")
        //获取数据成功
        registerObserver(Constants.TAG_GET_REPORT_DUTY_LIST_SUCCESS, List::class.java).observe(this, Observer {
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
        registerObserver(Constants.TAG_GET_REPORT_DUTY_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordWaitPayAdapter(mContext)
        recyclerView.adapter = mAdapter
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            jobId?.let {
//                mViewModel.getReportDutyList(it, isRefresh)
            }
        }
        //上拉加载
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            jobId?.let {
//                mViewModel.getReportDutyList(it, isRefresh)
            }
        }
    }

    companion object {
        fun newInstance(jobId: String): EmployRecordWaitPayFragment {
            val fragment = EmployRecordWaitPayFragment()
            val bundle = Bundle()
            bundle.putString("jobId", jobId)
            fragment.arguments = bundle
            return fragment
        }
    }
}