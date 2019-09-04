package com.jxqm.jiangdou.ui.home.view

import android.os.Bundle
import android.view.View
import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.attestation.view.CompanyAttestationActivity
import com.jxqm.jiangdou.ui.home.vm.MyViewModel
import com.jxqm.jiangdou.ui.login.view.LoginActivity
import com.jxqm.jiangdou.ui.user.view.MyCollectionJobActivity
import com.jxqm.jiangdou.ui.user.view.MyResumeActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.MyServiceDialog
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * 我的界面
 * Created by Administrator on 2019/8/20.
 */
class MyFragment : BaseMVVMFragment<MyViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_my

    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_MY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvUserName.clickWithTrigger {
            startActivity<LoginActivity>()
        }
        ivUserResume.clickWithTrigger {
            startActivity<MyResumeActivity>()
        }
        llMyCollection.clickWithTrigger {
            startActivity<MyCollectionJobActivity>()
        }
        rlMyCollection.clickWithTrigger {
            startActivity<MyCollectionJobActivity>()
        }
        rlCompanyAttestation.clickWithTrigger {
            startActivity<CompanyAttestationActivity>()
        }
        rlMyService.clickWithTrigger {
            activity?.let { activity -> MyServiceDialog.show(activity) }
        }

    }
}