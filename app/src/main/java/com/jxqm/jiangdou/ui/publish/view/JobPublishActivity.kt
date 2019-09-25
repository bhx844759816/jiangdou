package com.jxqm.jiangdou.ui.publish.view

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.bhx.common.utils.ToastUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.ui.attestation.model.AttestationStatusModel
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import com.jxqm.jiangdou.ui.publish.vm.JobPublishViewModel
import kotlinx.android.synthetic.main.activity_publish.*

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
    var mJobDetailsModel: JobDetailsModel = JobDetailsModel()
    private val gson = Gson()

    override fun getLayoutId(): Int = R.layout.activity_publish

    override fun getEventKey(): Any = Constants.EVENT_KEY_JOB_PUBLISH

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        showSelectJobTypeFragment()
    }

    override fun dataObserver() {
        //接收选择的兼职类型
        registerObserver(Constants.TAG_PUBLISH_JOB_TYPE, JobTypeModel::class.java).observe(this, Observer {
            mJobDetailsModel.jobTypeId = it.id.toString()
            mJobDetailsModel.jobTypeValue = it.jobTypeName
        })
        //接收兼职的基本信息
        registerObserver(Constants.TAG_PUBLISH_JOB_MESSAGE, Map::class.java).observe(this, Observer {
            val params = it as Map<String, String>
            mJobDetailsModel.title = params.getValue("title")
            mJobDetailsModel.content = params.getValue("content")
            mJobDetailsModel.gender = params.getValue("gender")
            mJobDetailsModel.recruitNum = params.getValue("recruitNum")
            mJobDetailsModel.area = params.getValue("area")
//            mJobDetailsModel.areaCode = params.getValue("areaCode")
            mJobDetailsModel.address = params.getValue("address")
            mJobDetailsModel.longitude = params.getValue("longitude")
            mJobDetailsModel.latitude = params.getValue("latitude")
        })
        //接收兼职的时间
        registerObserver(Constants.TAG_PUBLISH_JOB_TIME, Map::class.java).observe(this, Observer {
            val params = it as Map<String, String>
            mJobDetailsModel.salary = params.getValue("salary")
            mJobDetailsModel.times = gson.fromJson(
                params.getValue("times")
                , object : TypeToken<List<TimeRangeModel>>() {
                }.type
            )
            mJobDetailsModel.dates = gson.fromJson(
                params.getValue("dates")
                , object : TypeToken<List<String>>() {
                }.type
            )
        })
        //立即发布
        registerObserver(Constants.TAG_PUBLISH_JOB_EMPLOYER_PUBLISH, Map::class.java).observe(this, Observer {
            val params = it as Map<String, String>
            mJobDetailsModel.contact = params.getValue("contact")
            mJobDetailsModel.tel = params.getValue("tel")
            mJobDetailsModel.email = params.getValue("email")
            mJobDetailsModel.status = "0"
            val mapFilePath = Constants.APP_SAVE_DIR + Constants.MAPVIEW_FILENAME
            mViewModel.publishJob(mapFilePath, mJobDetailsModel)
        })
        //预览建立
        registerObserver(Constants.TAG_PUBLISH_JOB_EMPLOYER_PREVIEW, Map::class.java).observe(this, Observer {
            val params = it as Map<String, String>
            mJobDetailsModel.contact = params.getValue("contact")
            mJobDetailsModel.tel = params.getValue("tel")
            mJobDetailsModel.email = params.getValue("email")

            val intent = Intent(
                this@JobPublishActivity,
                PublishJobPreviewActivity::class.java
            )
            intent.putExtra("JobDetailsModel", mJobDetailsModel.toJson())
            startActivity(intent)
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
        registerObserver(Constants.TAG_PUBLISH_JOB_SUCCESS, Boolean::class.java).observe(this, Observer {
            //需要jobId

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