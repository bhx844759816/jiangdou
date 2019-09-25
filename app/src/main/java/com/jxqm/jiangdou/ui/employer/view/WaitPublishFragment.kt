package com.jxqm.jiangdou.ui.employer.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.model.JobDetailsWrapModel
import com.jxqm.jiangdou.ui.employer.adapter.JobPublishListAdapter
import com.jxqm.jiangdou.ui.employer.vm.WaitPublishViewModel
import com.jxqm.jiangdou.ui.order.view.OrderPaymentActivity
import kotlinx.android.synthetic.main.fragment_employee_work_list.*
import kotlinx.android.synthetic.main.fragment_wait_publish_layout.*
import kotlinx.android.synthetic.main.fragment_wait_publish_layout.recyclerView
import kotlinx.android.synthetic.main.fragment_wait_publish_layout.swipeRefreshLayout

/**
 * Created by Administrator on 2019/9/22.
 */
class WaitPublishFragment : BaseMVVMFragment<WaitPublishViewModel>() {
    private var isRefresh: Boolean = true
    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mJobPublishListAdapter: JobPublishListAdapter // 工作列表
    private var mJobDetailList = mutableListOf<JobDetailsModel>()

    override fun getLayoutId(): Int = R.layout.fragment_wait_publish_layout

    override fun getEventKey(): Any = Constants.EVENT_KEY_WAIT_PUBLISH_JOB

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //获取数据成功
        registerObserver(Constants.TAG_GET_WAIT_PUBLISH_JOB_LIST_SUCCESS, JobDetailsWrapModel::class.java).observe(this,
            Observer {
                if (isRefresh) {
                    if (it.records.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                    }
                    mJobDetailList.clear()
                    mJobDetailList.addAll(it.records)
                    mJobPublishListAdapter.setDataList(mJobDetailList)
                    if (swipeRefreshLayout.isRefreshing)
                        swipeRefreshLayout.finishRefresh()
                } else {
                    swipeRefreshLayout.finishLoadMore()
                    if (it.records.isEmpty()) {
                        swipeRefreshLayout.setNoMoreData(true)
                    } else {
                        mJobDetailList.addAll(it.records)
                        mJobPublishListAdapter.setDataList(mJobDetailList)
                    }
                }
            })

        registerObserver(Constants.TAG_GET_WAIT_PUBLISH_JOB_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mJobPublishListAdapter = JobPublishListAdapter(mContext, 0)
        recyclerView.adapter = mJobPublishListAdapter
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.getWaitPublishJob(isRefresh)
        }
        //支付押金
        mJobPublishListAdapter.paymentCallBack = {
            val intent = Intent(mContext, OrderPaymentActivity::class.java)
            intent.putExtra("JobId", it)
            mContext.startActivity(intent)
        }
        //取消发布
        mJobPublishListAdapter.cancelPublish = {

        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getWaitPublishJob(isRefresh)
        }
    }

    override fun onFirstUserVisible() {
        mViewModel.getWaitPublishJob(isRefresh)
    }
}