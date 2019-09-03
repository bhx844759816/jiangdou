package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordWaitPayItem
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordWaitPayAdapter
import kotlinx.android.synthetic.main.fragment_employ_record_wait_pay.*

/**
 * 雇佣记录 - 待结算
 * Created By bhx On 2019/9/3 0003 09:00
 */
class EmployRecordWaitPayFragment : BaseLazyFragment() {
    private val mEmployRecordWaitPayItems = arrayListOf<EmployRecordWaitPayItem>()
    private lateinit var mAdapter: EmployRecordWaitPayAdapter
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_wait_pay

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEmployRecordWaitPayItems.add(EmployRecordWaitPayItem())
        mEmployRecordWaitPayItems.add(EmployRecordWaitPayItem())
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordWaitPayAdapter(mContext)
        mAdapter.setDataList(mEmployRecordWaitPayItems)
        recyclerView.adapter = mAdapter
    }

}