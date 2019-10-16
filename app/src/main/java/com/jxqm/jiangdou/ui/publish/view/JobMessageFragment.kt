package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.baidu.mapapi.model.LatLng
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.ui.map.MapActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.SelectSexDialog
import kotlinx.android.synthetic.main.fragment_job_message.*

/**
 * 发布工作的消息信息
 * Created By bhx On 2019/8/8 0008 11:38
 */
class JobMessageFragment : BaseLazyFragment() {

    private var mCallback: OnJobPublishCallBack? = null
    private var mLocationLatLng: LatLng? = null//定位的经纬度信息
    private var mLocationProvince: String? = null
    private var mLocationCity: String? = null
    private var mLocationArea: String? = null
    private var mLocationAddress: String? = null
    private var mSex: Int = 2
    private val mParams = mutableMapOf<String, String>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_message

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        tvNextStep.clickWithTrigger {
            val title = tvJopTitleContent.text.toString().trim()
            val content = tvJopDescriptionContent.text.toString().trim()
            val recruitNum = etWorkPeopleNum.text.toString().trim()
            val address = tvLocationArea.text.toString().trim()
            val addressDetail = etDetailAddress.text.toString().trim()
            if (TextUtils.isEmpty(title)) {
                ToastUtils.toastShort("请输入兼职标题")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(content) || content.length < 20) {
                ToastUtils.toastShort("兼职描述请至少输入20个字符")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(recruitNum) || recruitNum.toInt() == 0) {
                ToastUtils.toastShort("招聘人数至少一人")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(address)) {
                ToastUtils.toastShort("请选择工作地点")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(addressDetail)) {
                ToastUtils.toastShort("请输入详细工作地点")
                return@clickWithTrigger
            }
            mParams["title"] = title//兼职标题
            mParams["content"] = content//兼职描述
            mParams["gender"] = mSex.toString()
            mParams["recruitNum"] = recruitNum//招聘人数
            mParams["address"] = address //定位地点
            mParams["addressDetail"] = addressDetail//详细地址
            mLocationArea?.let {
                mParams["area"] = it //区
            }
            mLocationCity?.let {
                mParams["city"] = it
            }
            mLocationProvince?.let {
                mParams["province"] = it
            }
            mLocationLatLng?.let {
                mParams["longitude"] = it.longitude.toString()//经度
                mParams["latitude"] = it.latitude.toString()//维度
            }
            LiveBus.getDefault().postEvent(
                Constants.EVENT_KEY_JOB_PUBLISH,
                Constants.TAG_PUBLISH_JOB_MESSAGE,
                mParams
            )
            mCallback?.jobMessageNextStep()
        }
        rlSelectSex.clickWithTrigger {
            activity?.let {
                SelectSexDialog.show(it) { sex, sexCode ->
                    mSex = sexCode
                    tvSex.text = sex
                }
            }
        }
        //兼职标题
        tvJopTitleContent.addTextChangedListener {
            afterTextChanged {
                val content = it?.toString()?.trim()
                tvJopTitleCount.text = "${content?.length}/20"
            }
        }
        //兼职内容
        tvJopDescriptionContent.addTextChangedListener {
            afterTextChanged {
                val content = it?.toString()?.trim()
                tvJopDescriptionCount.text = "${content?.length}/2000"
            }
        }
        //点击定位
        llLocationArea.clickWithTrigger {
            val intent = Intent(mContext, MapActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SELECT_AREA)
        }
        //判断下一步是否可以被点击
        tvJopTitleContent.addTextChangedListener {
            afterTextChanged {
                isNextStepStates()
            }
        }
        tvJopDescriptionContent.addTextChangedListener {
            afterTextChanged {
                isNextStepStates()
            }
        }
        etWorkPeopleNum.addTextChangedListener {
            afterTextChanged {
                isNextStepStates()
            }
        }
        etDetailAddress.addTextChangedListener {
            afterTextChanged {
                isNextStepStates()
            }
        }
        initStatus()
    }

    private fun initStatus() {
        val model = (activity as JobPublishActivity).mJobDetailsModel
        model?.let {
            tvJopTitleContent.setText(it.title)
            tvJopDescriptionContent.setText(it.content)
            tvSex.text = it.gender
            etWorkPeopleNum.setText(it.recruitNum.toString())
            tvLocationArea.text = it.address
            etDetailAddress.setText(it.addressDetail)
            tvNextStep.isEnabled = true
        }
    }

    private fun isNextStepStates() {
        val isCanClick = tvLocationArea.text.isNotEmpty() &&
                etDetailAddress.text.isNotEmpty() &&
                tvNextStep.text.isNotEmpty() &&
                tvJopTitleContent.text.isNotEmpty() &&
                (tvJopDescriptionContent.text.toString().length >= 20)
        if (isCanClick) {
            tvNextStep.setBackgroundResource(R.drawable.shape_button_select)
        } else {
            tvNextStep.setBackgroundResource(R.drawable.shape_button_default)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SELECT_AREA -> {
                data?.let {
                    tvLocationArea.text = it.getStringExtra("name")
                    mLocationCity = it.getStringExtra("city")
                    mLocationArea = it.getStringExtra("area")
                    mLocationAddress = it.getStringExtra("address")
                    mLocationProvince = it.getStringExtra("province")
                    mLocationLatLng = it.getParcelableExtra("latLng")
                    isNextStepStates()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val REQUEST_CODE_SELECT_AREA = 0x01
    }
}