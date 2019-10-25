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
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.model.JobDetailsWrapModel
import com.jxqm.jiangdou.ui.employer.adapter.JobPublishListAdapter
import com.jxqm.jiangdou.ui.employer.vm.EndSignUpViewModel
import com.jxqm.jiangdou.view.dialog.PromptDialog
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
        registerObserver(
            Constants.TAG_GET_END_SIGN_UP_JOB_LIST_SUCCESS,
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

        registerObserver(Constants.TAG_GET_END_SIGN_UP_JOB_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                if (swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.finishRefresh()
                    swipeRefreshLayout.finishLoadMore()
                }
                if (mJobDetailList.isEmpty()) {

                    mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
                }

            })
        //删除职位请求成功
        registerObserver(Constants.TAG_END_SIGN_UP_DELETE_JOB_SUCCESS, Boolean::class.java).observe(
            this,
            Observer {
                isRefresh = true
                mViewModel.getEndSignUpPublishJob(isRefresh)
            })

        registerObserver(Constants.TAG_END_SIGN_UP_JOB_LIST, Boolean::class.java).observe(this,
            Observer {
                isRefresh = true
                mViewModel.getEndSignUpPublishJob(isRefresh)
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mJobPublishListAdapter = JobPublishListAdapter(mContext, 3)
        recyclerView.adapter = mJobPublishListAdapter
        swipeRefreshLayout.setEnableLoadMore(false)
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.getEndSignUpPublishJob(isRefresh)
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getEndSignUpPublishJob(isRefresh)
        }
        mJobPublishListAdapter.cancelPublish = {
            PromptDialog.show(activity!!, "确认删除发布的工作吗？") {
                mViewModel.deletePublishJob(it)
            }
        }
        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                isRefresh = true
                mViewModel.getEndSignUpPublishJob(isRefresh)
            }
    }

    override fun onFirstUserVisible() {
        mViewModel.getEndSignUpPublishJob(isRefresh)
    }

    fun doSearch(searchKey: String) {
        mUiStatusController.changeUiStatus(UiStatus.LOADING)
        isRefresh = true
        mViewModel.getEndSignUpPublishJob(isRefresh)
    }

}