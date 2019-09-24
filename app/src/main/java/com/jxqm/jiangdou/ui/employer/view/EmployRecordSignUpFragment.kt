package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.EmployRecordSignUpItem
import com.jxqm.jiangdou.ui.employer.adapter.EmployRecordSignUpAdapter
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordSignUpViewModel
import kotlinx.android.synthetic.main.fragment_employ_record_sign_up.*

/**
 * 雇佣记录 - 报名的Fragment
 * Created By bhx On 2019/9/3 0003 08:57
 */
class EmployRecordSignUpFragment : BaseMVVMFragment<EmployRecordSignUpViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD_SIGN_UP
    private val mEmployRecordSignUpItems = arrayListOf<EmployRecordSignUpItem>()
    private lateinit var mAdapter: EmployRecordSignUpAdapter
    override fun getLayoutId(): Int = R.layout.fragment_employ_record_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEmployRecordSignUpItems.add(EmployRecordSignUpItem())
        mEmployRecordSignUpItems.add(EmployRecordSignUpItem())
        mEmployRecordSignUpItems.add(EmployRecordSignUpItem())
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = EmployRecordSignUpAdapter(mContext)
        mAdapter.setDataList(mEmployRecordSignUpItems)
        recyclerView.adapter = mAdapter
    }


}