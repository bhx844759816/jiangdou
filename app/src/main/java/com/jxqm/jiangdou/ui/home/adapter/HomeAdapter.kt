package com.jxqm.jiangdou.ui.home.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.home.model.*
import com.jxqm.jiangdou.ui.job.view.AllJobScreenActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.banner.BannerView
import com.jxqm.jiangdou.view.banner.IBannerView

/**
 * Created By bhx On 2019/8/21 0021 14:24
 */
class HomeAdapter(context: Context) : LoadMoreAdapter<HomeModel>(context) {
    var jobDetailsCallBack: ((JobDetailsModel) -> Unit)? = null


    init {
        //添加轮播图
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
        //添加分类列表
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_job_type_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: HomeModel, position: Int): Boolean = item.type == 2

            override fun convert(holder: ViewHolder?, t: HomeModel, position: Int) {
                holder?.let {
                    setJobTypeList(it.getView(R.id.rvJobTypeParent), t as HomeJobTypeModel)
                }
            }

        })
        //添加帮助Item
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_job_help_item
            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: HomeModel, position: Int): Boolean = item.type == 3

            override fun convert(holder: ViewHolder?, item: HomeModel?, position: Int) {
            }

        })
        //添加职位列表的topTitle
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_item_type

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: HomeModel, position: Int): Boolean = item.type == 4

            override fun convert(holder: ViewHolder?, t: HomeModel?, position: Int) {
                holder?.let {
                    val tvMoreJob = it.getView<TextView>(R.id.tvMoreJob)
                    tvMoreJob.clickWithTrigger {
                        mContext.startActivity<AllJobScreenActivity>()
                    }
                }
            }
        })
        //添加Item的布局
        addItemViewType(object : ItemViewType<HomeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_item
            override fun isItemClickable(): Boolean = true
            override fun isViewForType(item: HomeModel, position: Int): Boolean = item.type == 5

            override fun convert(holder: ViewHolder?, homeModel: HomeModel, position: Int) {
                holder?.let {
                    it.getView<LinearLayout>(R.id.llParent).clickWithTrigger {
                        jobDetailsCallBack?.invoke((homeModel as HomeJobDetailsModel).jobDetailsModel)
                    }
                    setHomeItem(it, homeModel)
                }
            }
        })
    }

    private fun setJobTypeList(recyclerView: RecyclerView, homeJobTypeModel: HomeJobTypeModel) {
        val list = homeJobTypeModel.jobTypeModeList
        val homeJobTypeAdapter = HomeJobTypeAdapter(mContext)
        recyclerView.layoutManager = GridLayoutManager(mContext, 5)
        recyclerView.adapter = homeJobTypeAdapter
        homeJobTypeAdapter.setDataList(list)
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
                    val url = Api.HTTP_BASE_URL + "/" + list[position].imageUrl
                    itemView.scaleType = ImageView.ScaleType.CENTER_CROP
                    Glide.with(itemView.context)
                        .load(url)
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

    private fun setHomeItem(holder: ViewHolder, homeModel: HomeModel) {
        val jobDetailsModel = (homeModel as HomeJobDetailsModel).jobDetailsModel
        val ivJobListImg = holder.getView<ImageView>(R.id.ivJobListImg)
        val tvJobTitle = holder.getView<TextView>(R.id.tvJobTitle)
        val tvJobCity = holder.getView<TextView>(R.id.tvJobCity)
        val tvJobArea = holder.getView<TextView>(R.id.tvJobArea)
        val tvJobNumbers = holder.getView<TextView>(R.id.tvJobNumbers)
        val tvJobSalary = holder.getView<TextView>(R.id.tvJobSalary)
        Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobDetailsModel.typeImgUrl).into(ivJobListImg)
        tvJobTitle.text = jobDetailsModel.title
//        val city = jobDetailsModel.areaCode.split(",")[0]
//        val area = jobDetailsModel.areaCode.split(",")[1]
//        tvJobCity.text = jobDetailsModel.areaCode
//        tvJobArea.text = jobDetailsModel.areaCode
        tvJobNumbers.text = "招${jobDetailsModel.recruitNum}人"
        tvJobSalary.text = "${jobDetailsModel.salary}币/时"
    }

}