package com.jxqm.jiangdou.ui.employer.view

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.http.RxHelper
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.LogUtils
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.listener.OnCompatRetryListener
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordWaitPayAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordWaitPayViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.PromptDialog
import com.jxqm.jiangdou.view.dialog.SettleDialog
import com.tbruyelle.rxpermissions2.RxPermissions
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
        registerObserver(Constants.TAG_GET_WAIT_PAY_LIST_SUCCESS, List::class.java).observe(this, Observer {
            val list = it as List<EmployeeResumeModel>
            if (isRefresh) {
                if (list.isNullOrEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                } else {
                    mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                    if (list.size >= 10) {
                        swipeRefreshLayout.setEnableLoadMore(true)
                    }
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
        registerObserver(Constants.TAG_GET_WAIT_PAY_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })
        //结算完成
        registerObserver(Constants.TAG_WAIT_PAY_SETTLE_SUCCESS, Boolean::class.java).observe(this, Observer {
            isRefresh = true
            jobId?.let {
                mViewModel.getWaitPayList(it, isRefresh)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordWaitPayAdapter(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setEnableLoadMore(false)
        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                isRefresh = true
                jobId?.let {
                    mViewModel.getWaitPayList(it, isRefresh)
                }
            }
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            jobId?.let {
                mViewModel.getWaitPayList(it, isRefresh)
            }
        }
        //上拉加载
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            jobId?.let {
                mViewModel.getWaitPayList(it, isRefresh)
            }
        }
        //联系
        mAdapter.mContactCallBack = {
            callPhone(it)
        }
        //单结
        mAdapter.mSingleSettle = { amount, jobId ->
            //单结提示
            SettleDialog.show(activity!!, amount) {
                LogUtils.i("singleSettleWork=$it")
                mViewModel.singleSettleWork(jobId, it)
            }
        }
        cbAllSelect.setOnCheckedChangeListener { _, checked ->
            tvMergerSettle.isEnabled = checked
        }
        flAllSelect.clickWithTrigger {
            cbAllSelect.isChecked = !cbAllSelect.isChecked
            val list = mEmployeeResumeModelList.map {
                it.isChecked = cbAllSelect.isChecked
                it
            }
            mEmployeeResumeModelList.clear()
            mEmployeeResumeModelList.addAll(list)
            mAdapter.setDataList(mEmployeeResumeModelList)
        }
        //
        mAdapter.checkCallBack = { position, isChecked ->
            val employeeResumeModel = mEmployeeResumeModelList[position]
            employeeResumeModel.isChecked = isChecked
            //判断是否全选
            var isAllChecked = true
            mEmployeeResumeModelList.forEach {
                if (!it.isChecked) {
                    isAllChecked = false
                }
            }
            cbAllSelect.isChecked = isAllChecked
        }
        //合并结算
        tvMergerSettle.clickWithTrigger {
            var mAmountCount = 0
            val mResumeId = mEmployeeResumeModelList.filter {
                it.isChecked
            }.map {
                mAmountCount += it.amount.toInt()
                it.id
            }
            //合并结算提示
            PromptDialog.show(activity!!, "确认结算薪资$mAmountCount 豆币？") {
                mViewModel.mergeSettleWork(mResumeId)
            }
        }
    }

    override fun onFirstUserVisible() {
        jobId?.let {
            mViewModel.getWaitPayList(it, isRefresh)
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

    /**
     * 打电话
     */
    private fun callPhone(tel: String) {
        activity?.let {
            RxPermissions(it).request(Manifest.permission.CALL_PHONE)
                .compose(RxHelper.io_main())
                .subscribe { result ->
                    if (result) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_DIAL
                        intent.data = Uri.parse("tel:$tel")
                        mContext.startActivity(intent)
                    }
                }
        }
    }
}