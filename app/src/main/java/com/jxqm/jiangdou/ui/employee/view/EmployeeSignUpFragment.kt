package com.jxqm.jiangdou.ui.employee.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employee.vm.EmployeeSignUpViewModel
import kotlinx.android.synthetic.main.fragment_end_sign_up_publish_layout.*

/**
 * 雇员界面 - 已报名
 * Created By bhx On 2019/9/24 0024 15:53
 */
class EmployeeSignUpFragment : BaseMVVMFragment<EmployeeSignUpViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOYEE_SIGN_UP
    private var isRefresh: Boolean = true
    private lateinit var mUiStatusController: UiStatusController

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
    }
}