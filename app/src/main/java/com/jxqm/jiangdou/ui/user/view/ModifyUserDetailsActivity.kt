package com.jxqm.jiangdou.ui.user.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.RegularUtils
import com.bhx.common.utils.ToastUtils
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.isRightInput
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.UserModel
import com.jxqm.jiangdou.ui.user.vm.ModifyUserDetailsViewModel
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.SingleSelectDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.compress.CompressHelper
import com.zhihu.matisse.compress.FileUtil
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.ui.widget.CropImageView
import kotlinx.android.synthetic.main.activity_modify_user_details.*
import java.io.File

/**
 * 修改用户信息
 */
class ModifyUserDetailsActivity : BaseDataActivity<ModifyUserDetailsViewModel>() {
    private val mSexList = arrayListOf("女", "男")
    private var mPhotoFile: File? = null
    private var mSexCode = 2
    override fun getLayoutId(): Int = R.layout.activity_modify_user_details

    override fun getEventKey(): Any = Constants.EVENT_MODIFY_USER_DETAILS

    override fun initView() {
        super.initView()
        val userModel = MyApplication.instance().userModel
        userModel?.let {
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.avatar)
                .transform(GlideCircleTransform(this))
                .into(ivHeadPhoto)
            etUserName.setText(it.nick)
            tvUserSex.text = it.gender
        }
        //修改头像
        rlHeadPhotoParent.clickWithTrigger {
            requestPermission()
        }
        //修改性别
        rlUserSexParent.clickWithTrigger {
            SingleSelectDialog.show(this, mSexList) {
                mSexCode = it
                tvUserSex.text = mSexList[it]
            }
        }
        tvUploadResume.clickWithTrigger {
            val paramsMap = mutableMapOf<String, String>()
            val filesMap = mutableMapOf<String, File>()
            val userName = etUserName.text.toString().trim()
            if (!RegularUtils.isLegalName(userName)) {
                ToastUtils.toastShort("请输入合法的姓名")
                return@clickWithTrigger
            }
            paramsMap["nick"] = userName
            paramsMap["genderCode"] = mSexCode.toString()
            mPhotoFile?.let {
                filesMap["avatarFile"] = it
            }
            mViewModel.updateUserInfo(paramsMap, filesMap)
        }

        rlBack.clickWithTrigger {
            finish()
        }
    }

    /**
     * 请求权限 SD卡存储和拍照权限
     */
    private fun requestPermission() {
        val disposable =
            RxPermissions(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).subscribe {
                if (it) {
                    selectHeadPhoto()
                }
            }
        addDisposable(disposable)

    }

    override fun dataObserver() {
        registerObserver(Constants.TAG_UPDATE_USER_SUCCESS, UserModel::class.java).observe(this,
            Observer {
                MyApplication.instance().userModel = it
                LiveBus.getDefault().postEvent(
                    Constants.EVENT_KEY_MAIN_MY,
                    Constants.TAG_MAIN_MY_LOGIN_SUCCESS,
                    true
                )
                finish()
            })
    }

    /**
     * 选择头像
     */
    private fun selectHeadPhoto() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .countable(true)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.jxqm.jiangdou.fileprovider"))
            .maxSelectable(1)
            .isCrop(true)
            .cropStyle(CropImageView.Style.CIRCLE)
            .isCropSaveRectangle(false)
            .thumbnailScale(0.6f)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .imageEngine(GlideEngine())
            .forResult(0x01)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            0x01 -> {
                //接收到选择的照片
                val file = FileUtil.getFileByPath(Matisse.obtainPathResult(data)[0])
                // 压缩后的文件（多个文件压缩可以循环压缩）
                mPhotoFile = CompressHelper.getDefault(this).compressToFile(file)
                //显示到头像上
                Glide.with(this)
                    .load(mPhotoFile)
                    .transform(GlideCircleTransform(this))
                    .into(ivHeadPhoto)
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}