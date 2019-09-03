package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordReportDutyItem
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordReportDutyAdapter
import kotlinx.android.synthetic.main.fragment_employ_record_report_duty.*

/**
 * 雇佣记录 - 到岗
 * Created By bhx On 2019/9/3 0003 08:59
 */
class EmployRecordReportDutyFragment : BaseLazyFragment() {
    private val mEmployRecordReportDutyItems = arrayListOf<EmployRecordReportDutyItem>()
    private lateinit var mAdapter: EmployRecordReportDutyAdapter

    override fun getLayoutId(): Int = R.layout.fragment_employ_record_report_duty

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEmployRecordReportDutyItems.add(EmployRecordReportDutyItem())
        mEmployRecordReportDutyItems.add(EmployRecordReportDutyItem())
        mAdapter = EmployRecordReportDutyAdapter(mContext)
        mAdapter.setDataList(mEmployRecordReportDutyItems)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
    }

}