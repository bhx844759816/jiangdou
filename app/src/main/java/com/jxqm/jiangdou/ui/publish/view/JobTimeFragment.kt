package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.calendar.CalendarRangeSelectDialog
import kotlinx.android.synthetic.main.fragment_job_work_time.*

/**
 * 工作时间以及工作福利的界面
 * Created By bhx On 2019/8/8 0008 14:20
 */
class JobTimeFragment : BaseLazyFragment() {

    private var mCallback: OnJobPublishCallBack? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_work_time

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        tvNextStep.clickWithTrigger {
            mCallback?.jobTimeNextStep()
        }
        tvSelectDate.clickWithTrigger {
            if (activity is FragmentActivity) {
                LogUtils.i("弹出日期选择对话框")
                CalendarRangeSelectDialog.show(activity!!)
            }
        }
    }
}