package com.jxqm.jiangdou.ui.job.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.LogUtils
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jxqm.jiangdou.MyApplication
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
    private var mScreenResult: String? = null
    private val mParamsMap = mutableMapOf<String, String>()
    private val mGson: Gson by lazy {
        Gson()
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_list

    override fun getEventKey(): Any = Constants.EVENT_KEY_SEARCH_JOB_LIST
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mSearchKey = arguments?.getString("SearchKey", "")
        mSearchKey?.let {
            mParamsMap["searchKey"] = it
        }
        registerObserver(Constants.TAG_GET_SEARCH_JOB_LIST_SUCCESS, List::class.java).observe(
            this,
            Observer {
                val list = it as List<JobDetailsModel>
                if (isRefresh) {
                    if (list.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                        if (list.size >= 10) {
                            swipeRefreshLayout.setEnableLoadMore(true)
                        }
                    }
                    mJobDetailList.clear()
                    mJobDetailList.addAll(list)
                    mAdapter.setDataList(mJobDetailList)
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.finishRefresh()
                    }
                } else {
                    swipeRefreshLayout.finishLoadMore()
                    if (list.isEmpty()) {
                        swipeRefreshLayout.setNoMoreData(true)
                    } else {
                        mJobDetailList.addAll(list)
                        mAdapter.setDataList(mJobDetailList)
                    }
                }
            })

        registerObserver(Constants.TAG_GET_SEARCH_JOB_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
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
        rlScreenJob.clickWithTrigger {
            startActivity<JobScreenActivity>()
        }
        flSortWhole.clickWithTrigger {
            rgJobParent.clearCheck()
            rgJobParent.check(R.id.rbSortWhole)
        }
        flSortPublishNew.clickWithTrigger {
            rgJobParent.clearCheck()
            rgJobParent.check(R.id.rbSortPublishNew)
        }
        flSortDistance.clickWithTrigger {
            rgJobParent.clearCheck()
            rgJobParent.check(R.id.rbSortDistance)
        }
        rgJobParent.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rbSortWhole -> {
                    showSortPopupWindow()
                }
                R.id.rbSortPublishNew -> {
                    mSortPopupWindow?.dismissPopup()
                    mParamsMap.remove("latitude")
                    mParamsMap.remove("longitude")
                    mParamsMap["jobSort"] = "time"
                    isRefresh = true
                    mViewModel.getSearchJobList(mParamsMap, isRefresh)
                }
                R.id.rbSortDistance -> {
                    mSortPopupWindow?.dismissPopup()
                    mParamsMap["jobSort"] = "instance"
                    mParamsMap["latitude"] =
                        MyApplication.instance().locationModel?.latitude.toString()
                    mParamsMap["longitude"] =
                        MyApplication.instance().locationModel?.longitude.toString()
                    isRefresh = true
                    mViewModel.getSearchJobList(mParamsMap, isRefresh)
                }
            }
        }
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mSearchKey?.let {
                mViewModel.getSearchJobList(mParamsMap, isRefresh)
            }
        }
        rlScreenJob.clickWithTrigger {
            val intent = Intent(mContext, JobScreenActivity::class.java)
            intent.putExtra("ScreenResult", mScreenResult)
            startActivityForResult(intent, REQUEST_CODE_JOB_SCREEN)
        }

    }

    private fun showSortPopupWindow() {
        if (mSortPopupWindow == null && activity != null) {
            mSortPopupWindow = JobListSortPopupWindow(activity!!)
            mSortPopupWindow!!.setCallBack { index, content ->
                rbSortWhole.text = content
                when (index) {
                    0 -> {
                        mParamsMap.remove("jobSort")
                    }
                    1 -> {
                        mParamsMap["jobSort"] = "salary"
                    }
                    2 -> {
                        mParamsMap["jobSort"] = "recruitNum"
                    }
                }
                mParamsMap.remove("latitude")
                mParamsMap.remove("longitude")
                isRefresh = true
                mViewModel.getSearchJobList(mParamsMap, isRefresh)
            }
        }
        mSortPopupWindow!!.showPopup(rgJobParent)
    }

    override fun onFirstUserVisible() {
        mSearchKey?.let {
            mViewModel.getSearchJobList(mParamsMap, isRefresh)
        }
    }

    fun startSearch(searchKey: String) {
        mSearchKey = searchKey
        mSearchKey?.let {
            isRefresh = true
            mParamsMap["searchKey"] = it
            mViewModel.getSearchJobList(mParamsMap, isRefresh)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_JOB_SCREEN) {
            mScreenResult = data?.getStringExtra("ScreenResult")
            LogUtils.i("mScreenResult=$mScreenResult")
            mScreenResult?.let {
                val params = mGson.fromJson<Map<String, String>>(
                    it,
                    object : TypeToken<Map<String, String>>() {
                    }.type
                )
                mParamsMap.remove("year")
                mParamsMap.remove("month")
                mParamsMap.remove("day")
                mParamsMap.remove("gender")
                mParamsMap.remove("times")
                mParamsMap.remove("date")
                mParamsMap.putAll(params)
                isRefresh = true
                mViewModel.getSearchJobList(mParamsMap, isRefresh)
            }
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

        const val REQUEST_CODE_JOB_SCREEN = 0x01
    }

}