package com.jxqm.jiangdou.ui.job.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.bhx.common.utils.DensityUtil
import com.bhx.common.view.FlowLayout
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import kotlinx.android.synthetic.main.activity_job_details.*
import kotlinx.android.synthetic.main.activity_job_details.ivMapView
import kotlinx.android.synthetic.main.activity_job_details.llDateParent
import kotlinx.android.synthetic.main.activity_job_details.toolbar
import kotlinx.android.synthetic.main.activity_job_details.tvJobArea
import kotlinx.android.synthetic.main.activity_job_details.tvJobContent
import kotlinx.android.synthetic.main.activity_job_details.tvJobMoney
import kotlinx.android.synthetic.main.activity_job_details.tvJobTips
import kotlinx.android.synthetic.main.activity_job_details.tvJobTitle
import kotlinx.android.synthetic.main.activity_job_details.tvJobType
import kotlinx.android.synthetic.main.activity_job_details.tvRecruitPeoples

import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.ToastUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haibin.calendarview.Calendar
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.ui.job.vm.JobDetailsViewModel
import com.jxqm.jiangdou.ui.login.view.LoginActivity
import com.jxqm.jiangdou.ui.order.view.OrderPaymentActivity
import com.jxqm.jiangdou.ui.user.view.MyResumeActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.MapSelectDialog
import com.jxqm.jiangdou.view.dialog.PromptDialog


/**
 * 工作详情界面
 * Created By bhx On 2019/8/12 0012 10:56
 */
