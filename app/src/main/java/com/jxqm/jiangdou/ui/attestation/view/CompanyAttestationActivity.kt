package com.jxqm.jiangdou.ui.attestation.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.text.BoringLayout
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.baidu.mapapi.model.LatLng
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.ui.attestation.model.CompanyTypeModel
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.ui.attestation.vm.CompanyAttestationViewModel
import com.jxqm.jiangdou.ui.map.MapActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.SingleSelectDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.compress.CompressHelper
import com.zhihu.matisse.compress.FileUtil
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.ui.widget.CropImageView
import kotlinx.android.synthetic.main.activity_company_attestation.*
import java.io.File

/**
 * 企业认证
 * Created By bhx On 2019/9/4 0004 18:15
 */
class CompanyAttestationActivity : BaseDataActivity<CompanyAttestationViewModel>() {
    //企业类型
    private var mCompanyTypeList = mutableListOf<CompanyTypeModel>()
    private val mCompanyTypeItemList = mutableListOf<String>()
    //人员规模
    private var mCompanyPeopleList = mutableListOf<CompanyTypeModel>()
    private val mCompanyPeopleItemList = mutableListOf<String>()
    //所属行业
    private var mCompanyJobTypeList = mutableListOf<CompanyTypeModel>()
    private val mCompanyJobTypeItemList = mutableListOf<String>()
    private var mSelectCompanyType: CompanyTypeModel? = null
    private var mSelectCompanyPeople: CompanyTypeModel? = null
    private var mSelectCompanyJobType: CompanyTypeModel? = null
    private var mSelectFile: File? = null//选择的营业执照的图片
    private var mSelectLogoFile: File? = null//选择公司logo的图片
    private var mLocationLatLng: LatLng? = null//定位的经纬度信息
    private var mLocationProvince: String? = null
    private var mLocationCity: String? = null
    private var mLocationArea: String? = null
    private var mLocationAddress: String? = null
    private var mMapImgFilePath: String? = null
    private var mAttestationStatus: AttestationStatusModel? = null

    override fun getEventKey(): Any = Constants.EVENT_KEY_COMPANY_ATTESTATION

