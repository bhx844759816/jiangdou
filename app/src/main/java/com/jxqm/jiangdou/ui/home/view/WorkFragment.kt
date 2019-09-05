package com.jxqm.jiangdou.ui.home.view

import android.os.Bundle
import android.view.View
import com.bhx.common.base.BaseFragment
import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employee.view.EmployeeListFragment
import com.jxqm.jiangdou.ui.employer.view.EmployerListFragment
import com.jxqm.jiangdou.ui.home.vm.WorkViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * 工作台界面
 * Created by Administrator on 2019/8/20.
 */
class WorkFragment : BaseFragment() {
    private var isEmployee = false
    override fun getLayoutId(): Int = R.layout.fragment_work

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvChange.clickWithTrigger {
            isEmployee = !isEmployee
            if (isEmployee) {
                tvChange.text = "雇主"
                ivScanCode.visibility = View.GONE
                showEmployerFragment()
            } else {
                tvChange.text = "雇员"
                ivScanCode.visibility = View.VISIBLE
                showEmployeeFragment()
            }
        }
        showEmployeeFragment()
    }

    override fun fetchData() {

    }

    private fun showEmployeeFragment() {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, EmployeeListFragment())
        transaction.commit()
    }

    private fun showEmployerFragment() {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, EmployerListFragment())
        transaction.commit()
    }
}
