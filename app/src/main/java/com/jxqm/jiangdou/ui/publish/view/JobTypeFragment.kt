package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.ui.publish.vm.SelectJobTypeViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.MultipleRadioGroup
import kotlinx.android.synthetic.main.fragment_select_job_type.*

/**
 * 选择界面类型
 * Created By bhx On 2019/8/8 0008 09:09
 */
class JobTypeFragment : BaseMVVMFragment<SelectJobTypeViewModel>() {
    private var mCallback: OnJobPublishCallBack? = null
    private var mMoreJobModelList = mutableListOf<JobTypeModel>()
    private var mHotJobModelList = mutableListOf<JobTypeModel>()
    private var mSelectJobModel: JobTypeModel? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_select_job_type

    override fun getEventKey(): Any = Constants.EVENT_KEY_SELECT_JOB_TYPE

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        //点击下一步
        tvNextStep.clickWithTrigger {
            LiveBus.getDefault()
                .postEvent(Constants.EVENT_KEY_JOB_PUBLISH, Constants.TAG_PUBLISH_JOB_TYPE, mSelectJobModel)
            mCallback?.jobTypNextStep()
        }
        rgHotJobType.setOnCheckedChangeListener { p0, id ->
            selectJobTypeMode(id)
        }
        rgMoreJobType.setOnCheckedChangeListener { p0, id ->
            selectJobTypeMode(id)
        }
    }

    private fun selectJobTypeMode(id: Int) {
        tvNextStep.isEnabled = true
        mHotJobModelList.forEach {
            if (it.id == id) {
                mSelectJobModel = it
                return
            }
        }
        mMoreJobModelList.forEach {
            if (it.id == id) {
                mSelectJobModel= it
                return
            }
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mViewModel.getJobType()
        registerObserver(Constants.TAG_SELECT_JOB_TYPE_HOT, List::class.java).observe(this, Observer {
            val hotJobModelList = it as List<JobTypeModel>
            mHotJobModelList.clear()
            mHotJobModelList.addAll(hotJobModelList)
            addHotJobType()
        })
        registerObserver(Constants.TAG_SELECT_JOB_TYPE_MORE, List::class.java).observe(this, Observer {
            val moreJobModelList = it as List<JobTypeModel>
            mMoreJobModelList.clear()
            mMoreJobModelList.addAll(moreJobModelList)
            if (mMoreJobModelList.size > 6) {
                //裁剪5个
                addMoreJobType(mMoreJobModelList.subList(0, 5))
                addExpandJobType()
            } else {
                addMoreJobType(mMoreJobModelList)
            }
        })
    }

    /**
     * 添加兼职类型
     */
    private fun addHotJobType() {
        mHotJobModelList.forEach {
            val radioButton = LayoutInflater.from(mContext)
                .inflate(R.layout.view_publish_job_type_item, null) as RadioButton
            radioButton.text = it.jobTypeName
            radioButton.id = it.id
            radioButton.setPadding(DensityUtil.dip2px(mContext, 10f))
            rgHotJobType.addView(radioButton)
        }
    }

    /**
     * 添加更多类型
     */
    private fun addMoreJobType(list: List<JobTypeModel>) {
        list.forEach {
            val radioButton = LayoutInflater.from(mContext)
                .inflate(R.layout.view_publish_job_type_item, null) as RadioButton
            radioButton.text = it.jobTypeName
            radioButton.id = it.id
            radioButton.setPadding(DensityUtil.dip2px(mContext, 10f))
            rgMoreJobType.addView(radioButton)
        }
    }

    /**
     * 添加展开得View
     */
    private fun addExpandJobType() {
        val expandView = LayoutInflater.from(mContext)
            .inflate(R.layout.view_publish_job_expand_type_item, null) as RelativeLayout
        expandView.clickWithTrigger {
            rgMoreJobType.removeView(expandView)
            addMoreJobType(mMoreJobModelList.subList(6, mMoreJobModelList.size - 1))
        }
        rgMoreJobType.addView(expandView)
    }


}