class JobDetailsActivity : BaseDataActivity<JobDetailsViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_JOB_DETAILS
    private var mAttestationStatusModel: AttestationStatusModel? = null
    private var tvSignUp: TextView? = null
    private var tvConsult: TextView? = null //咨询
    private var tvCollection: CheckBox? = null//收藏
    private var rlCollection: RelativeLayout? = null//收藏
    private val mGson = Gson()
    private var mJobDetailsModel: JobDetailsModel? = null

    override fun getLayoutId(): Int = R.layout.activity_job_details

    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        val jobId = intent.getStringExtra("JobId")
        val status = intent.getIntExtra("Status", STATUS_SINGUP) //标识是什么状态 （兼职详情界面,报名界面）
        initUIByStatus(status)
        jobId?.let {
            mViewModel.getJobDetails(it)
        }
        //返回
        toolbar.setNavigationOnClickListener {
            finish()
        }
        //跳转到公司详情
        rlCompanyDetails.clickWithTrigger {
            mAttestationStatusModel?.let {
                startActivity<CompanyDetailsActivity>("AttestationStatusModel" to it.toJson())
            }
        }
        llJobAddress.clickWithTrigger {
            mJobDetailsModel?.let {
                MapSelectDialog.show(
                    this,
                    it.latitude.toString(),
                    it.longitude.toString(),
                    it.address
                )
            }
        }
    }

    private fun initUIByStatus(status: Int) {
        when (status) {
            STATUS_SINGUP -> { //报名
                val parent = vsSignUpJob.inflate() as LinearLayout
                tvSignUp = parent.findViewById(R.id.tvSignUp)
                tvConsult = parent.findViewById(R.id.tvConsult)
                tvCollection = parent.findViewById(R.id.tvCollection)
                rlCollection = parent.findViewById(R.id.rlCollection)
                tvSignUp?.clickWithTrigger {
                    if (MyApplication.instance().userModel == null) {
                        startActivity<LoginActivity>()
                        return@clickWithTrigger
                    }
                    mViewModel.signUpJob(mJobDetailsModel!!.id.toString())
                }
                //点击收藏
                rlCollection?.clickWithTrigger {
                    if (MyApplication.instance().userModel == null) {
                        startActivity<LoginActivity>()
                        tvCollection?.isChecked = tvCollection?.isChecked ?: true
                        return@clickWithTrigger
                    }
                    val isCollection = tvCollection?.isChecked ?: true
                    if (isCollection) {
                        mViewModel.cancelCollectionJob(mJobDetailsModel!!.id.toString())
                    } else {
                        mViewModel.collectionJob(mJobDetailsModel!!.id.toString())
                    }
                }

            }
            STATUS_PAY_DEPOSIT -> {//支付押金
                val parent = vsPayDepositJob.inflate() as LinearLayout
                val tvCancelPublish = parent.findViewById<TextView>(R.id.tvCancelPublish)
                val tvPayMoney = parent.findViewById<TextView>(R.id.tvPayMoney)
                tvPayMoney.clickWithTrigger {
                    //支付押金
                    startActivity<OrderPaymentActivity>("JobId" to mJobDetailsModel!!.id.toString())
                }
                tvCancelPublish.clickWithTrigger {
                    //取消发布
                    PromptDialog.show(this, "确认删除发布的职位吗？") {
                        //删除发布的职位
                        mViewModel.deletePublishJob(mJobDetailsModel!!.id.toString())
                    }
                }
            }
        }
    }

    override fun dataObserver() {
        //获取工作详情
        registerObserver(
            Constants.TAG_GET_JOB_DETAILS_SUCCESS,
            JobDetailsModel::class.java
        ).observe(this, Observer {
            mJobDetailsModel = it
            initJobDetails()
        })
        //获取企业详情
        registerObserver(
            Constants.TAG_GET_EMPLOYER_DETAILS_SUCCESS,
            AttestationStatusModel::class.java
        ).observe(this,
            Observer {
                mAttestationStatusModel = it
                joPublishCompanyName.text = it.employerName //企业名称
                joPublishCompanyUserName.text = it.contact //联系人姓名
                Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.logo)
                    .into(ivCompanyLogo)
            })
        //删除职位成功
        registerObserver(Constants.TAG_DELETE_WAIT_PUBLISH_JOB_SUCCESS, String::class.java).observe(
            this,
            Observer {
                //发布职位成功刷新列表
                LiveBus.getDefault().postEvent(
                    Constants.EVENT_KEY_WAIT_PUBLISH_JOB,
                    Constants.TAG_WAIT_PUBLISH_REFRESH_JOB_LIST, true
                )
                this.finish()
            })
        //报名成功
        registerObserver(Constants.TAG_SIGN_UP_JOB_SUCCESS, Boolean::class.java).observe(
            this,
            Observer {
                startActivity<JobSingUpSuccessActivity>()
            })
        //简历不存在
        registerObserver(Constants.TAG_SIGN_UP_RESUME_NOT_EXIST, Boolean::class.java).observe(
            this,
            Observer {
                startActivity<MyResumeActivity>()
            })
        //收藏返回
        registerObserver(Constants.TAG_COLLECTION_STATUS_CHANGE, Boolean::class.java).observe(this,
            Observer {
                tvCollection?.text = if (it) "已收藏" else "收藏"
                tvCollection?.isChecked = it
            })
    }

    private fun initJobDetails() {
        mJobDetailsModel?.let {
            tvJobType.text = it.jobTypeName
            tvJobMoney.text = "${it.salary} 币/小时"
            tvRecruitPeoples.text = "招${it.recruitNum}人"
            tvJobSex.text = it.gender
            tvJobTitle.text = it.title
            tvJobContent.text = it.content
            tvJobArea.text = it.address
            tvJobTips.text = "${it.area} | 日结"
            if (it.sign) {
                tvSignUp?.text = "已报名(${it.signNum}人报名)"
            } else {
                tvSignUp?.text = "我要报名(${it.signNum}人报名)"
            }
            tvSignUp?.isEnabled = !it.sign
            tvCollection?.text = if (it.isCollection) "已收藏" else "收藏"
            tvCollection?.isChecked = it.isCollection
            val listDates =
                mGson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {
                }.type)
            val listTimes =
                mGson.run {
                    fromJson<List<TimeRangeModel>>(
                        it.timesJson,
                        object : TypeToken<List<TimeRangeModel>>() {
                        }.type
                    )
                }
            if (listDates.isNotEmpty() && listTimes.isNotEmpty()) {
                showDateRange(listDates, listTimes)
            }
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.mapImg).into(ivMapView)
        }
    }

    private fun showDateRange(dates: List<String>, times: List<TimeRangeModel>) {
        val calendarList = mutableListOf<Calendar>()
        val rangeCalendarList = mutableListOf<MutableList<Calendar>>()
        dates.forEach { date ->
            val calendar = Calendar()
            val endDates = date.split("-")
            calendar.year = endDates[0].toInt()
            calendar.month = endDates[1].toInt()
            calendar.day = endDates[2].toInt()
            calendarList.add(calendar)
        }
        calendarList.forEachIndexed { index, calendar ->
            if (index == 0) {
                val list = mutableListOf(calendar)
                rangeCalendarList.add(list)
                return@forEachIndexed
            }
            if (index == calendarList.size - 1) {
                val list = rangeCalendarList.last()
                list.add(calendar)
                return@forEachIndexed
            }
            val lastCalendar = calendarList[index - 1]
            val lastTimeMillis = lastCalendar.timeInMillis + 24 * 60 * 60 * 1000
            val curTimeMillis = calendar.timeInMillis
            Log.i("TAG2", "$curTimeMillis")
            Log.i("TAG2", "$lastTimeMillis")
            Log.i("TAG2", "${curTimeMillis == lastTimeMillis}")
            if ((curTimeMillis - lastTimeMillis) <= 1000L) {
                val list = rangeCalendarList.last()
                list.add(calendar)
            } else {
                val list = mutableListOf(calendar)
                rangeCalendarList.add(list)
            }
        }
        rangeCalendarList.forEach {
            var startData = ""
            var endData = ""
            if (it.size == 1) {
                startData = StringBuffer().append(it.first().year)
                    .append(" - ")
                    .append(it.first().month)
                    .append(" - ")
                    .append(it.first().day).toString()
                endData = startData
            }
            if (it.size > 1) {
                startData = StringBuffer().append(it.first().year)
                    .append(" - ")
                    .append(it.first().month)
                    .append(" - ")
                    .append(it.first().day).toString()
                endData = StringBuffer().append(it.last().year)
                    .append(" - ")
                    .append(it.last().month)
                    .append(" - ")
                    .append(it.last().day).toString()
            }
            val view = LayoutInflater.from(this)
                .inflate(R.layout.view_job_details_data_range, null)
            val tvStartDate = view.findViewById<TextView>(R.id.tvStartDate)
            val tvEndDate = view.findViewById<TextView>(R.id.tvEndDate)
            val flTimeRangeParent = view.findViewById<FlowLayout>(R.id.flTimeRangeParent)
            tvStartDate.text = startData
            tvEndDate.text = endData
            times.forEach { timeRangeModel ->
                addTimeRange(flTimeRangeParent, timeRangeModel)
            }
            llDateParent.addView(view)
        }
    }

    private fun addTimeRange(flowLayout: FlowLayout, timeRange: TimeRangeModel) {
        val textView = TextView(this)
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        layoutParams.rightMargin = DensityUtil.dip2px(this, 10f)
        layoutParams.bottomMargin = DensityUtil.dip2px(this, 5f)
        textView.setTextColor(resources.getColor(R.color.text_hint))
        textView.textSize = DensityUtil.dip2px(this, 5f).toFloat()
        textView.text = "${timeRange.start}-${timeRange.end}"
        textView.layoutParams = layoutParams
        flowLayout.addView(textView)
    }

    companion object {
        const val STATUS_SINGUP = 0x01 //报名
        const val STATUS_PAY_DEPOSIT = 0x02 //支付押金
    }
}