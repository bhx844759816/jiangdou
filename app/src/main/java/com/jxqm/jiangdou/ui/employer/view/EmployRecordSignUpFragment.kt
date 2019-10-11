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
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordSignUpAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordSignUpViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.fragment_employ_record_sign_up.*

/**
 * 雇佣记录 - 报名的Fragment
 * Created By bhx On 2019/9/3 0003 08:57
 */
class EmployRecordSignUpFragment : BaseMVVMFragment<EmployRecordSignUpViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD_SIGN_UP
    private val mEmployRecordSignUpItems = arrayListOf<EmployeeResumeModel>()
    private lateinit var mAdapter: EmployRecordSignUpAdapter
    private lateinit var mUiStatusController: UiStatusController
    private val mIdArrays = arrayListOf<Long>()
    private var isRefresh = true
    private var jobId: String? = null

    override fun getLayoutId(): Int = R.layout.fragment_employ_record_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        mAdapter = EmployRecordSignUpAdapter(mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setEnableLoadMore(false)
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            jobId?.let {
                mViewModel.getSignUpEmployee(it, isRefresh)
            }
        }
        //上拉加载
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            jobId?.let {
                mViewModel.getSignUpEmployee(it, isRefresh)
            }
        }
        flAllSelect.clickWithTrigger {
            cbAllSelect.isChecked = !cbAllSelect.isChecked
            val list = mEmployRecordSignUpItems.map {
                it.isChecked = cbAllSelect.isChecked
                it
            }
            mEmployRecordSignUpItems.clear()
            mEmployRecordSignUpItems.addAll(list)
            mAdapter.setDataList(mEmployRecordSignUpItems)
        }
        //录用
        tvAgreeWork.clickWithTrigger {
            mIdArrays.clear()
            //统一录用
            mEmployRecordSignUpItems.forEach {
                if (it.isChecked) {
                    mIdArrays.add(it.id)
                }
            }
            mViewModel.acceptResume(mIdArrays)
        }
        //驳回
        tvNotAgreeWork.clickWithTrigger {
            mIdArrays.clear()
            //统一录用
            mEmployRecordSignUpItems.forEach {
                if (it.isChecked) {
                    mIdArrays.add(it.id)
                }
            }
            mViewModel.regectedResume(mIdArrays)
        }
        //单独录用
        mAdapter.acceptCallBack = {
            mIdArrays.clear()
            mIdArrays.add(it.id)
            mViewModel.acceptResume(mIdArrays)
        }
        //
        mAdapter.checkCallBack = { position, isChecked ->
            val employeeResumeModel = mEmployRecordSignUpItems[position]
            employeeResumeModel.isChecked = isChecked
            //判断是否全选
            var isAllChecked = true
            mEmployRecordSignUpItems.forEach {
                if (!it.isChecked) {
                    isAllChecked = false
                }
            }
            cbAllSelect.isChecked = isAllChecked
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        jobId = arguments?.getString("jobId")
        //获取报名列表数据成功
        registerObserver(Constants.TAG_GET_EMPLOYEE_LIST_SUCCESS, List::class.java).observe(this, Observer {
            val list = it as List<EmployeeResumeModel>
            if (isRefresh) {
                if (list.isEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                } else {
                    mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                    if (list.size >= 10) {
                        swipeRefreshLayout.setEnableLoadMore(true)
                    }
                }
                mEmployRecordSignUpItems.clear()
                mEmployRecordSignUpItems.addAll(list)
                mAdapter.setDataList(mEmployRecordSignUpItems)
                if (swipeRefreshLayout.isRefreshing)
                    swipeRefreshLayout.finishRefresh()
            } else {
                swipeRefreshLayout.finishLoadMore()
                if (list.isEmpty()) {
                    swipeRefreshLayout.setNoMoreData(true)
                } else {
                    mEmployRecordSignUpItems.addAll(list)
                    mAdapter.addDatas(list)
                }
            }

        })
        //获取报名列表数据失败
        registerObserver(Constants.TAG_GET_EMPLOYEE_LIST_ERROR, String::class.java).observe(this, Observer {
            mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
        })
        //录用成功
        registerObserver(Constants.TAG_ACCEPT_OR_REFUSED_RESUME_SUCCESS, Boolean::class.java).observe(this, Observer {
            val iterator = mEmployRecordSignUpItems.iterator()
            while (iterator.hasNext()) {
                if (mIdArrays.contains(iterator.next().id)) {
                    iterator.remove()
                }
            }
            mAdapter.setDataList(mEmployRecordSignUpItems)
        })
        //
    }

    override fun onFirstUserVisible() {
        jobId?.let {
            mViewModel.getSignUpEmployee(it, isRefresh)
        }
    }

    companion object {
        fun newInstance(jobId: String): EmployRecordSignUpFragment {
            val fragment = EmployRecordSignUpFragment()
            val bundle = Bundle()
            bundle.putString("jobId", jobId)
            fragment.arguments = bundle
            return fragment
        }
    }

}