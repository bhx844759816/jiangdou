package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordEmploymentItem
import com.jxqm.jiangdou.model.EmployRecordSignUpItem
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordEmploymentAdapter
import kotlinx.android.synthetic.main.fragment_employ_record_employment.*

/**
 * 雇佣记录 - 已录用
 * Created By bhx On 2019/9/3 0003 08:58
 */
class EmployRecordEmploymentFragment : BaseLazyFragment() {
    private val mEmployRecordEmploymentItems = arrayListOf<EmployRecordEmploymentItem>()
    private lateinit var mAdapter: EmployRecordEmploymentAdapter
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_employment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEmployRecordEmploymentItems.add(EmployRecordEmploymentItem())
        mEmployRecordEmploymentItems.add(EmployRecordEmploymentItem())
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordEmploymentAdapter(mContext)
        mAdapter.setDataList(mEmployRecordEmploymentItems)
        recyclerView.adapter = mAdapter
    }
}