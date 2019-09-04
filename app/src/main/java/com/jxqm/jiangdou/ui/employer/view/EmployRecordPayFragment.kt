package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordPayItem
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordPayAdapter
import kotlinx.android.synthetic.main.fragment_employ_record_pay.*

/**
 * 雇佣记录 - 已结算
 * Created By bhx On 2019/9/3 0003 09:01
 */
class EmployRecordPayFragment : BaseLazyFragment() {
    private val mEmployRecordPayItems = arrayListOf<EmployRecordPayItem>()
    private lateinit var mAdapter: EmployRecordPayAdapter
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_pay

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = EmployRecordPayAdapter(mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        rgSelectState.check(R.id.rbInvite)
        showPayFinish()
        rgSelectState.setOnCheckedChangeListener { _, radioButtonId ->
            when (radioButtonId) {
                R.id.rbInvite -> {
                    LogUtils.i("rgSelectState select 出账")
                    showPayFinish()
                }
                R.id.rbAccept -> {
                    LogUtils.i("rgSelectState select 接受")
                    showPayWait()
                }
                R.id.rbRefuse -> {
                    LogUtils.i("rgSelectState select 拒绝")
                    showPayRefuse()
                }
            }
        }
    }

    private fun showPayFinish() {
        mEmployRecordPayItems.clear()
        mEmployRecordPayItems.add(EmployRecordPayItem(0))
        mEmployRecordPayItems.add(EmployRecordPayItem(0))
        mAdapter.updateDatas(mEmployRecordPayItems)
    }

    private fun showPayWait() {
        mEmployRecordPayItems.clear()
        mEmployRecordPayItems.add(EmployRecordPayItem(1))
        mEmployRecordPayItems.add(EmployRecordPayItem(1))
        mAdapter.updateDatas(mEmployRecordPayItems)
    }

    private fun showPayRefuse() {
        mEmployRecordPayItems.clear()
        mEmployRecordPayItems.add(EmployRecordPayItem(2))
        mEmployRecordPayItems.add(EmployRecordPayItem(2))
        mAdapter.updateDatas(mEmployRecordPayItems)
    }
}