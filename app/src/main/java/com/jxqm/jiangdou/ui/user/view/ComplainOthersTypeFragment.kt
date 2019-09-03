package com.jxqm.jiangdou.ui.user.view

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.user.adapter.PhotoListAdapter
import com.jxqm.jiangdou.utils.GridItemSpaceDecoration
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
import kotlinx.android.synthetic.main.fragment_complain_other_type.*
import java.io.File

/**
 * 其他类型的投诉
 * Created By bhx On 2019/8/26 0026 10:39
 */
class ComplainOthersTypeFragment : BaseLazyFragment() {
    private lateinit var mPhotoLisAdapter: PhotoListAdapter
    private val mPhotoList = mutableListOf<File>()

    override fun getLayoutId(): Int = R.layout.fragment_complain_other_type


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPhotoList.layoutManager = GridLayoutManager(mContext, 4)
        mPhotoLisAdapter = PhotoListAdapter(mContext, mPhotoList)
        rvPhotoList.addItemDecoration(GridItemSpaceDecoration(DensityUtil.dip2px(mContext, 20f)))
        rvPhotoList.adapter = mPhotoLisAdapter
        mPhotoLisAdapter.setAddCallBack {
            selectHeadPhoto(8, REQUEST_PHOTO_SHOW_CODE)
        }
        mPhotoLisAdapter.setDeleteCallBack {
            mPhotoList.removeAt(it)
            mPhotoLisAdapter.notifyDataSetChanged()
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
            .isCrop(true)
            .cropStyle(CropImageView.Style.CIRCLE)
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
    }

    /**
     * 处理选择图片的回调
     */
    private fun handlePhoto(paths: List<String>) {
        val disposable = Observable.create(ObservableOnSubscribe<Any> {
            paths.forEach { path ->
                val file = CompressHelper.getDefault(mContext).compressToFile(FileUtil.getFileByPath(path))
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