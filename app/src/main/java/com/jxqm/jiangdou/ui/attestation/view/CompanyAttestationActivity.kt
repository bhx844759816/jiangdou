package com.jxqm.jiangdou.ui.attestation.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.LogUtils
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.map.MapActivity
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_company_attestation.*

/**
 * 企业认证
 * Created By bhx On 2019/9/4 0004 18:15
 */
class CompanyAttestationActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_company_attestation
    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        tvNextStep.clickWithTrigger {
            startActivity<PeopleAttestationActivity>()
        }

        companyAttestationBack.clickWithTrigger {
            finish()
        }

        llSelectCompanyArea.clickWithTrigger {
            requestPermission()
        }
    }

    private fun requestPermission() {
        addDisposable(RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe { aBoolean ->
                if (aBoolean!!) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            LogUtils.i("获取定位权限")
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivityForResult(intent, 0x01) // 设置完成后返回到原来的界面
                        } else {
                            LogUtils.i("获取定位权限成功")
                            startActivity<MapActivity>()
                        }
                    }
                }
            })
    }
}