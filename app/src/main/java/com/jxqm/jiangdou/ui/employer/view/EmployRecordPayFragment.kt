package com.jxqm.jiangdou.ui.employer.view

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.http.RxHelper
import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.LogUtils
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.listener.OnCompatRetryListener
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.EmployRecordPayItem
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordPayAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordPayViewModel
import com.jxqm.jiangdou.view.dialog.SettleDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_employ_record_pay.*

/**
 * 雇佣记录 - 已结算
 * Created By bhx On 2019/9/3 0003 09:01
 */
class EmployRecordPayFragment : BaseMVVMFragment<EmployRecordPayViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD_PAY
    private val mEmployeeResumeModelList = arrayListOf<EmployeeResumeModel>()
    private lateinit var mAdapter: EmployRecordPayAdapter
    private lateinit var mUiStatusController: UiStatusController
    private var isRefresh = true
    private var mStatus = 0 //状态
    private var jobId: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_pay

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordPayAdapter(mContext)
        recyclerView.adapter = mAdapter
        rgSelectState.check(R.id.rbInvite)
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
        rgSelectState.setOnCheckedChangeListener { _, radioButtonId ->
            mStatus = when (radioButtonId) {
                R.id.rbInvite -> {
                    0
                }
                R.id.rbAccept -> {
                    1
                }
                R.id.rbRefuse -> {
                    2
                }
                else -> {
                    0
                }
            }
            isRefresh = true
            mUiStatusController.changeUiStatus(UiStatus.LOADING)
            isRefresh = true
            getData()
        }
        mAdapter.repeatSettleCallBack = { amount, jobId ->
            //单结提示
            SettleDialog.show(activity!!, amount) {
                mViewModel.singleSettleWork(jobId, it)
            }
        }
        mAdapter.contactCallBack = {
            callPhone(it.tel)
        }
    }

    private fun getData() {
        jobId?.let {
            mViewModel.getSettleListByStatus(it, isRefresh, mStatus)
        }
    }

    override fun onFirstUserVisible() {
        getData()
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        jobId = arguments?.getString("jobId")
        //获取结算列表成功
        registerObserver(Constants.TAG_GET_SETTLE_FINISH_LIST_SUCCESS, List::class.java).observe(
            this,
            Observer {
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
        registerObserver(Constants.TAG_GET_SETTLE_FINISH_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.finishRefresh()
                    swipeRefreshLayout.finishLoadMore()
                }
                if (mEmployeeResumeModelList.isEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)

                }
            })
        registerObserver(Constants.TAG_REPEAT_SETTLE_FINISH, Boolean::class.java).observe(this,
            Observer {
                isRefresh = true
                getData()
            })
    }

    private fun callPhone(phone: String) {
        activity?.let {
            RxPermissions(it).request(Manifest.permission.CALL_PHONE)
                .compose(RxHelper.io_main())
                .subscribe { result ->
                    if (result) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_DIAL
                        intent.data = Uri.parse("tel:$phone")
                        mContext.startActivity(intent)
                    }
                }
        }
    }

    companion object {
        fun newInstance(jobId: String): EmployRecordPayFragment {
            val fragment = EmployRecordPayFragment()
            val bundle = Bundle()
            bundle.putString("jobId", jobId)
            fragment.arguments = bundle
            return fragment
        }
    }
}