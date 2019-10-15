package com.jxqm.jiangdou.ui.publish.view

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.ToastUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.order.view.OrderPaymentActivity
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import com.jxqm.jiangdou.ui.publish.vm.JobPublishViewModel
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_publish.*
import java.io.File
import com.jxqm.jiangdou.ui.publish.view.PublishJobPreviewActivity as PublishJobPreviewActivity

/**
 * 发布兼职的界面
 * Created By bhx On 2019/8/8 0008 09:00
 */
class JobPublishActivity : BaseDataActivity<JobPublishViewModel>(), OnJobPublishCallBack {
    private var mJobTypeFragment: JobTypeFragment? = null
    private var mJobMessageFragment: JobMessageFragment? = null
    private var mJobTimeFragment: JobTimeFragment? = null
    private var mJobContactsFragment: JobContactsFragment? = null
    private var mCurrentFragment: Fragment? = null
    var mAttestationStatusModel: AttestationStatusModel? = null
    var mJobDetailsModel: JobDetailsModel? = null
    private val mParamsMap = mutableMapOf<String, String>()
    private val gson = Gson()

    override fun getLayoutId(): Int = R.layout.activity_publish

    override fun getEventKey(): Any = Constants.EVENT_KEY_JOB_PUBLISH

    override fun initView() {
        super.initView()
        val jsonString = intent.getStringExtra("JobDetails")
        jsonString?.let {
            mJobDetailsModel = CommonConfig.fromJson(it, JobDetailsModel::class.java)
        }
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        showSelectJobTypeFragment()
    }

    override fun dataObserver() {
        //接收选择的兼职类型
        registerObserver(Constants.TAG_PUBLISH_JOB_TYPE, JobTypeModel::class.java).observe(
            this,
            Observer {
                mParamsMap["jobTypeId"] = it.id.toString()
                mParamsMap["jobTypeName"] = it.jobTypeName
            })
        //接收兼职的基本信息
        registerObserver(Constants.TAG_PUBLISH_JOB_MESSAGE, Map::class.java).observe(
            this,
            Observer {
                val params = it as Map<String, String>
                mParamsMap.putAll(params)
            })
        //接收兼职的时间
        registerObserver(Constants.TAG_PUBLISH_JOB_TIME, Map::class.java).observe(this, Observer {
            val params = it as Map<String, String>
            mParamsMap.putAll(params)
        })
        //立即发布
        registerObserver(Constants.TAG_PUBLISH_JOB_EMPLOYER_PUBLISH, Map::class.java).observe(
            this,
            Observer {
                val params = it as Map<String, String>
                mParamsMap.putAll(params)
                val mapFilePath = Constants.APP_SAVE_DIR + Constants.MAPVIEW_FILENAME
                if(mJobDetailsModel == null){
                    mViewModel.publishJob(mapFilePath, mParamsMap)
                }else{
                    mParamsMap["id"]=mJobDetailsModel!!.id.toString()
                    mViewModel.updatePublishJob(mapFilePath, mParamsMap)
                }
            })
        //预览建立
        registerObserver(Constants.TAG_PUBLISH_JOB_EMPLOYER_PREVIEW, Map::class.java).observe(
            this,
            Observer {
                val params = it as Map<String, String>
                mParamsMap.putAll(params)
                startActivity<PublishJobPreviewActivity>("JobDetailsModel" to gson.toJson(mParamsMap))

            })
        //获取认证信息
        registerObserver(
            Constants.TAG_PUBLISH_JOB_ATTESTATION_DETAILS,
            AttestationStatusModel::class.java
        ).observe(this,
            Observer {
                mAttestationStatusModel = it
            })
        //发布成功 TAG_PUBLISH_JOB_SUCCESS
        registerObserver(Constants.TAG_PUBLISH_JOB_SUCCESS, String::class.java).observe(
            this,
            Observer {
                val mapFilePath = Constants.APP_SAVE_DIR + Constants.MAPVIEW_FILENAME
                val mapFile = File(mapFilePath)
                mapFile.deleteOnExit()
                //发布职位成功刷新列表
                LiveBus.getDefault().postEvent(
                    Constants.EVENT_KEY_WAIT_PUBLISH_JOB,
                    Constants.TAG_WAIT_PUBLISH_REFRESH_JOB_LIST, true
                )
                //需要jobId
                ToastUtils.toastShort("发布职位成功请支付押金")
                startActivity<OrderPaymentActivity>("JobId" to it)
                this@JobPublishActivity.finish()
            })
    }

    /**
     * 展示选择工作类别
     */
    private fun showSelectJobTypeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobTypeFragment == null) {
            mJobTypeFragment = JobTypeFragment()
        }
        hideAllFragment(transaction)
        if (!mJobTypeFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobTypeFragment!!)
        } else {
            transaction.show(mJobTypeFragment!!)
        }
        mCurrentFragment = mJobTypeFragment
        transaction.commit()
    }

    /**
     * 展示选择工作详情的界面
     */
    private fun showSelectJobMessageFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobMessageFragment == null) {
            mJobMessageFragment = JobMessageFragment()
        }
        hideAllFragment(transaction)
        if (!mJobMessageFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobMessageFragment!!)
        } else {
            transaction.show(mJobMessageFragment!!)
        }
        mCurrentFragment = mJobMessageFragment
        transaction.commit()
    }

    /**
     * 展示时间选着界面
     */
    private fun showSelectJobTimeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobTimeFragment == null) {
            mJobTimeFragment = JobTimeFragment()
        }
        hideAllFragment(transaction)
        if (!mJobTimeFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobTimeFragment!!)
        } else {
            transaction.show(mJobTimeFragment!!)
        }
        mCurrentFragment = mJobTimeFragment
        transaction.commit()
    }

    /**
     * 展示联系人界面
     */
    private fun showSelectJobContactsFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobContactsFragment == null) {
            mJobContactsFragment = JobContactsFragment()
        }
        hideAllFragment(transaction)
        if (!mJobContactsFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobContactsFragment!!)
        } else {
            transaction.show(mJobContactsFragment!!)
        }
        mCurrentFragment = mJobContactsFragment
        transaction.commit()
    }

    private fun hideAllFragment(transaction: FragmentTransaction) {
        mJobTypeFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
        mJobMessageFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
        mJobTimeFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
        mJobContactsFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
    }

    override fun jobTypNextStep() {
        showSelectJobMessageFragment()
    }

    override fun jobMessageNextStep() {
        showSelectJobTimeFragment()
    }

    override fun jobTimeNextStep() {
        showSelectJobContactsFragment()
    }

    override fun jobContactsNextStep() {
        ToastUtils.toastShort("发布成功")
        finish()

    }

    override fun onBackPressed() {
        if (mCurrentFragment is JobTypeFragment) {
            super.onBackPressed()
        } else {
            when (mCurrentFragment) {
                is JobMessageFragment -> {
                    showSelectJobTypeFragment()
                }
                is JobTimeFragment -> {
                    showSelectJobMessageFragment()
                }
                is JobContactsFragment -> {
                    showSelectJobTimeFragment()
                }
            }
        }
    }

}