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
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.model.JobDetailsWrapModel
import com.jxqm.jiangdou.ui.employer.adapter.JobPublishListAdapter
import com.jxqm.jiangdou.ui.employer.vm.EndSignUpViewModel
import kotlinx.android.synthetic.main.fragment_end_sign_up_publish_layout.*

/**
 * 截止报名
 * Created by Administrator on 2019/9/22.
 */
class EndSignUpFragment : BaseMVVMFragment<EndSignUpViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_end_sign_up_publish_layout
    override fun getEventKey(): Any = Constants.EVENT_KEY_END_SIGN_UP

    private var isRefresh: Boolean = true
    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mJobPublishListAdapter: JobPublishListAdapter // 工作列表
    private var mJobDetailList = mutableListOf<JobDetailsModel>()
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //获取数据成功
        registerObserver(Constants.TAG_GET_END_SIGN_UP_JOB_LIST_SUCCESS, JobDetailsWrapModel::class.java).observe(this,
            Observer {
                if (isRefresh) {
                    if (it.records.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                    }
                    mJobDetailList.clear()
                    mJobDetailList.addAll(it.records)
                    mJobPublishListAdapter.notifyDataSetChanged()
                    if (swipeRefreshLayout.isRefreshing)
                        swipeRefreshLayout.finishRefresh()
                } else {
                    swipeRefreshLayout.finishLoadMore()
                    if (it.records.isEmpty()) {
                        swipeRefreshLayout.setNoMoreData(true)
                    } else {
                        mJobDetailList.addAll(it.records)
                        mJobPublishListAdapter.notifyDataSetChanged()

                    }
                }
            })

        registerObserver(Constants.TAG_GET_END_SIGN_UP_JOB_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mJobPublishListAdapter = JobPublishListAdapter(mContext, 3)
        recyclerView.adapter = mJobPublishListAdapter
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.getEndSignUpPublishJob(isRefresh)
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getEndSignUpPublishJob(isRefresh)
        }
    }

    override fun onFirstUserVisible() {
        mViewModel.getEndSignUpPublishJob(isRefresh)
    }

}