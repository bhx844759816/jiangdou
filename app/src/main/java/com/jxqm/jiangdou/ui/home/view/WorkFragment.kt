package com.jxqm.jiangdou.ui.home.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import cn.bertsir.zbar.Qr.Config
import cn.bertsir.zbar.Qr.ScanResult
import cn.bertsir.zbar.QrConfig
import cn.bertsir.zbar.view.ScanLineView
import com.bhx.common.base.BaseFragment
import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employee.view.EmployeeListFragment
import com.jxqm.jiangdou.ui.employer.view.EmployerListFragment
import com.jxqm.jiangdou.ui.home.vm.WorkViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.fragment_work.*
import cn.bertsir.zbar.QrManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.hideKeyboard
import com.jxqm.jiangdou.utils.startActivity


/**
 * 工作台界面
 * Created by Administrator on 2019/8/20.
 */
class WorkFragment : BaseMVVMFragment<WorkViewModel>() {
    private var mCurrentFragment: Fragment? = null
    override fun getLayoutId(): Int = R.layout.fragment_work
    override fun getEventKey(): Any = Constants.EVENT_KEY_WORK
    private var isEmployee = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (MyApplication.instance().attestationViewModel?.statusCode == 2) {
            isEmployee = true
            changeUiStatus()
            showEmployerFragment()
        } else {
            showEmployeeFragment(0)
        }
        tvChange.clickWithTrigger {
            isEmployee = !isEmployee
            if (isEmployee) {
                if (MyApplication.instance().attestationViewModel == null) {
                    mViewModel.getAttestationStatus()
                    return@clickWithTrigger
                }
                if (MyApplication.instance().attestationViewModel?.statusCode != 2) {
                    mViewModel.getAttestationStatus()
                    return@clickWithTrigger
                }
                changeUiStatus()
                showEmployerFragment()
            } else {
                changeUiStatus()
                showEmployeeFragment(0)
            }
        }
        //点击扫码
        ivScanCode.clickWithTrigger {
            startScan()
        }

        etSearch.addTextChangedListener {
            afterTextChanged {
                val content = etSearch.text.toString().trim()
                if (content.isNotEmpty()) {
                    ivDelete.visibility = View.VISIBLE
                } else {
                    ivDelete.visibility = View.GONE
                }
            }
        }
        ivDelete.clickWithTrigger {
            etSearch.setText("")
        }
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchKey = etSearch.text.toString()
                MyApplication.instance().searchKeyWork = searchKey
                if (searchKey.isNotEmpty()) {
                    etSearch.hideKeyboard()
                    //存储到SharedPreference
                    doSearch(searchKey)
                    return@setOnEditorActionListener true
                } else {
                    ToastUtils.toastShort("请输入搜索关键词")
                }
            }
            return@setOnEditorActionListener false
        }

    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //跳转到工作台 雇员
        registerObserver(
            Constants.TAG_STATUS_EMPLOYEE_SETTLEMENT,
            Boolean::class.java
        ).observe(this,
            Observer {
                isEmployee = false
                changeUiStatus()
                showEmployeeFragment(3)
            })
        //获取审核状态
        registerObserver(
            Constants.TAG_GET_EMPLOYER_ATTESTATION_STATUS,
            Boolean::class.java
        ).observe(this,
            Observer {
                val attestationStatus = MyApplication.instance().attestationViewModel
                if (attestationStatus != null && attestationStatus.statusCode == 2) {
                    //已认证
                    changeUiStatus()
                    showEmployerFragment()
                } else {
                    isEmployee = false
                    if (attestationStatus == null) {
                        ToastUtils.toastShort("未认证，请先认证")
                    } else if (attestationStatus.statusCode != 2) {
                        ToastUtils.toastShort("正在审核，请耐心等待")
                    }
                }
            })
    }

    private fun changeUiStatus() {
        if (isEmployee) {
            tvChange.text = "雇主"
            ivScanCode.visibility = View.GONE
            etSearch.hint = "搜索职位"
            etSearch.setText("")
        } else {
            tvChange.text = "雇员"
            ivScanCode.visibility = View.VISIBLE
            etSearch.hint = "搜索已投职位"
            etSearch.setText("")
        }

    }

    /**
     * 模糊查询对应的
     */
    private fun doSearch(searchKey: String) {
        mCurrentFragment?.let {
            when (it) {
                is EmployeeListFragment -> {//雇员搜索
//                    (mCurrentFragment as EmployeeListFragment).doSearch(searchKey)
                }
                is EmployerListFragment -> {//雇主搜索
//                    (mCurrentFragment as EmployerListFragment).doSearch(searchKey)
                }
                else -> {

                }
            }
        }
    }

    override fun onFirstUserVisible() {


    }

    private fun startScan() {
        val qrConfig = QrConfig.Builder()
            .setShowTitle(true)
            .setShowLight(false)//显示手电筒按钮
            .setShowAlbum(false)//显示从相册选择按钮
            .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
            .setTitleBackgroudColor(Color.parseColor("#262020"))//设置状态栏颜色
            .setTitleTextColor(Color.WHITE)//设置Title文字颜色
            .setTitleText("扫码签到")//设置Tilte文字
            .setCornerColor(Color.parseColor("#E42E30"))//设置扫描框颜色
            .setLineColor(Color.parseColor("#E42E30"))//设置扫描线颜色
            .setScanLineStyle(ScanLineView.style_line)
            .setPlaySound(true)
            .create()
        QrManager.getInstance().init(qrConfig).startScan(
            activity
        ) { result ->
            run {
                mViewModel.employeeArrival(result.content)
            }
        }

    }

    private fun showEmployeeFragment(position: Int) {
        val transaction = childFragmentManager.beginTransaction()
        val employeeListFragment = EmployeeListFragment()
        val bundle = Bundle()
        bundle.putInt("position", position)
        employeeListFragment.arguments = bundle
        mCurrentFragment = employeeListFragment
        transaction.replace(R.id.flFragment, employeeListFragment)
        transaction.commit()
    }

    private fun showEmployerFragment() {
        val transaction = childFragmentManager.beginTransaction()
        val employerListFragment = EmployerListFragment()
        mCurrentFragment = employerListFragment
        transaction.replace(R.id.flFragment, employerListFragment)
        transaction.commit()
    }
}
