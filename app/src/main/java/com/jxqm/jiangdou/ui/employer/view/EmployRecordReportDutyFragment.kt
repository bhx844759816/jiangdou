package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.EmployRecordReportDutyItem
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordReportDutyAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordReportDutyViewModel
import com.jxqm.jiangdou.utils.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_employ_record_report_duty.*

/**
 * 雇佣记录 - 到岗
 * Created By bhx On 2019/9/3 0003 08:59
 */
class EmployRecordReportDutyFragment : BaseMVVMFragment<EmployRecordReportDutyViewModel>() {
    private val mEmployRecordReportDutyItems = arrayListOf<EmployRecordReportDutyItem>()
    private lateinit var mAdapter: EmployRecordReportDutyAdapter

    override fun getLayoutId(): Int = R.layout.fragment_employ_record_report_duty
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD_REPORT_DUTY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEmployRecordReportDutyItems.add(EmployRecordReportDutyItem())
        mEmployRecordReportDutyItems.add(EmployRecordReportDutyItem())
        mAdapter = EmployRecordReportDutyAdapter(mContext)
        mAdapter.setDataList(mEmployRecordReportDutyItems)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.addItemDecoration(SpaceItemDecoration(DensityUtil.dip2px(mContext, 10f)))
        recyclerView.adapter = mAdapter
    }


}