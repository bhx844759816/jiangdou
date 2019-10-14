package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.baidu.mapapi.model.LatLng
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.event.LiveBus
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
        initStatus()
        tvNextStep.clickWithTrigger {
            mParams["title"] = tvJopTitleContent.text.toString().trim() //兼职标题
            mParams["content"] = tvJopDescriptionContent.text.toString().trim()//兼职描述
            mParams["gender"] = mSex.toString()
            mParams["recruitNum"] = etWorkPeopleNum.text.toString().trim()//招聘人数
            mParams["address"] = tvLocationArea.text.toString().trim() //定位地点
            mParams["addressDetail"] = etDetailAddress.text.toString().trim()//详细地址
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
        tvLocationArea.clickWithTrigger {
            val intent = Intent(mContext, MapActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SELECT_AREA)
        }
        //判断下一步是否可以被点击
        tvNextStep.isEnable(tvJopTitleContent) { isNetStepState() }
        tvNextStep.isEnable(tvJopDescriptionContent) { isNetStepState() }
        tvNextStep.isEnable(etWorkPeopleNum) { isNetStepState() }
        tvNextStep.isEnable(etDetailAddress) { isNetStepState() }
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
        }
    }

    private fun isNetStepState(): Boolean {
        return tvLocationArea.text.isNotEmpty() &&
                etDetailAddress.text.isNotEmpty() &&
                tvNextStep.text.isNotEmpty() &&
                tvJopTitleContent.text.isNotEmpty() &&
                (tvJopDescriptionContent.text.toString().length >= 20)
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
                    tvNextStep.isEnabled = isNetStepState()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val REQUEST_CODE_SELECT_AREA = 0x01
    }
}