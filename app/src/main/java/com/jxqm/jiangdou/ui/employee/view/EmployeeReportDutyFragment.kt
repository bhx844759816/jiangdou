package com.jxqm.jiangdou.ui.employee.view

import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employee.vm.EmployeeReportDutyViewModel

/**
 * 雇员 - 已到岗
 * Created By bhx On 2019/9/24 0024 15:59
 */
class EmployeeReportDutyFragment :BaseMVVMFragment<EmployeeReportDutyViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_employee_work_list
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOYEE_REPORT_DUTY
}