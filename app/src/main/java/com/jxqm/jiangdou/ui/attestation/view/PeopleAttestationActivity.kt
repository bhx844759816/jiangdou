package com.jxqm.jiangdou.ui.attestation.view

import android.content.Intent
import android.content.pm.ActivityInfo
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.ui.attestation.vm.PeopleAttestationViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.AttestationSuccessDialog
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
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
    private var companyName: String? = null // 机构名称
    private var locationArea: String? = null //定位
    private var locationDetails: String? = null//详细地址
    private var companyDescription: String? = null//企业简介
    private var mSelectCompanyType: String? = null//企业类型
    private var mSelectCompanyPeople: String? = null//人员规模
    private var mSelectCompanyJobType: String? = null//所属行业
    private var locationLat: Double = 0.0
    private var locationLon: Double = 0.0

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        tvSubmit.clickWithTrigger {
            //提交认证信息
            val name = etUserName.text.toString().trim()//姓名
            val idCardNum = etIdNum.text.toString().trim()//身份证号
            val alipay = etPayNumber.text.toString().trim()//支付宝账号
            val contacts = etContacts.text.toString().trim()//招聘联系人
            val phone = etContactsPhone.text.toString().trim()//联系人电话
            val fileMaps = mutableMapOf<String, File>() // 上传文件数组
            val paramsMaps = mutableMapOf<String, String>() //上传参数数组
            //提交的参数
            paramsMaps["address"] = locationDetails!!
            paramsMaps["alipay"] = alipay
            paramsMaps["area"] = locationArea!!
            paramsMaps["contact"] = contacts
            paramsMaps["employerName"] = companyName!!
            paramsMaps["hylx"] = mSelectCompanyJobType!!
            paramsMaps["qylx"] = mSelectCompanyType!!
            paramsMaps["rygm"] = mSelectCompanyPeople!!
            paramsMaps["name"] = name
            paramsMaps["introduction"] = companyDescription!!
            paramsMaps["idcard"] = idCardNum
            paramsMaps["latitude"] = locationLat.toString()
            paramsMaps["longitude"] = locationLon.toString()
            //上传的文件
            fileMaps["businessLicense"] = File(businessLicensePath)
            fileMaps["idcardBack"] = mIdBackImgFile!!
            fileMaps["idcardFront"] = mIdFrontImgFile!!
            mViewModel.submit(fileMaps, paramsMaps)
        }

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
        tvSubmit.isEnable(etUserName) { isSubmitState() }
        tvSubmit.isEnable(etIdNum) { isSubmitState() }
        tvSubmit.isEnable(etPayNumber) { isSubmitState() }
        tvSubmit.isEnable(etContacts) { isSubmitState() }
        tvSubmit.isEnable(etContactsPhone) { isSubmitState() }
    }

    override fun initData() {
        intent.apply {
            businessLicensePath = getStringExtra("businessLicense")
            companyName = getStringExtra("companyName")
            locationDetails = getStringExtra("locationDetails")
            locationArea = getStringExtra("locationArea")
            companyDescription = getStringExtra("companyDescription")
            mSelectCompanyType = getStringExtra("selectCompanyType")
            mSelectCompanyPeople = getStringExtra("selectCompanyPeople")
            mSelectCompanyJobType = getStringExtra("selectCompanyJobType")
            locationLat = getDoubleExtra("locationLat", 0.0)
            locationLon = getDoubleExtra("locationLon", 0.0)
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
        if (isFront) {
            mIdFrontImgFile = FileUtil.getFileByPath(Matisse.obtainPathResult(data)[0])
        } else {
            mIdBackImgFile = FileUtil.getFileByPath(Matisse.obtainPathResult(data)[0])
        }
        Glide.with(this)
            .load(if (isFront) mIdFrontImgFile else mIdBackImgFile)
            .into(if (isFront) ivPeopleCardPositive else ivPeopleCardBack)
        tvSubmit.isEnabled = isSubmitState()

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


    private fun isSubmitState(): Boolean {
        return mIdFrontImgFile != null &&
                mIdBackImgFile != null &&
                etUserName.text.toString().trim().isNotEmpty() &&
                etIdNum.text.toString().trim().isNotEmpty() &&
                etPayNumber.text.toString().trim().isNotEmpty() &&
                etContacts.text.toString().trim().isNotEmpty() &&
                etContactsPhone.text.toString().trim().isNotEmpty()
    }

    companion object {
        const val REQUEST_CODE_SELECT_IMAGE_FRONT = 0x01
        const val REQUEST_CODE_SELECT_IMAGE_BACK = 0x02
    }
}