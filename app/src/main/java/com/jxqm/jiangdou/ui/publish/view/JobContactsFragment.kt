package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.PhoneUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.fragment_job_contacts.*

/**
 * Created By bhx On 2019/8/8 0008 15:40
 */
class JobContactsFragment : BaseLazyFragment() {

    private var mCallback: OnJobPublishCallBack? = null
    private var mAttestationStatusModel: AttestationStatusModel? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_contacts

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        initStatus()
        //发布兼职
        tvImmediatelyPublish.clickWithTrigger {
            LiveBus.getDefault().postEvent(
                Constants.EVENT_KEY_JOB_PUBLISH,
                Constants.TAG_PUBLISH_JOB_EMPLOYER_PUBLISH, createParams()
            )
        }
        // 预览兼职
        tvPreviewPublish.clickWithTrigger {
            LiveBus.getDefault().postEvent(
                Constants.EVENT_KEY_JOB_PUBLISH,
                Constants.TAG_PUBLISH_JOB_EMPLOYER_PREVIEW, createParams()
            )
        }
        tvImmediatelyPublish.isEnable(etContacts) { isPublishState() }
        tvImmediatelyPublish.isEnable(etContactsPhone) { isPublishState() }
        tvImmediatelyPublish.isEnable(etContactsEmail) { isPublishState() }

    }

    /**
     * 初始化状态
     */
    private fun initStatus() {
        val model = (activity as JobPublishActivity).mJobDetailsModel
        if (model == null) {
            //获取企业认证信息
            mAttestationStatusModel = MyApplication.instance().attestationViewModel
            mAttestationStatusModel?.let {
                etContacts.setText(it.contact)
                etContactsPhone.setText(it.tel)
            }
        } else {
            etContacts.setText(model.contact)
            etContactsPhone.setText(model.tel)
            etContactsEmail.setText(model.email)
            tvImmediatelyPublish.isEnabled = isPublishState()
        }
    }

    private fun createParams(): MutableMap<String, String> {
        val params = mutableMapOf<String, String>()
        params["contact"] = etContacts.text.toString().trim()
        params["tel"] = etContactsPhone.text.toString().trim()
        params["email"] = etContactsEmail.text.toString().trim()
        return params
    }

    private fun isPublishState(): Boolean {
        return etContacts.text.isNotEmpty() &&
                PhoneUtils.isMobile(etContactsPhone.text.toString().trim()) &&
                etContactsEmail.text.isNotEmpty()
    }
}