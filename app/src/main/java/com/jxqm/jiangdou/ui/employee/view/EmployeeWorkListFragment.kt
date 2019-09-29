package com.jxqm.jiangdou.ui.employee.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.base.BaseLazyFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployWorkItem
import com.jxqm.jiangdou.model.EmployeeWorkBaseItem
import com.jxqm.jiangdou.model.ReportDutyWorkItem
import com.jxqm.jiangdou.model.StatementsWorkItem
import com.jxqm.jiangdou.ui.employee.adapter.EmployWorkListAdapter
import com.jxqm.jiangdou.ui.employee.adapter.ReportDutyWorkListAdapter
import com.jxqm.jiangdou.ui.employee.adapter.SignUpWorkListAdapter
import com.jxqm.jiangdou.ui.employee.adapter.StatementsWorkAdapter
import kotlinx.android.synthetic.main.fragment_employee_work_list.*

/**
 * 工作台-雇员-列表界面
 * Created by Administrator on 2019/8/31.
 */
class EmployeeWorkListFragment : BaseLazyFragment() {
    private var mItemList = arrayListOf<EmployeeWorkBaseItem>()
    private var mEmployWorkItemList = arrayListOf<EmployWorkItem>()
    private var mReportDutyWorkItemList = arrayListOf<ReportDutyWorkItem>()
    private var mStatementsWorkItemList = arrayListOf<StatementsWorkItem>()
    private lateinit var mSignUpAdapter: SignUpWorkListAdapter
    private lateinit var mEmployWorkAdapter: EmployWorkListAdapter
    private lateinit var mReportDutyWorkAdapter: ReportDutyWorkListAdapter
    private lateinit var mStatementsWorkAdapter: StatementsWorkAdapter
    private var mType = 0

    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        when (mType) {
            0 -> {//已报名
                mSignUpAdapter = SignUpWorkListAdapter(mContext)
//                mSignUpAdapter.setDataList(mItemList)
                recyclerView.adapter = mSignUpAdapter
            }
            1 -> {//已录用
                mEmployWorkAdapter = EmployWorkListAdapter(mContext)
                mEmployWorkAdapter.setDataList(mEmployWorkItemList)
                recyclerView.adapter = mEmployWorkAdapter
            }
            2 -> {//已到岗
                mReportDutyWorkAdapter = ReportDutyWorkListAdapter(mContext)
                mReportDutyWorkAdapter.setDataList(mReportDutyWorkItemList)
                recyclerView.adapter = mReportDutyWorkAdapter
            }
            3 -> {//已结算
                mStatementsWorkAdapter = StatementsWorkAdapter(mContext)
                mStatementsWorkAdapter.setDataList(mStatementsWorkItemList)
                recyclerView.adapter = mStatementsWorkAdapter
            }
        }


    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mType = arguments?.getInt("type")!!
        mItemList.add(EmployeeWorkBaseItem(0))
        mItemList.add(EmployeeWorkBaseItem(-1))
        mItemList.add(EmployeeWorkBaseItem(1))


        mEmployWorkItemList.add(EmployWorkItem(0, isNormal = true, isAccept = false, isTimeOut = false))
        mEmployWorkItemList.add(EmployWorkItem(0, isNormal = false, isAccept = true, isTimeOut = false))
        mEmployWorkItemList.add(EmployWorkItem(-1, isNormal = false, isAccept = false, isTimeOut = false))
        mEmployWorkItemList.add(EmployWorkItem(0, isNormal = false, isAccept = false, isTimeOut = false))
        mEmployWorkItemList.add(EmployWorkItem(0, isNormal = false, isAccept = false, isTimeOut = true))

        mReportDutyWorkItemList.add(ReportDutyWorkItem(0))
        mReportDutyWorkItemList.add(ReportDutyWorkItem(1))
        mReportDutyWorkItemList.add(ReportDutyWorkItem(2))

        mStatementsWorkItemList.add(StatementsWorkItem(0,true, isAccept = false))
        mStatementsWorkItemList.add(StatementsWorkItem(0,false, isAccept = false))
        mStatementsWorkItemList.add(StatementsWorkItem(0,false, isAccept = true))

    }

    companion object {
        fun newInstance(type: Int): EmployeeWorkListFragment {
            val fragment = EmployeeWorkListFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}