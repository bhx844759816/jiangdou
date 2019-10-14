package com.jxqm.jiangdou.ui.job.view

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
import com.jxqm.jiangdou.ui.job.adapter.JobListAdapter
import com.jxqm.jiangdou.ui.job.vm.JobListViewModel
import com.jxqm.jiangdou.ui.job.widget.JobListSortPopupWindow
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_job_list.*

/**
 * Created by Administrator on 2019/8/17.
 */
class JobListFragment : BaseMVVMFragment<JobListViewModel>() {

    private var mSortPopupWindow: JobListSortPopupWindow? = null
    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mAdapter: JobListAdapter
    private var mJobDetailList = mutableListOf<JobDetailsModel>()
    private var mSearchKey: String? = null
    private var isRefresh = true

    override fun getLayoutId(): Int = R.layout.fragment_job_list

    override fun getEventKey(): Any = Constants.EVENT_KEY_SEARCH_JOB_LIST
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mSearchKey = arguments?.getString("SearchKey", "")
        registerObserver(Constants.TAG_GET_SEARCH_JOB_LIST_SUCCESS, List::class.java).observe(this, Observer {
            val list = it as List<JobDetailsModel>
           if(isRefresh){
               if (list.isEmpty()) {
                   mUiStatusController.changeUiStatus(UiStatus.EMPTY)
               } else {
                   mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                   if(list.size >= 10){
                       swipeRefreshLayout.setEnableLoadMore(true)
                   }
               }
               mJobDetailList.clear()
               mJobDetailList.addAll(list)
               mAdapter.setDataList(mJobDetailList)
               if (swipeRefreshLayout.isRefreshing) {
                   swipeRefreshLayout.finishRefresh()
               }
           }else{

           }
        })

        registerObserver(Constants.TAG_GET_SEARCH_JOB_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        mAdapter = JobListAdapter(mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setEnableLoadMore(false)
        rgJobSortParent.check(R.id.rbSortDistance)
        rlScreenJob.clickWithTrigger {
            startActivity<JobScreenActivity>()
        }
        rgJobSortParent.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rbSortWhole -> {
                    showSortPopupWindow()
                }
                R.id.rbSortPublishNew -> {
                    mSortPopupWindow?.dismissPopup()
                }
                R.id.rbSortDistance -> {
                    mSortPopupWindow?.dismissPopup()
                }
            }
        }
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mSearchKey?.let {
                mViewModel.getSearchJobList(it, isRefresh)
            }
        }


    }

    private fun showSortPopupWindow() {
        if (mSortPopupWindow == null && activity != null) {
            mSortPopupWindow = JobListSortPopupWindow(activity!!)
            mSortPopupWindow!!.setCallBack { _, content ->
                rbSortWhole.text = content
            }
        }
        mSortPopupWindow!!.showPopup(rgJobSortParent)
    }

    override fun onFirstUserVisible() {
        mSearchKey?.let {
            mViewModel.getSearchJobList(it, isRefresh)
        }
    }

    fun startSearch(searchKey: String) {
        mSearchKey = searchKey
        mSearchKey?.let {
            mViewModel.getSearchJobList(it, isRefresh)
        }
    }


    companion object {
        fun newInstance(searchKey: String): JobListFragment {
            val fragment = JobListFragment()
            val bundle = Bundle()
            bundle.putString("SearchKey", searchKey)
            fragment.arguments = bundle
            return fragment
        }
    }
}