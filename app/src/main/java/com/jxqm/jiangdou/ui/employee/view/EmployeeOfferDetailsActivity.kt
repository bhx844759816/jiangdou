package com.jxqm.jiangdou.ui.employee.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.bhx.common.http.RxHelper
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.model.JobEmployeeModel
import com.jxqm.jiangdou.ui.employee.vm.EmployeeOfferDetailsViewModel
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.MapSelectDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_employee_offer_details.*

class EmployeeOfferDetailsActivity : BaseDataActivity<EmployeeOfferDetailsViewModel>() {

    private var mJobDetailsModel: JobEmployeeModel? = null
    override fun getLayoutId(): Int = R.layout.activity_employee_offer_details

    override fun getEventKey(): Any = Constants.EVENT_EMPLOYEE_OFFER_DETAILS

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        val jsonString = intent.getStringExtra("JobEmployeeModel")
        jsonString?.let {
            mJobDetailsModel = CommonConfig.fromJson(it, JobEmployeeModel::class.java)
            tvCompanyName.text = mJobDetailsModel?.employerName
            tvContacts.text = "联系人: ${mJobDetailsModel?.contact}"
            tvArrivalTime.text = "到岗时间:  ${mJobDetailsModel?.startTime}"
            tvContactPhone.text = "联系电话:  ${mJobDetailsModel?.tel}"
            tvJobType.text = mJobDetailsModel?.title
            tvLocation.text =
                "地址： ${mJobDetailsModel?.city + mJobDetailsModel?.area + mJobDetailsModel?.address + mJobDetailsModel?.addressDetail}"
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + mJobDetailsModel?.logo)
                .transform(GlideCircleTransform(this)).into(ivCompanyLogo)
        }
        tvLocation.clickWithTrigger {
            mJobDetailsModel?.let {
                MapSelectDialog.show(
                    this,
                    it.latitude.toString(),
                    it.longitude.toString(),
                    it.address
                )
            }
        }
        rlContactPhone.clickWithTrigger {
            mJobDetailsModel?.let {
                callPhone(it.tel)
            }
        }
        rlJobType.clickWithTrigger {
            val intent = Intent(this, JobDetailsActivity::class.java)
            intent.putExtra("JobId", mJobDetailsModel?.id.toString())
            intent.putExtra("Status", JobDetailsActivity.STATUS_SINGUP)
            startActivity(intent)
        }

        rlBack.clickWithTrigger { finish() }
    }

    private fun callPhone(phone: String) {
        addDisposable(RxPermissions(this).request(Manifest.permission.CALL_PHONE)
            .compose(RxHelper.io_main())
            .subscribe { result ->
                if (result) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_DIAL
                    intent.data = Uri.parse("tel:$phone")
                    startActivity(intent)
                }
            })
    }
}