package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.controller.IUiStatusController
import com.fengchen.uistatus.listener.OnCompatRetryListener
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.*
import com.jxqm.jiangdou.ui.employer.adapter.AdvertiseJobAdapter
import com.jxqm.jiangdou.ui.employer.adapter.EndSignUpAdapter
import com.jxqm.jiangdou.ui.employer.adapter.WaitExamineJobAdapter
import com.jxqm.jiangdou.ui.employer.adapter.WaitPublishJobAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployerJobListViewModel
import kotlinx.android.synthetic.main.fragment_employee_work_list.*

/**
 * Created by Administrator on 2019/9/2.
 */
class EmployerJobListFragment : BaseMVVMFragment<EmployerJobListViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOYER_JOB_LIST

    private var mWaitPublishJobItemList = arrayListOf<WaitPublishJobItem>()
    private var mWaitExamineJobItemList = arrayListOf<WaitExamineJobItem>()
    private var mAdvertiseJobItemList = arrayListOf<AdvertiseJobItem>()
    private var mEndSignUpItemList = arrayListOf<EndSignUpItem>()
    private lateinit var mWaitPublishJobAdapter: WaitPublishJobAdapter
    private lateinit var mWaitExamineJobAdapter: WaitExamineJobAdapter
    private lateinit var mAdvertiseJobAdapter: AdvertiseJobAdapter
    private lateinit var mEndSignUpAdapter: EndSignUpAdapter
    private lateinit var mUiStatusController: UiStatusController


    private var mType = 0
    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        when (mType) {
            0 -> {//等待发布
                mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
                mUiStatusController.onCompatRetryListener =
                    OnCompatRetryListener { p0, p1, p2, p3 ->
                        mUiStatusController.changeUiStatus(UiStatus.LOADING)
                    }
                mWaitPublishJobAdapter = WaitPublishJobAdapter(mContext)
                mWaitPublishJobAdapter.setDataList(mWaitPublishJobItemList)
                recyclerView.adapter = mWaitPublishJobAdapter
            }
            1 -> {//等待审核
                mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                mWaitExamineJobAdapter = WaitExamineJobAdapter(mContext)
                mWaitExamineJobAdapter.setDataList(mWaitExamineJobItemList)
                recyclerView.adapter = mWaitExamineJobAdapter
            }
            2 -> {//正在招聘
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                mAdvertiseJobAdapter = AdvertiseJobAdapter(mContext)
                mAdvertiseJobAdapter.setDataList(mAdvertiseJobItemList)
                recyclerView.adapter = mAdvertiseJobAdapter
            }
            3 -> {//截止报名
                mEndSignUpAdapter = EndSignUpAdapter(mContext)
                mEndSignUpAdapter.setDataList(mEndSignUpItemList)
                recyclerView.adapter = mEndSignUpAdapter
            }
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)

        mType = arguments?.getInt("type")!!
        mWaitPublishJobItemList.add(WaitPublishJobItem())
        mWaitPublishJobItemList.add(WaitPublishJobItem())
        mWaitPublishJobItemList.add(WaitPublishJobItem())
        //
        mWaitExamineJobItemList.add(WaitExamineJobItem())
        mWaitExamineJobItemList.add(WaitExamineJobItem())
        mWaitExamineJobItemList.add(WaitExamineJobItem())
        //
        mAdvertiseJobItemList.add(AdvertiseJobItem())
        mAdvertiseJobItemList.add(AdvertiseJobItem())
        mAdvertiseJobItemList.add(AdvertiseJobItem())
        //
        mEndSignUpItemList.add(EndSignUpItem(true))
        mEndSignUpItemList.add(EndSignUpItem(false))
        mEndSignUpItemList.add(EndSignUpItem(true))

    }

    companion object {
        fun newInstance(type: Int): EmployerJobListFragment {
            val fragment = EmployerJobListFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}