package com.jxqm.jiangdou.ui.employer.view

import android.content.Intent
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
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.model.JobDetailsWrapModel
import com.jxqm.jiangdou.ui.employer.adapter.JobPublishListAdapter
import com.jxqm.jiangdou.ui.employer.vm.WaitPublishViewModel
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.ui.order.view.OrderPaymentActivity
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.PromptDialog
import kotlinx.android.synthetic.main.fragment_wait_publish_layout.*

/**
 * 等待发布
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
        registerObserver(
            Constants.TAG_GET_WAIT_PUBLISH_JOB_LIST_SUCCESS,
            JobDetailsWrapModel::class.java
        ).observe(this,
            Observer {
                if (isRefresh) {
                    if (it.records.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                        if (it.records.size >= 10) {
                            swipeRefreshLayout.setEnableLoadMore(true)
                        }
                    }
                    mJobDetailList.clear()
                    mJobDetailList.addAll(it.records)
                    mJobPublishListAdapter.setDataList(mJobDetailList)
                    if (swipeRefreshLayout.isRefreshing)
                        swipeRefreshLayout.finishRefresh()
                    swipeRefreshLayout.resetNoMoreData()
                } else {
                    if (it.records.isEmpty()) {
                        swipeRefreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        swipeRefreshLayout.finishLoadMore()
                        mJobDetailList.addAll(it.records)
                        mJobPublishListAdapter.setDataList(mJobDetailList)
                    }
                }
            })
        //获取数据失败
        registerObserver(Constants.TAG_GET_WAIT_PUBLISH_JOB_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                if (mJobDetailList.isEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.finishRefresh()
                        swipeRefreshLayout.finishLoadMore()
                    }
                }
            })
        //刷新列表
        registerObserver(Constants.TAG_WAIT_PUBLISH_REFRESH_JOB_LIST, Boolean::class.java).observe(
            this,
            Observer {
                isRefresh = true
                mViewModel.getWaitPublishJob(isRefresh)
            })
        //取消发布
        registerObserver(Constants.TAG_DELETE_WAIT_PUBLISH_JOB_SUCCESS, String::class.java).observe(
            this,
            Observer {
                val iterator = mJobDetailList.iterator()
                while (iterator.hasNext()) {
                    val jobDetailsModel = iterator.next()
                    if (jobDetailsModel.id == it.toInt()) {
                        iterator.remove()
                    }
                }
                mJobPublishListAdapter.setDataList(mJobDetailList)
                if (mJobDetailList.isEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mJobPublishListAdapter = JobPublishListAdapter(mContext, 0)
        recyclerView.adapter = mJobPublishListAdapter
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.getWaitPublishJob(isRefresh)
        }
        swipeRefreshLayout.setEnableLoadMore(false)
        //支付押金
        mJobPublishListAdapter.paymentCallBack = {
            val intent = Intent(mContext, OrderPaymentActivity::class.java)
            intent.putExtra("JobId", it)
            mContext.startActivity(intent)
        }
        //取消发布
        mJobPublishListAdapter.cancelPublish = {
            PromptDialog.show(activity!!, "确认删除发布的职位吗？") {
                mViewModel.deletePublishJob(it)
            }
        }
        //兼职详情
        mJobPublishListAdapter.contentClickCallBack = {
            mContext.startActivity<JobDetailsActivity>(
                "JobId" to it.id.toString(),
                "Status" to JobDetailsActivity.STATUS_PAY_DEPOSIT
            )
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getWaitPublishJob(isRefresh)
        }

        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                isRefresh = true
                mViewModel.getWaitPublishJob(isRefresh)
            }
    }

    override fun onFirstUserVisible() {
        mViewModel.getWaitPublishJob(isRefresh)
    }
}