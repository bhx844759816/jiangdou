package com.jxqm.jiangdou.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.UserModel
import com.jxqm.jiangdou.ui.attestation.view.CompanyAttestationActivity
import com.jxqm.jiangdou.ui.home.vm.MyViewModel
import com.jxqm.jiangdou.ui.login.view.LoginActivity
import com.jxqm.jiangdou.ui.user.view.*
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.MyServiceDialog
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * 我的界面
 * Created by Administrator on 2019/8/20.
 */
class MyFragment : BaseMVVMFragment<MyViewModel>() {
    private var mUserModel: UserModel? = null
    override fun getLayoutId(): Int = R.layout.fragment_my

    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_MY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserModel = MyApplication.instance().userModel
        mUserModel?.let {
            tvUserName.text = it.username
            tvRankPoints.text = it.rankPoints
            tvBalance.text = it.balance
            Glide.with(mContext).load(Api.HTTP_BASE_URL + it.avatar).into(ivHeadPhoto)
        }
        //用户名称
        tvUserName.clickWithTrigger {
            startActivity<LoginActivity>()
        }
        //用户简历
        ivUserResume.clickWithTrigger {
            startActivity<MyResumeActivity>()
        }
        //我的收藏
        rlMyCollection.clickWithTrigger {
            startActivity<MyCollectionJobActivity>()
        }
        //企业认证
        rlCompanyAttestation.clickWithTrigger {
            startActivity<CompanyAttestationActivity>()
        }
        //客服
        rlMyService.clickWithTrigger {
            activity?.let { activity -> MyServiceDialog.show(activity) }
        }
        //提现
        llMyMoney.clickWithTrigger {
            startActivity<UserWalletActivity>()
        }
        //
        ivSetting.clickWithTrigger {
            startActivity<SettingActivity>()
        }
        rlAboutUs.clickWithTrigger {
            startActivity<AboutUsActivity>()
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        registerObserver(Constants.TAG_MAIN_MY_LOGIN_SUCCESS, UserModel::class.java).observe(this, Observer {
            tvUserName.text = it.username
        })
    }


}