package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.model.LatLng
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.ui.attestation.view.CompanyAttestationActivity
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
    private var mLocationCity: String? = null
    private var mLocationArea: String? = null
    private var mSex: Int = 2

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_message

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        tvNextStep.clickWithTrigger {
            val params = mutableMapOf<String, String>()
            params["title"] = tvJopTitleContent.text.toString().trim() //兼职标题
            params["content"] = tvJopDescriptionContent.text.toString().trim()//兼职描述
            params["gender"] = mSex.toString()
            params["recruitNum"] = etWorkPeopleNum.text.toString().trim()//招聘人数
            params["area"] = tvLocationArea.text.toString().trim() //定位地点
//            params["areaCode"] = "$mLocationCity,$mLocationArea"
            params["address"] = etDetailAddress.text.toString().trim()//详细地址
            params["longitude"] = mLocationLatLng?.longitude.toString()//经度
            params["latitude"] = mLocationLatLng?.latitude.toString()//维度
            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_JOB_PUBLISH, Constants.TAG_PUBLISH_JOB_MESSAGE, params)
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
                    mLocationLatLng = it.getParcelableExtra("latLng")
                    LogUtils.i("选择地区$mLocationLatLng")
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