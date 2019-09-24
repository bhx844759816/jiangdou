package com.jxqm.jiangdou.ui.employee.view

import android.os.Bundle
import android.view.View
import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employee.vm.EmployeeEmploymentViewModel

/**
 * 雇员 - 已录用
 * Created By bhx On 2019/9/24 0024 15:56
 */
class EmployeeEmploymentFragment : BaseMVVMFragment<EmployeeEmploymentViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOYEE_EMPLOYMENT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}