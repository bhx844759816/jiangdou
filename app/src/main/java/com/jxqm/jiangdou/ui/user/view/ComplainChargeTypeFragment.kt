package com.jxqm.jiangdou.ui.user.view

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.ToastUtils
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ui.user.adapter.PhotoListAdapter
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.GridItemSpaceDecoration
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.SingleSelectDialog
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.compress.CompressHelper
import com.zhihu.matisse.compress.FileUtil
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.ui.widget.CropImageView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_complain_charge_type.*
import java.io.File

/**
 * 收取费用投诉
 * Created By bhx On 2019/8/24 0024 17:39
 */
class ComplainChargeTypeFragment : BaseLazyFragment() {
    //    deposit(0,"押金"), service(1,"服务费") ;
    private val mChargeType = listOf("押金", "服务费")
    private lateinit var mPhotoLisAdapter: PhotoListAdapter
    private val mPhotoList = mutableListOf<File>()
    private var mChargeTypeCode = -1

    override fun getLayoutId(): Int = R.layout.fragment_complain_charge_type


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPhotoList.layoutManager = GridLayoutManager(mContext, 4)
        mPhotoLisAdapter = PhotoListAdapter(mContext, mPhotoList)
        mPhotoLisAdapter.setMaxSelectPhotoCounts(8)
        rvPhotoList.addItemDecoration(GridItemSpaceDecoration(DensityUtil.dip2px(mContext, 20f)))
        rvPhotoList.adapter = mPhotoLisAdapter
        mPhotoLisAdapter.setAddCallBack {
            selectHeadPhoto(8 - mPhotoList.size, REQUEST_PHOTO_SHOW_CODE)
        }
        mPhotoLisAdapter.setDeleteCallBack {
            mPhotoList.removeAt(it)
            mPhotoLisAdapter.notifyDataSetChanged()
        }
        rlSelectChargeType.clickWithTrigger {
            activity?.let {
                SingleSelectDialog.show(it, mChargeType) { index ->
                    mChargeTypeCode = index
                    tvChargeText.text = mChargeType[index]
                }
            }
        }
        //提交
        tvSubmit.clickWithTrigger {
            val charge = etInputChargeAmount.text.toString().trim()
            val content = etInputContent.text.toString().trim()
            val address = etInputLocation.text.toString().trim()
            if (mChargeTypeCode == -1) {
                ToastUtils.toastShort("请选择收费类型")
                return@clickWithTrigger
            }
            if (charge.isEmpty()) {
                ToastUtils.toastShort("请输入收费金额")
                return@clickWithTrigger
            }
            if (content.isEmpty()) {
                ToastUtils.toastShort("请输入情况描述")
                return@clickWithTrigger
            }
            val params = mutableMapOf<String, Any>()
            params["chargeTypeCode"] = mChargeTypeCode
            params["amount"] = charge
            if (address.isNotEmpty())
                params["address"] = address
            params["content"] = content
            if (activity is ComplainDetailsActivity) {
                (activity as ComplainDetailsActivity).submitComplain(params, mPhotoList)
            }

        }
        etInputChargeAmount.addTextChangedListener {
            afterTextChanged {
                changeSubmitStatus()
            }
        }
        etInputContent.addTextChangedListener {
            afterTextChanged {
                changeSubmitStatus()
            }
        }

    }

    /**
     * 改变提价按钮的状态
     */
    private fun changeSubmitStatus() {
        val charge = etInputChargeAmount.text.toString().trim()
        val content = etInputContent.text.toString().trim()

        val isEnabled = charge.isNotEmpty() && content.isNotEmpty() && mChargeTypeCode != -1
        if (isEnabled) {
            tvSubmit.setBackgroundResource(R.drawable.shape_button_select)
        } else {
            tvSubmit.setBackgroundResource(R.drawable.shape_button_default)
        }
    }

    /**
     * 选择头像
     */
    private fun selectHeadPhoto(maxSelectable: Int, requestCode: Int) {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .countable(true)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.jxqm.jiangdou.fileprovider"))
            .maxSelectable(maxSelectable)
            .isCrop(false)
            .isCropSaveRectangle(false)
            .thumbnailScale(0.6f)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .imageEngine(GlideEngine())
            .forResult(requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_PHOTO_SHOW_CODE -> {
                val paths: List<String> = Matisse.obtainPathResult(data)
                handlePhoto(paths)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 处理选择图片的回调
     */
    private fun handlePhoto(paths: List<String>) {
        val disposable = Observable.create(ObservableOnSubscribe<Any> {
            paths.forEach { path ->
                val file =
                    CompressHelper.getDefault(mContext).compressToFile(FileUtil.getFileByPath(path))
                mPhotoList.add(file)
            }
            it.onNext(Any())
            it.onComplete()
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe {
                mPhotoLisAdapter.notifyDataSetChanged()
            }
    }

    companion object {
        const val REQUEST_PHOTO_SHOW_CODE = 0x01
    }
}