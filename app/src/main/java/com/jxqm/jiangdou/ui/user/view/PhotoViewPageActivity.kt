package com.jxqm.jiangdou.ui.user.view

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bhx.common.utils.LogUtils
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.user.vm.PhotoViewPageViewModel
import kotlinx.android.synthetic.main.activity_photo_view_page.*

class PhotoViewPageActivity : BaseDataActivity<PhotoViewPageViewModel>() {
    val mPhotoList = mutableListOf<String>()

    override fun getLayoutId(): Int = R.layout.activity_photo_view_page

    override fun getEventKey(): Any = Constants.EVENT_KEY_PHOTO_VIEW_PAGE


    override fun initView() {
        super.initView()
        val list = intent.getStringArrayListExtra("ImageUrls")
        LogUtils.i("list:$list")
        list?.let {
            mPhotoList.addAll(it)
        }
        viewPager.adapter = MyViewPagerAdapter()
    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int = mPhotoList.size


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val photoView = PhotoView(container.context)
            Glide.with(this@PhotoViewPageActivity).load(mPhotoList[position]).into(photoView)
            container.addView(
                photoView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}