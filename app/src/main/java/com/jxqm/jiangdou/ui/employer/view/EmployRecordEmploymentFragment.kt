package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.EmployRecordEmploymentItem
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordEmploymentAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordEmploymentViewModel
import kotlinx.android.synthetic.main.fragment_employ_record_employment.*

/**
 * 雇佣记录 - 已录用
 * Created By bhx On 2019/9/3 0003 08:58
 */
class EmployRecordEmploymentFragment : BaseMVVMFragment<EmployRecordEmploymentViewModel>() {
    private val mEmployRecordEmploymentItems = arrayListOf<EmployRecordEmploymentItem>()
    private lateinit var mUiStatusController: UiStatusController
    private var isRefresh = true
    private var jobId: String? = null
    private lateinit var mAdapter: EmployRecordEmploymentAdapter
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_employment
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        mEmployRecordEmploymentItems.add(EmployRecordEmploymentItem())
        mEmployRecordEmploymentItems.add(EmployRecordEmploymentItem())
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordEmploymentAdapter(mContext)
        mAdapter.setDataList(mEmployRecordEmploymentItems)
        recyclerView.adapter = mAdapter
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            jobId?.let {
            }
        }
        //上拉加载
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            jobId?.let {
            }
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        jobId = arguments?.getString("jobId")
    }


    companion object {
        fun newInstance(jobId: String): EmployRecordEmploymentFragment {
            val fragment = EmployRecordEmploymentFragment()
            val bundle = Bundle()
            bundle.putString("jobId", jobId)
            fragment.arguments = bundle
            return fragment
        }
    }
}