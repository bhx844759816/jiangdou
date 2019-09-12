package com.jxqm.jiangdou.ui.attestation.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.Observer
import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.utils.LogUtils
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.model.CompanyTypeModel
import com.jxqm.jiangdou.ui.attestation.vm.CompanyAttestationViewModel
import com.jxqm.jiangdou.ui.map.MapActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.SingleSelectDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
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
class CompanyAttestationActivity : BaseMVVMActivity<CompanyAttestationViewModel>() {
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
    private var mCompanyArea: String? = null //公司地址
    private var mCompanyScreenShot: String? = null//公司地址的截图

    override fun getEventKey(): Any = Constants.EVENT_KEY_COMPANY_ATTESTATION

    override fun getLayoutId(): Int = R.layout.activity_company_attestation

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        //下一步
        tvNextStep.clickWithTrigger {
            startActivity<PeopleAttestationActivity>()
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
            requestSdPermission()
        }
        //公司名称
        etCompanyName.addTextChangedListener {
            afterTextChanged {
                isNextStepEnable()
            }
        }
    }

    private fun isNextStepEnable() {
        val companyName = etCompanyName.text.toString().trim()
        if (mSelectFile != null && companyName.isNotEmpty()
            && mSelectCompanyType != null && mSelectCompanyPeople != null
            && mSelectCompanyJobType != null
        ) {
            tvNextStep.isEnabled = true
        }
    }

    /**
     * 请求sd存储权限
     */
    private fun requestSdPermission() {
        val disposable =
            RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe {
                    LogUtils.i("requestGpsPermission$it")
                    if (it) {
                        selectImage(1, REQUEST_CODE_SELECT_IMAGE)
                    }
                }
        addDisposable(disposable)
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
            .isCrop(true)
            .cropStyle(CropImageView.Style.CIRCLE)
            .isCropSaveRectangle(false)
            .thumbnailScale(0.6f)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .imageEngine(GlideEngine())
            .forResult(requestCode)
    }

    /**
     * 初始化数据
     */
    override fun initData() {
        mViewModel.getCompanyPeople()
        mViewModel.getCompanyJobType()
        mViewModel.getCompanyType()
    }

    /**
     * 请求Gps权限
     */
    private fun requestGpsPermission() {
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

    /**
     * 注册Observer监听
     */
    override fun dataObserver() {
        registerObserver(Constants.TAG_GET_COMPANY_TYPE_RESULT, List::class.java).observe(this, Observer {
            val list = it as List<CompanyTypeModel>
            mCompanyTypeList.clear()
            mCompanyTypeList.addAll(list)
            mCompanyTypeItemList.clear()
            mCompanyTypeList.forEach { item ->
                mCompanyTypeItemList.add(item.codeName)
            }
        })
        registerObserver(Constants.TAG_GET_COMPANY_PEOPLE_RESULT, List::class.java).observe(this, Observer {
            val list = it as List<CompanyTypeModel>
            mCompanyPeopleList.clear()
            mCompanyPeopleList.addAll(list)
            mCompanyPeopleItemList.clear()
            mCompanyPeopleList.forEach { item ->
                mCompanyPeopleItemList.add(item.codeName)
            }
        })
        registerObserver(Constants.TAG_GET_COMPANY_JOB_TYPE_RESULT, List::class.java).observe(this, Observer {
            val list = it as List<CompanyTypeModel>
            mCompanyJobTypeList.clear()
            mCompanyJobTypeList.addAll(list)
            mCompanyJobTypeItemList.clear()
            mCompanyJobTypeList.forEach { item ->
                mCompanyJobTypeItemList.add(item.codeName)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SELECT_IMAGE -> {
                mSelectFile = FileUtil.getFileByPath(Matisse.obtainPathResult(data)[0])
                mSelectFile?.let {
                    Glide.with(this)
                        .load(it)
                        .into(ivAttestationImg)
                    isNextStepEnable()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val REQUEST_CODE_SELECT_IMAGE = 0x01
    }
}