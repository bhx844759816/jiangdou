package com.jxqm.jiangdou.ui.user.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bhx.common.base.BaseActivity
import com.bhx.common.http.upload.FileParams
import com.bhx.common.utils.ToastUtils
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.user.vm.ComplainDetailsViewModel
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_complain_details.*
import java.io.File

/**
 * 投诉详情界面
 * Created By bhx On 2019/8/20 0020 14:51
 */
class ComplainDetailsActivity : BaseDataActivity<ComplainDetailsViewModel>() {
    private lateinit var mFragment: Fragment
    private var mJobId: String? = null
    private var mComplaintTypeCode = -1
    override fun getLayoutId(): Int = R.layout.activity_complain_details
    override fun getEventKey(): Any = Constants.EVENT_COMPLAIN_DETAILS

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        mJobId = intent.getStringExtra("jobId")
        when (intent.getIntExtra("ComplainType", -1)) {
            0 -> {
                mFragment = ComplainChargeTypeFragment()
                tvTitle.text = "收取费用投诉"
                mComplaintTypeCode = 3
            }
            1 -> {
                mFragment = ComplainMoneyTypeFragment()
                tvTitle.text = "工资拖欠投诉"
                mComplaintTypeCode = 4
            }
            2 -> {
                mFragment = ComplainTimeoutTypeFragment()
                tvTitle.text = "放鸽子投诉"
                mComplaintTypeCode = 1

            }
            3 -> {
                mFragment = ComplainFalseMessageTypeFragment()
                tvTitle.text = "信息虚假投诉"
                mComplaintTypeCode = 2
            }
            4 -> {
                mFragment = ComplainOthersTypeFragment()
                tvTitle.text = "其他投诉"
                mComplaintTypeCode = 0
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flFragmentParent, mFragment)
        transaction.commit()
        complainDetailsBack.clickWithTrigger {
            finish()
        }

    }

    override fun dataObserver() {
        registerObserver(Constants.TAG_JOB_COMPLAIN_SUCCESS, Boolean::class.java).observe(this,
            Observer {
                ToastUtils.toastShort("投诉成功")
                finish()
            })
    }

    /**
     * 提交投诉信息
     */
    fun submitComplain(params: MutableMap<String, Any>, files: List<File>) {
        params["jobId"] = mJobId!!
        params["complaintTypeCode"] = mComplaintTypeCode
        mViewModel.complaintJob(params, files)
    }
}