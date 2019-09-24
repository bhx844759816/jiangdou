package com.jxqm.jiangdou.ui.home.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.ui.home.model.HomeJobDetailsTitleModel
import com.jxqm.jiangdou.ui.home.model.HomeJobTypeModel
import com.jxqm.jiangdou.ui.home.model.HomeModel
import com.jxqm.jiangdou.ui.home.model.HomeSwipeModel
import com.jxqm.jiangdou.view.GridRadioGroup
import com.jxqm.jiangdou.view.banner.BannerView
import com.jxqm.jiangdou.view.banner.IBannerView

/**
 * Created By bhx On 2019/8/21 0021 14:24
 */
class HomeAdapter(context: Context) : LoadMoreAdapter<HomeModel>(context) {
    private val imageList = arrayListOf(
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565003123891&di=6b99987620571a5600e681f1ed9a7e56&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FqpYuMBtI9tONDBEBXrp6Cg%3D%3D%2F6631251384142500810.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565003197464&di=7de9e4ce6a18c31469492d743472a1b1&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F5c5a4a0f4f967198c9dd9ccb46174efc61a4707b.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565003219156&di=5061bb93e67f62b54d0d20b23e1bf425&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201611%2F22%2F20161122082357_sjyKQ.jpeg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565003180679&di=e567595cdfdbffd601297374aac6e2f5&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201701%2F13%2F20170113092725_AucYf.jpeg"
    )

    init {
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_swiper_layout
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: HomeModel, position: Int): Boolean = item.type == 1

            override fun convert(holder: ViewHolder?, homeModel: HomeModel?, position: Int) {
                holder?.let {
                    setBanner(it.getView(R.id.bannerView), homeModel as HomeSwipeModel)
                }
            }

        })

        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_job_type_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: HomeModel, position: Int): Boolean = item.type == 2

            override fun convert(holder: ViewHolder?, t: HomeModel, position: Int) {
                holder?.let {
                    setJobTypeList(it.getView(R.id.rgJobTypeParent), t as HomeJobTypeModel)
                }
            }

        })

        //添加顶部的布局
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_top_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: HomeModel?, position: Int): Boolean = item?.type == 1

            override fun convert(holder: ViewHolder?, t: HomeModel?, position: Int) {
                holder?.let {
                    setBanner(it.getView(R.id.bannerView))
                }
            }

        })
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_item_type

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: HomeModel?, position: Int): Boolean =
                position < mDatas.size && (mDatas[position] is HomeJobDetailsTitleModel)

            override fun convert(holder: ViewHolder?, t: HomeModel?, position: Int) {
            }

        })

        //添加Item的布局
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_item
            override fun isItemClickable(): Boolean = true
            override fun isViewForType(item: HomeModel?, position: Int): Boolean =
                position < mDatas.size && (mDatas[position] is HomeItemModel)

            override fun convert(holder: ViewHolder?, t: HomeModel?, position: Int) {
            }
        })

    }

    private fun setJobTypeList(gridGroup: GridRadioGroup, homeJobTypeModel: HomeJobTypeModel) {
        val list = homeJobTypeModel.jobTypeModeList
        list.forEach {

        }
    }


    private fun setBanner(bannerView: BannerView, swipeModel: HomeSwipeModel) {
        val list = swipeModel.swpierModel
        bannerView.setBannerViewImpl(object : IBannerView {
            override fun getCount(): Int {
                return list.size
            }

            override fun getItemView(context: Context): View {
                return ImageView(context)
            }

            override fun onBindView(itemView: View, position: Int) {
                if (itemView is ImageView) {
                    itemView.scaleType = ImageView.ScaleType.CENTER_CROP
                    Glide.with(itemView.context)
                        .load(Api.HTTP_BASE_URL + "/" + list[position].imageUrl)
                        .into(itemView)
                    itemView.setOnClickListener {
                    }
                }
            }

            override fun getDefaultView(context: Context): View? {
                return View(context).apply {
                    setBackgroundColor(Color.BLUE)
                }
            }

            override fun isDefaultAutoScroll(): Boolean {
                return true
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

        })
    }

}