    override fun getLayoutId(): Int = R.layout.activity_company_attestation

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        //下一步
        tvNextStep.clickWithTrigger {
            val intent =
                Intent(this@CompanyAttestationActivity, PeopleAttestationActivity::class.java)
            //公司名称
            val companyName = etCompanyName.text.toString().trim()
            //机构简介
            val companyDescription = etCompanyDescription.text.toString().trim()
            //详细工作地址
            val locationDetails = etDetailsAddress.text.toString().trim()
            //选择地区
            val locationArea = tvLocationArea.text.toString().trim()
            if (mAttestationStatus == null && mSelectFile == null) {
                ToastUtils.toastShort("请选择营业执照")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(companyName)) {
                ToastUtils.toastShort("请输入公司名称")
                return@clickWithTrigger
            }
            if (mSelectCompanyJobType == null) {
                ToastUtils.toastShort("请选择所属行业")
                return@clickWithTrigger
            }
            if (mSelectCompanyType == null) {
                ToastUtils.toastShort("请选择企业类型")
                return@clickWithTrigger
            }
            if (mSelectCompanyPeople == null) {
                ToastUtils.toastShort("请选择人员规模")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(companyDescription) || companyDescription.length < 20) {
                ToastUtils.toastShort("机构简介请至少输入20个字符")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(locationArea)) {
                ToastUtils.toastShort("请选择工作地址")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(locationDetails)) {
                ToastUtils.toastShort("请输入详细工作地址")
                return@clickWithTrigger
            }
            intent.apply {
                putExtra("businessLicense", mSelectFile?.absolutePath)
                putExtra("logo", mSelectLogoFile?.absolutePath)
                putExtra("companyName", companyName)
                putExtra("companyDescription", companyDescription)
                putExtra("address", locationArea)
                putExtra("addressDetails", locationDetails)
                putExtra("province", mLocationProvince)
                putExtra("mapImageFilePath", mMapImgFilePath)
                putExtra("city", mLocationCity)
                putExtra("area", mLocationArea)
                putExtra("selectCompanyType", mSelectCompanyType?.id.toString())
                putExtra("selectCompanyPeople", mSelectCompanyPeople?.id.toString())
                putExtra("selectCompanyJobType", mSelectCompanyJobType?.id.toString())
                mLocationLatLng?.let {
                    putExtra("locationLat", it.latitude.toString())
                }
                mLocationLatLng?.let {
                    putExtra("locationLon", it.longitude.toString())
                }
                putExtra("AttestationStatus", mAttestationStatus?.toJson())
            }
            startActivity(intent)
        }
        //返回
        companyAttestationBack.clickWithTrigger {
            finish()
        }
        //选择工作地址
        llSelectCompanyArea.clickWithTrigger {
            requestGpsPermission()
        }
        //所属行业
        rlCompanyJobType.clickWithTrigger {
            SingleSelectDialog.show(this, mCompanyJobTypeItemList) {
                mSelectCompanyJobType = mCompanyJobTypeList[it]
                tvCompanyJobType.text = mSelectCompanyJobType?.codeName
                isNextStepEnable()
            }
        }
        //企业类型
        rlCompanyType.clickWithTrigger {
            SingleSelectDialog.show(this, mCompanyTypeItemList) {
                mSelectCompanyType = mCompanyTypeList[it]
                tvCompanyType.text = mSelectCompanyType?.codeName
                isNextStepEnable()
            }
        }
        //人员规模
        rlCompanyPeople.clickWithTrigger {
            SingleSelectDialog.show(this, mCompanyPeopleItemList) {
                mSelectCompanyPeople = mCompanyPeopleList[it]
                tvCompanyPeople.text = mSelectCompanyPeople?.codeName
                isNextStepEnable()
            }
        }
        //选择营业执照
        tvSelectAttestationImg.clickWithTrigger {
            requestSdPermission(false, REQUEST_CODE_SELECT_IMAGE)
        }
        tvCompanyLogoImg.clickWithTrigger {
            requestSdPermission(false, REQUEST_CODE_SELECT_IMAGE_LOGO)
        }
        //公司名称
        etCompanyName.addTextChangedListener {
            afterTextChanged {
                isNextStepEnable()
            }
        }
        //机构简介
        etCompanyDescription.addTextChangedListener {
            afterTextChanged {
                isNextStepEnable()
            }
        }
        //详细工作地址
        etDetailsAddress.addTextChangedListener {
            afterTextChanged {
                isNextStepEnable()
            }
        }
    }


    /**
     * 初始化数据
     */
    override fun initData() {
        mViewModel.getAttestationData()
    }

    /**
     * 是否可以点击
     */
    private fun isNextStepEnable() {
        val companyName = etCompanyName.text.toString().trim()
        val companyDescription = etCompanyDescription.text.toString().trim()
        val companyJobType = tvCompanyJobType.text.toString().trim()
        val companyType = tvCompanyType.text.toString().trim()
        val companyPeople = tvCompanyPeople.text.toString().trim()
        val detailsAddress = etDetailsAddress.text.toString().trim()
        val isCanClick =
            ((mSelectFile != null || mAttestationStatus != null) &&
                    (mSelectLogoFile != null || mAttestationStatus != null) &&
                    companyName.isNotEmpty() &&
                    detailsAddress.isNotEmpty() &&
                    companyDescription.isNotEmpty() &&
                    companyDescription.length >= 20 &&
                    companyJobType.isNotEmpty() &&
                    companyType.isNotEmpty() &&
                    companyPeople.isNotEmpty())
        if (isCanClick) {
            tvNextStep.setBackgroundResource(R.drawable.shape_button_select)
        } else {
            tvNextStep.setBackgroundResource(R.drawable.shape_button_default)
        }
    }

    /**
     * 是否可以点击下一步
     */
    private fun isCanClickNextStep() {

    }


    /**
     * 请求sd存储权限
     */
    private fun requestSdPermission(isCrop: Boolean, requestCode: Int) {
        val disposable =
            RxPermissions(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
                .subscribe {
                    LogUtils.i("requestGpsPermission$it")
                    if (it) {
                        selectImage(isCrop, requestCode)
                    }
                }
        addDisposable(disposable)
    }

    /**
     * 选择头像
     */
    private fun selectImage(isCrop: Boolean, requestCode: Int) {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .countable(true)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.jxqm.jiangdou.fileprovider"))
            .maxSelectable(1)
            .isCrop(isCrop)
            .cropStyle(CropImageView.Style.CIRCLE)
            .isCropSaveRectangle(false)
            .thumbnailScale(0.6f)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .imageEngine(GlideEngine())
            .forResult(requestCode)
    }

    /**
     * 请求Gps权限
     */
    private fun requestGpsPermission() {
        addDisposable(RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe { aBoolean ->
                if (aBoolean!!) {
                    LogUtils.i("获取定位权限成功")
                    val intent =
                        Intent(this@CompanyAttestationActivity, MapActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE_SELECT_AREA)

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        val locManager =
//                            getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                            startActivityForResult(
//                                intent,
//                                REQUEST_CODE_LOCATION_SETTING
//                            ) // 设置完成后返回到原来的界面
//                        } else {
//
//                        }
//                    }
                }
            })
    }


    /**
     * 注册Observer监听
     */
    override fun dataObserver() {
        registerObserver(Constants.TAG_GET_COMPANY_ITEM_RESULT, List::class.java).observe(
            this,
            Observer {
                val list = it as List<CompanyTypeModel>
                if (mCompanyTypeList.isEmpty()) {
                    mCompanyTypeList.addAll(list)
                    mCompanyTypeList.forEach { item ->
                        mCompanyTypeItemList.add(item.codeName)
                    }
                    return@Observer
                }
                if (mCompanyPeopleList.isEmpty()) {
                    mCompanyPeopleList.addAll(list)
                    mCompanyPeopleList.forEach { item ->
                        mCompanyPeopleItemList.add(item.codeName)
                    }
                    return@Observer
                }
                if (mCompanyJobTypeList.isEmpty()) {
                    mCompanyJobTypeList.addAll(list)
                    mCompanyJobTypeList.forEach { item ->
                        mCompanyJobTypeItemList.add(item.codeName)
                    }
                }
                showState()
            })

        registerObserver(
            Constants.TAG_GET_COMPANY_ATTESTATION_STATUS,
            AttestationStatusModel::class.java
        ).observe(
            this,
            Observer {
                mAttestationStatus = it
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_SELECT_IMAGE -> { //选择营业执照
                val file = FileUtil.getFileByPath(Matisse.obtainPathResult(data)[0])
                mSelectFile = CompressHelper.getDefault(this).compressToFile(file)
                mSelectFile?.let {
                    Glide.with(this)
                        .load(it)
                        .into(ivAttestationImg)
                    isNextStepEnable()
                }
            }
            REQUEST_CODE_SELECT_IMAGE_LOGO -> {//选择公司Logo
                val file = FileUtil.getFileByPath(Matisse.obtainPathResult(data)[0])
                mSelectLogoFile = CompressHelper.getDefault(this).compressToFile(file)
                mSelectLogoFile?.let {
                    Glide.with(this)
                        .load(it)
                        .into(ivCompanyLogoImg)
                    isNextStepEnable()
                }
            }
            REQUEST_CODE_SELECT_AREA -> {
                data?.let {
                    mMapImgFilePath = Constants.APP_SAVE_DIR + "/" + Constants.MAPVIEW_FILENAME
                    tvLocationArea.text = it.getStringExtra("name")
                    mLocationLatLng = it.getParcelableExtra("latLng")
                    mLocationCity = it.getStringExtra("city")
                    mLocationArea = it.getStringExtra("area")
                    mLocationAddress = it.getStringExtra("address")
                    mLocationProvince = it.getStringExtra("province")
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showState() {
        LogUtils.i("showState$mAttestationStatus")
        mAttestationStatus?.let {
            //营业执照
            flAttestationStatusParent.visibility = View.VISIBLE
            tvAttestationStatusText.text = it.status
            //公司LOGO
            flCompanyLogoStatusParent.visibility = View.VISIBLE
            tvCompanyLogoStatusText.text = it.status
            LogUtils.i(Api.HTTP_BASE_URL + it.businessLicense)
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.businessLicense)
                .into(ivAttestationImg)
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.logo).into(ivCompanyLogoImg)
            etCompanyName.setText(it.employerName)
            etCompanyDescription.setText(it.introduction)
            //企业类型
            mSelectCompanyType = mCompanyTypeList.find { companyTypeModel ->
                companyTypeModel.id == it.qylx.toInt()
            }
            mSelectCompanyType?.let { companyTypeModel ->
                tvCompanyType.text = companyTypeModel.codeName
            }
            //行业类型
            mSelectCompanyJobType = mCompanyJobTypeList.find { companyJobTypeModel ->
                companyJobTypeModel.id == it.hyfl.toInt()
            }
            mSelectCompanyJobType?.let { companyJobTypeModel ->
                tvCompanyJobType.text = companyJobTypeModel.codeName
            }
            //人员规模
            mSelectCompanyPeople = mCompanyPeopleList.find { companyPeopleModel ->
                companyPeopleModel.id == it.rygm.toInt()
            }
            mSelectCompanyPeople?.let { companyPeopleModel ->
                tvCompanyPeople.text = companyPeopleModel.codeName
            }
            //定位数据
            tvLocationArea.text = it.address
            etDetailsAddress.setText(it.addressDetail)
            when (it.statusCode) {
                1 -> {//审核中
                    tvSelectAttestationImg.isEnabled = false
                    tvCompanyLogoImg.isEnabled = false

                }
                2 -> {//已认证
                    tvSelectAttestationImg.isEnabled = false
                    tvCompanyLogoImg.isEnabled = false
                    etCompanyName.isEnabled = false
                    rlCompanyJobType.isEnabled = false
                    rlCompanyType.isEnabled = false
                    rlCompanyPeople.isEnabled = false
                    etCompanyDescription.isEnabled = false
                    llSelectCompanyArea.isEnabled = false
                    etDetailsAddress.isEnabled = false
                }
            }
            isNextStepEnable()
        }
    }

    companion object {
        const val REQUEST_CODE_SELECT_IMAGE = 0x01
        const val REQUEST_CODE_SELECT_IMAGE_LOGO = 0x04
        const val REQUEST_CODE_LOCATION_SETTING = 0x03
        const val REQUEST_CODE_SELECT_AREA = 0x02 // 选择工作地址
    }
}