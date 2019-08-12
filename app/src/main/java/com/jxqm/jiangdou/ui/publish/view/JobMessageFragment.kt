package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bhx.common.base.BaseLazyFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.SelectSexDialog
import kotlinx.android.synthetic.main.fragment_job_message.*

/**
 * 发布工作的消息信息
 * Created By bhx On 2019/8/8 0008 11:38
 */
class JobMessageFragment : BaseLazyFragment() {

    private var mCallback: OnJobPublishCallBack? = null
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
            mCallback?.jobMessageNextStep()
        }

        rlSelectSex.clickWithTrigger {
            activity?.let {
                SelectSexDialog.show(it)
            }
        }
    }
}