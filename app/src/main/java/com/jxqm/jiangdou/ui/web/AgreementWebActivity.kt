package com.jxqm.jiangdou.ui.web

import android.widget.LinearLayout
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.ui.web.vm.WebViewModel
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_agreement_web.*


class AgreementWebActivity : BaseDataActivity<WebViewModel>() {
    private val mUrlList = arrayListOf(
        "/html/advance-deposit.html",
        "/html/job-release.html",
        "/html/privacy-policy.html",
        "/html/user-agreement.html"
    )

    override fun getLayoutId(): Int = R.layout.activity_agreement_web
    override fun getEventKey(): Any = Constants.EVENT_AGREEMENT_WEB

    override fun initView() {
        super.initView()
        val url = when (intent.getIntExtra("Status", TAG_DEPOSIT_AGREEMENT)) {
            TAG_DEPOSIT_AGREEMENT -> {
                tvTitle.text = "预付押金协议"
                Api.HTTP_BASE_URL + mUrlList[0]
            }
            TAG_PUBLISH_JOB_AGREEMENT -> {
                tvTitle.text = "职位信息发布协议"
                Api.HTTP_BASE_URL + mUrlList[1]
            }
            TAG_USER_PRIVACY_AGREEMENT -> {
                tvTitle.text = "隐私协议"
                Api.HTTP_BASE_URL + mUrlList[2]
            }
            TAG_USER_AGREEMENT -> {
                tvTitle.text = "用户协议"
                Api.HTTP_BASE_URL + mUrlList[3]
            }
            else -> {
                Api.HTTP_BASE_URL + mUrlList[0]
            }
        }
        AgentWeb.with(this)
            .setAgentWebParent(llWebParent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }


    companion object {
        const val TAG_DEPOSIT_AGREEMENT = 0x01
        const val TAG_PUBLISH_JOB_AGREEMENT = 0x02
        const val TAG_USER_PRIVACY_AGREEMENT = 0x03
        const val TAG_USER_AGREEMENT = 0x04
    }
}