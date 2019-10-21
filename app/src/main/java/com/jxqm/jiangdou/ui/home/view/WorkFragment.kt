package com.jxqm.jiangdou.ui.home.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
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


/**
 * 工作台界面
 * Created by Administrator on 2019/8/20.
 */
class WorkFragment : BaseMVVMFragment<WorkViewModel>() {
    private var mEmployeeListFragment: EmployeeListFragment? = null
    private var mEmployerListFragment: EmployerListFragment? = null
    override fun getLayoutId(): Int = R.layout.fragment_work
    override fun getEventKey(): Any = Constants.EVENT_KEY_WORK
    private var isEmployee = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (MyApplication.instance().attestationViewModel?.statusCode == 2) {
            tvChange.text = "雇主"
            isEmployee = true
            ivScanCode.visibility = View.GONE
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
                tvChange.text = "雇主"
                ivScanCode.visibility = View.GONE
                showEmployerFragment()
            } else {
                tvChange.text = "雇员"
                ivScanCode.visibility = View.VISIBLE
                showEmployeeFragment(0)
            }
        }
        //点击扫码
        ivScanCode.clickWithTrigger {
            startScan()
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
                tvChange.text = "雇员"
                ivScanCode.visibility = View.VISIBLE
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
                    tvChange.text = "雇主"
                    ivScanCode.visibility = View.GONE
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
        mEmployeeListFragment = EmployeeListFragment()
        val bundle = Bundle()
        bundle.putInt("position", position)
        mEmployeeListFragment!!.arguments = bundle
        transaction.replace(R.id.flFragment, mEmployeeListFragment!!)
        transaction.commit()
    }

    private fun showEmployerFragment() {
        val transaction = childFragmentManager.beginTransaction()
        mEmployerListFragment = EmployerListFragment()
        transaction.replace(R.id.flFragment, mEmployerListFragment!!)
        transaction.commit()
    }
}
