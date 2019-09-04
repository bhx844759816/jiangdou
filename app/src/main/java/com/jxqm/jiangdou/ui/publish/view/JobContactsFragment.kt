package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bhx.common.base.BaseLazyFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_job_contacts.*

/**
 * Created By bhx On 2019/8/8 0008 15:40
 */
class JobContactsFragment : BaseLazyFragment() {

    private var mCallback: OnJobPublishCallBack? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_contacts

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        //发布兼职
        tvImmediatelyPublish.clickWithTrigger {
            mCallback?.jobContactsNextStep()
        }
        // 预览兼职
        tvPreviewPublish.clickWithTrigger {
            startActivity<PublishJobPreviewActivity>()
        }
    }
}