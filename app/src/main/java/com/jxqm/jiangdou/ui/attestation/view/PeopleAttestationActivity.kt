package com.jxqm.jiangdou.ui.attestation.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.bhx.common.utils.AppManager
import com.bhx.common.utils.PhoneUtils
import com.bhx.common.utils.ToastUtils
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.ui.attestation.vm.PeopleAttestationViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.AttestationSuccessDialog
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.compress.CompressHelper
import com.zhihu.matisse.compress.FileUtil
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.ui.widget.CropImageView
import kotlinx.android.synthetic.main.activity_people_attestation.*
import java.io.File

/**
 * 身份认证
 * Created By bhx On 2019/8/9 0009 11:58
 */
class PeopleAttestationActivity : BaseDataActivity<PeopleAttestationViewModel>() {

    private var mIdFrontImgFile: File? = null
    private var mIdBackImgFile: File? = null

    override fun getLayoutId(): Int = R.layout.activity_people_attestation
    override fun getEventKey(): Any = Constants.EVENT_KEY_PEOPLE_ATTESTATION

    private var businessLicensePath: String? = null//营业执照图片
    private var mMapImgFilePath: String? = null
    private var companyName: String? = null // 机构名称
    private var mLocationProvince: String? = null //省
    private var mLocationCity: String? = null//市
    private var mLocationArea: String? = null//区
    private var mLocationAddress: String? = null//地址
    private var mLocationAddressDetails: String? = null//详细地址
    private var companyDescription: String? = null//企业简介
    private var mSelectCompanyType: String? = null//企业类型
    private var mSelectCompanyPeople: String? = null//人员规模
    private var mSelectCompanyJobType: String? = null//所属行业
    private var locationLat: String? = null
    private var locationLon: String? = null
    private var mAttestationStatus: AttestationStatusModel? = null

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        tvSubmit.clickWithTrigger {
            //提交认证信息
            val duty = etUserName.text.toString().trim()//负责人
            val idCardNum = etIdNum.text.toString().trim()//身份证号
            val alipay = etPayNumber.text.toString().trim()//支付宝账号
            val contacts = etContacts.text.toString().trim()//招聘联系人
            val phone = etContactsPhone.text.toString().trim()//联系人电话
            val fileMaps = mutableMapOf<String, File>() // 上传文件数组
            if (TextUtils.isEmpty(duty)) {
                ToastUtils.toastShort("请输入负责人")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(idCardNum) || idCardNum.length != 18) {
                ToastUtils.toastShort("请输入正确的身份证号")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(alipay)) {
                ToastUtils.toastShort("请输入支付宝账号")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(contacts)) {
                ToastUtils.toastShort("请输入招聘联系人")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.toastShort("请输入招聘咨询电话")
                return@clickWithTrigger
            }
            val paramsMaps = mutableMapOf<String, String>() //上传参数数组
            paramsMaps["duty"] = duty
            paramsMaps["tel"] = phone
            paramsMaps["idcard"] = idCardNum
            paramsMaps["alipay"] = alipay
            paramsMaps["contact"] = contacts
            mAttestationStatus?.let {
                paramsMaps["id"] = it.id
            }
            mLocationAddress?.let {
                paramsMaps["address"] = it
            }
            mLocationAddressDetails?.let {
                paramsMaps["addressDetail"] = it
            }
            mLocationProvince?.let {
                paramsMaps["province"] = it
            }
            mLocationCity?.let {
                paramsMaps["city"] = it
            }
            mLocationArea?.let {
                paramsMaps["area"] = it
            }
            companyName?.let {
                paramsMaps["employerName"] = it
            }
            mSelectCompanyJobType?.let {
                paramsMaps["hyfl"] = it
            }
            mSelectCompanyType?.let {
                paramsMaps["qylx"] = it
            }
            mSelectCompanyPeople?.let {
                paramsMaps["rygm"] = it
            }
            companyDescription?.let {
                paramsMaps["introduction"] = it
            }
            locationLat?.let {
                paramsMaps["latitude"] = it
            }
            locationLon?.let {
                paramsMaps["longitude"] = it
            }
            businessLicensePath?.let {
                fileMaps["businessLicenseFile"] = File(it) //企业证书
            }
            mIdBackImgFile?.let {
                fileMaps["idcardBackFile"] = it//身份证反面
            }
            mIdFrontImgFile?.let {
                fileMaps["idcardFrontFile"] = it//身份证正面
            }
            mMapImgFilePath?.let {
                fileMaps["mapImgFile"] = File(it)
            }
            mViewModel.submit(fileMaps, paramsMaps)
        }
        //back
        peopleAttestationBack.clickWithTrigger {
            finish()
        }
        //省份证正面
        tvPeopleCardPositive.clickWithTrigger {
            selectImage(1, REQUEST_CODE_SELECT_IMAGE_FRONT)
        }
        //身份证反面
        tvPeopleCardBack.clickWithTrigger {
            selectImage(1, REQUEST_CODE_SELECT_IMAGE_BACK)
        }
        etUserName.addTextChangedListener {
            afterTextChanged {
                isSubmitState()
            }
        }
        etIdNum.addTextChangedListener {
            afterTextChanged {
                isSubmitState()
            }
        }
        etPayNumber.addTextChangedListener {
            afterTextChanged {
                isSubmitState()
            }
        }
        etContacts.addTextChangedListener {
            afterTextChanged {
                isSubmitState()
            }
        }
        etContactsPhone.addTextChangedListener {
            afterTextChanged {
                isSubmitState()
            }
        }
    }

    override fun initData() {
        intent.apply {
            businessLicensePath = getStringExtra("businessLicense")
            mMapImgFilePath = getStringExtra("mapImageFilePath")
            companyName = getStringExtra("companyName")
            mLocationAddress = getStringExtra("address")
            mLocationAddressDetails = getStringExtra("addressDetails")
            mLocationCity = getStringExtra("city")
            mLocationArea = getStringExtra("area")
            mLocationProvince = getStringExtra("province")
            companyDescription = getStringExtra("companyDescription")
            mSelectCompanyType = getStringExtra("selectCompanyType")
            mSelectCompanyPeople = getStringExtra("selectCompanyPeople")
            mSelectCompanyJobType = getStringExtra("selectCompanyJobType")
            locationLat = getStringExtra("locationLat")
            locationLon = getStringExtra("locationLon")
            getStringExtra("AttestationStatus")?.let {
                mAttestationStatus = CommonConfig.fromJson(it, AttestationStatusModel::class.java)
            }
            showState()
        }
    }

    private fun showState() {
        mAttestationStatus?.let {
            flCardPositiveStatusParent.visibility = View.VISIBLE
            flCardBackStatusParent.visibility = View.VISIBLE
            tvCardPositiveStatusText.text = it.status
            tvCardBackStatusText.text = it.status
            Glide.with(this).load(Api.HTTP_BASE_URL + it.idcardFront)
                .into(ivPeopleCardPositive)
            Glide.with(this).load(Api.HTTP_BASE_URL + it.idcardBack)
                .into(ivPeopleCardBack)
            when (it.statusCode) {
                1, 2 -> {//审核中 //已认证
                    tvPeopleCardPositive.isEnabled = false
                    tvPeopleCardBack.isEnabled = false
                }
            }
            //
            etUserName.setText(it.duty)
            etContacts.setText(it.contact)
            etIdNum.setText(it.idcard)
            etPayNumber.setText(it.alipay)
            etContactsPhone.setText(it.tel)
            isSubmitState()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SELECT_IMAGE_FRONT -> {
                data?.let {
                    showIdCardImg(it, true)
                }
            }
            REQUEST_CODE_SELECT_IMAGE_BACK -> {
                data?.let {
                    showIdCardImg(it, false)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 展示身份证正反面
     */
    private fun showIdCardImg(data: Intent, isFront: Boolean) {
        val file = FileUtil.getFileByPath(Matisse.obtainPathResult(data)[0])
        if (isFront) {
            mIdFrontImgFile = CompressHelper.getDefault(this).compressToFile(file)
        } else {
            mIdBackImgFile = CompressHelper.getDefault(this).compressToFile(file)
        }
        Glide.with(this)
            .load(if (isFront) mIdFrontImgFile else mIdBackImgFile)
            .into(if (isFront) ivPeopleCardPositive else ivPeopleCardBack)
        isSubmitState()
    }

    /**
     * 选择头像
     */
    private fun selectImage(maxSelectable: Int, requestCode: Int) {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .countable(true)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.jxqm.jiangdou.fileprovider"))
            .maxSelectable(maxSelectable)
            .isCrop(false)
            .cropStyle(CropImageView.Style.CIRCLE)
            .isCropSaveRectangle(false)
            .thumbnailScale(0.6f)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .imageEngine(GlideEngine())
            .forResult(requestCode)
    }

    /**
     * 是否是提交状态
     */
    private fun isSubmitState() {
        val userName = etUserName.text.toString().trim()
        val peopleCard = etIdNum.text.toString().trim()
        val payNum = etPayNumber.text.toString().trim()
        val contacts = etContacts.text.toString().trim()
        val phone = etContactsPhone.text.toString().trim()
        val isCanClick = (mIdFrontImgFile != null || mAttestationStatus != null) &&
                (mIdBackImgFile != null || mAttestationStatus != null) &&
                userName.isNotEmpty() &&
                peopleCard.isNotEmpty() &&
                peopleCard.length == 18 &&
                payNum.isNotEmpty() &&
                contacts.isNotEmpty() &&
                phone.isNotEmpty()
        if (isCanClick) {
            tvSubmit.setBackgroundResource(R.drawable.shape_button_select)
        } else {
            tvSubmit.setBackgroundResource(R.drawable.shape_button_default)
        }
    }

    /**
     *
     */
    override fun dataObserver() {
        registerObserver(
            Constants.TAG_PEOPLE_ATTESTATION_SUBMIT_SUCCESS,
            Boolean::class.java
        ).observe(this, Observer {
            AttestationSuccessDialog.show(this@PeopleAttestationActivity) {
                AppManager.getAppManager().finishActivity(CompanyAttestationActivity::class.java)
                finish()
            }
        })
    }

    companion object {
        const val REQUEST_CODE_SELECT_IMAGE_FRONT = 0x01
        const val REQUEST_CODE_SELECT_IMAGE_BACK = 0x02
    }
}