package com.jxqm.jiangdou.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.ToastUtils
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
import com.jxqm.jiangdou.utils.GlideCircleTransform
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
        initUserStatus()
        //用户名称
        tvUserName.clickWithTrigger {
            if (MyApplication.instance().userModel == null) {
                startActivity<LoginActivity>()
                return@clickWithTrigger
            } else {
                startActivity<ModifyUserDetailsActivity>()
            }
        }
        //用户简历
        ivUserResume.clickWithTrigger {
            if (MyApplication.instance().userModel == null) {
                ToastUtils.toastShort("请先登陆")
                return@clickWithTrigger
            }
            startActivity<MyResumeActivity>()
        }
        //我的收藏
        rlMyCollection.clickWithTrigger {
            if (MyApplication.instance().userModel == null) {
                ToastUtils.toastShort("请先登陆")
                return@clickWithTrigger
            }
            startActivity<MyCollectionJobActivity>()
        }
        //企业认证
        rlCompanyAttestation.clickWithTrigger {
            if (MyApplication.instance().userModel == null) {
                ToastUtils.toastShort("请先登陆")
                return@clickWithTrigger
            }
            startActivity<CompanyAttestationActivity>()
        }
        //我的消息
        rlMyMessage.clickWithTrigger {
            if (MyApplication.instance().userModel == null) {
                ToastUtils.toastShort("请先登陆")
                return@clickWithTrigger
            }
            startActivity<MyMessageActivity>()
        }
        ivHeadPhoto.clickWithTrigger {
            if (MyApplication.instance().userModel == null) {
                ToastUtils.toastShort("请先登陆")
                return@clickWithTrigger
            }
            startActivity<ModifyUserDetailsActivity>()
        }
        //客服
        rlMyService.clickWithTrigger {
            activity?.let { activity -> MyServiceDialog.show(activity) }
        }
        //提现
        llMyMoney.clickWithTrigger {
            if (MyApplication.instance().userModel == null) {
                ToastUtils.toastShort("请先登陆")
                return@clickWithTrigger
            }
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
        registerObserver(Constants.TAG_MAIN_MY_LOGIN_SUCCESS, Boolean::class.java).observe(
            this,
            Observer {
                mUserModel = MyApplication.instance().userModel
                initUserStatus()
            })
        registerObserver(
            Constants.TAG_GET_ACCOUNT_BALANCE_SUCCESS,
            String::class.java
        ).observe(this,
            Observer {
                tvBalance.text = it
            })
    }

    /**
     * 设置用户信息展示
     */
    private fun initUserStatus() {
        if (mUserModel != null) {
            mUserModel?.let {
                tvUserName.text = it.nick
                tvRankPoints.text = it.rankPoints
                tvBalance.text = it.balance
                if (it.genderCode == 2) {
                    ivUserSex.visibility = View.GONE
                } else {
                    ivUserSex.visibility = View.VISIBLE
                    if (it.genderCode == 0) {
                        ivUserSex.setBackgroundResource(R.drawable.icon_girl)
                    } else {
                        ivUserSex.setBackgroundResource(R.drawable.icon_boy)
                    }
                }
                tvResumeDescribe.text = "完善度${it.perfectionDegree}%\n简历越完善，录用率越高哦～"
                Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + it.avatar)
                    .transform(
                        GlideCircleTransform(mContext)
                    )
                    .error(R.drawable.icon_default_head_photo)
                    .placeholder(R.drawable.icon_default_head_photo).into(ivHeadPhoto)
            }
        } else {
            tvUserName.text = "立即登录"
            ivUserSex.visibility = View.GONE
            tvResumeDescribe.text = "登陆后才可以填写简历哦"
            ivHeadPhoto.setImageResource(R.drawable.icon_head_photo)
            tvRankPoints.text = "0"
            tvBalance.text = "0"
        }

    }

    override fun onResume() {
        super.onResume()
        mViewModel.getAccountBalance()
    }


}