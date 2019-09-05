package com.jxqm.jiangdou.ui.user.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.user.adapter.PhotoListAdapter
import com.jxqm.jiangdou.ui.user.vm.MyResumeViewModel
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.GridItemSpaceDecoration
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.SelectCityDialog
import com.jxqm.jiangdou.view.dialog.SingleSelectDialog
import com.tbruyelle.rxpermissions2.RxPermissions
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
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_resume.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 我的简历
 * Created By bhx On 2019/8/19 0019 14:06
 */
class MyResumeActivity : BaseActivity(){
    private val mSexList = arrayListOf("男", "女")
    private val mEducationList = arrayListOf("小学", "初中", "专科", "本科", "硕士", "博士")
    private val mHeightList = mutableListOf<String>()
    private val mWeightList = mutableListOf<String>()
    private lateinit var mPhotoLisAdapter: PhotoListAdapter
    private var mTimePickView: TimePickerView? = null
    private var mPhotoFile: File? = null
    private val mPhotoList = mutableListOf<File>()

    override fun getLayoutId(): Int = R.layout.activity_my_resume

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        mPhotoLisAdapter = PhotoListAdapter(this, mPhotoList)
        mPhotoLisAdapter.setAddCallBack {
            selectHeadPhoto(9, 0x02)
        }
        mPhotoLisAdapter.setDeleteCallBack {
            mPhotoList.removeAt(it)
            mPhotoLisAdapter.notifyDataSetChanged()
        }
        rvPhotoList.layoutManager = GridLayoutManager(this, 4)
        rvPhotoList.addItemDecoration(GridItemSpaceDecoration(DensityUtil.dip2px(this, 20f)))
        rvPhotoList.adapter = mPhotoLisAdapter
        myResumeBack.clickWithTrigger {
            finish()
        }
        rlHeadPhotoParent.clickWithTrigger {
            onViewClick(it)
        }
        rlUserSexParent.clickWithTrigger {
            onViewClick(it)
        }
        rlUserBirthdayParent.clickWithTrigger {
            onViewClick(it)
        }
        rlUserAgeParent.clickWithTrigger {
            onViewClick(it)
        }
        rlUserEducationParent.clickWithTrigger {
            onViewClick(it)
        }
        rlUserHeightParent.clickWithTrigger {
            onViewClick(it)
        }
        rlUserWeightParent.clickWithTrigger {
            onViewClick(it)
        }
        rlUserLocationParent.clickWithTrigger {
            onViewClick(it)
        }

    }

    private fun onViewClick(view: View) {
        when (view.id) {
            R.id.rlHeadPhotoParent -> { //修改头像
                requestPermission()
            }
            R.id.rlUserSexParent -> { //修改性别
                SingleSelectDialog.show(this, mSexList)
            }
            R.id.rlUserBirthdayParent -> {//修改出生年月
                showTimePickedDialog()
            }
            R.id.rlUserAgeParent -> {//修改年龄和星座

            }
            R.id.rlUserEducationParent -> {//修改学历
                SingleSelectDialog.show(this, mEducationList)
            }
            R.id.rlUserHeightParent -> {//修改身高
                initHeightList()
                SingleSelectDialog.show(this, mHeightList, "cm")
            }
            R.id.rlUserWeightParent -> {//修改体重
                initWeightList()
                SingleSelectDialog.show(this, mWeightList, "kg")
            }
            R.id.rlUserLocationParent -> {//修改出生地
                SelectCityDialog.showDialog(this)
            }
        }
    }

    /**
     * 初始化身高的数据
     */
    private fun initHeightList() {
        if (mHeightList.isEmpty()) {
            for (index in 150 until 200) {
                mHeightList.add(index.toString())
            }
        }
    }

    /**
     * 初始化体重数据
     */
    private fun initWeightList() {
        if (mWeightList.isEmpty()) {
            for (index in 30 until 150) {
                mWeightList.add(index.toString())
            }
        }
    }

    /**
     * 展示日期选择对话框
     */
    private fun showTimePickedDialog() {
        if (mTimePickView == null) {
            mTimePickView = TimePickerBuilder(this, OnTimeSelectListener { date, _ ->
                run {
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val time = format.format(date)
                    etUserBirthday.setText(time)
                }
            }).build()
        }
        if (!mTimePickView!!.isShowing) {
            mTimePickView!!.show()
        }
    }

    /**
     * 请求权限 SD卡存储和拍照权限
     */
    private fun requestPermission() {
        val disposable =
            RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe {
                    LogUtils.i("requestPermission$it")
                    if (it) {
                        selectHeadPhoto(1, 0x01)
                    }
                }
        addDisposable(disposable)

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
            .isCrop(true)
            .cropStyle(CropImageView.Style.CIRCLE)
            .isCropSaveRectangle(false)
            .thumbnailScale(0.6f)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .imageEngine(GlideEngine())
            .forResult(requestCode)
    }

    /**
     * 选择完成图片的回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_HEAD_PHOTO_CODE -> {
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
            REQUEST_PHOTO_SHOW_CODE -> {
                val paths: List<String> = Matisse.obtainPathResult(data)
                handlePhoto(paths)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 处理选择图片的回调
     */
    private fun handlePhoto(paths: List<String>) {
        val disposable = Observable.create(ObservableOnSubscribe<Any> {
            paths.forEach { path ->
                val file = CompressHelper.getDefault(this).compressToFile(FileUtil.getFileByPath(path))
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
        addDisposable(disposable)
    }

    companion object {
        const val REQUEST_HEAD_PHOTO_CODE = 0x01
        const val REQUEST_PHOTO_SHOW_CODE = 0x02
    }
}