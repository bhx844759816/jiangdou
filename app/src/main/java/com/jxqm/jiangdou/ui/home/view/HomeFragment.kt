package com.jxqm.jiangdou.ui.home.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.geocode.GeoCoder
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.FileUtils
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.http.AppUpdateManager
import com.jxqm.jiangdou.http.HttpResult
import com.jxqm.jiangdou.model.*
import com.jxqm.jiangdou.ui.attestation.view.CompanyAttestationActivity
import com.jxqm.jiangdou.ui.city.SelectCity
import com.jxqm.jiangdou.ui.home.adapter.HomeAdapter
import com.jxqm.jiangdou.ui.home.model.*
import com.jxqm.jiangdou.ui.home.vm.HomeViewModel
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.ui.job.view.JobSearchActivity
import com.jxqm.jiangdou.ui.map.MapActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.vector.update_app.UpdateAppBean
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created By bhx On 2019/9/5 0005 14:38
 */

class HomeFragment : BaseMVVMFragment<HomeViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_HOME
    private val mHomeModelList = arrayListOf<HomeModel>()
    private var isRefresh = true
    private lateinit var mAdapter: HomeAdapter
    private lateinit var mLocationClient: LocationClient
    private var mDisposable: Disposable? = null
    //定位回调
    private val mLocationListener = object : BDAbstractLocationListener() {
        override fun onReceiveLocation(bdLocation: BDLocation?) {
            bdLocation?.let {
                mLocationClient.stop()
                val model = LocationModel(
                    it.province,
                    it.city,
                    it.district,
                    it.latitude,
                    it.longitude
                )
                MyApplication.instance().locationModel = model
                tvLocationCity.text = model.city
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = HomeAdapter(mContext)
        mAdapter.setDataList(mHomeModelList)
        mAdapter.jobDetailsCallBack = {
            val intent = Intent(mContext, JobDetailsActivity::class.java)
            intent.putExtra("JobId", it.id.toString())
            intent.putExtra("Status", JobDetailsActivity.STATUS_SINGUP)
            startActivity(intent)
        }
        swipeRefreshLayout.setEnableLoadMore(false)
        swipeRefreshLayout.setEnableAutoLoadMore(true)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setOnRefreshListener {
            mHomeModelList.clear()

            isRefresh = true
            mViewModel.getHomeDataRefresh()
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getHomeDataLoadMore()
        }
        tvLocationCity.clickWithTrigger {
            startActivity<SelectCity>()
        }
        llSearch.clickWithTrigger {
            startActivity<JobSearchActivity>()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestGpsPermission()
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        checkAppUpdate()
        //注册获取轮播图
        registerObserver(Constants.TAG_GET_HOME_SWIPER, List::class.java).observe(this, Observer {
            val list = it as List<SwpierModel>
            val homeSwipeModel = HomeSwipeModel(list)
            mHomeModelList.add(homeSwipeModel)
            mAdapter.updateDatas(mHomeModelList)
        })
        //获取兼职类型
        registerObserver(Constants.TAG_GET_HOME_JOB_TYPE, List::class.java).observe(this, Observer {
            val list = it as List<JobTypeModel>
            val jobTypeModel = HomeJobTypeModel(list)
            mHomeModelList.add(jobTypeModel)
            mHomeModelList.add(HomeJobHelpModel())
            mHomeModelList.add(HomeJobDetailsTitleModel())
            mAdapter.updateDatas(mHomeModelList)

        })
        //获取推荐兼职列表J
        registerObserver(Constants.TAG_GET_HOME_RECOMMEND_LIST, List::class.java).observe(
            this,
            Observer {
                val list = it as List<JobDetailsModel>
                val homeJobDetailsModelList = mutableListOf<HomeJobDetailsModel>()
                list.forEach { jobDetailsModel ->
                    val homeJobDetailsModel = HomeJobDetailsModel(jobDetailsModel)
                    homeJobDetailsModelList.add(homeJobDetailsModel)
                }
                if (isRefresh) {
                    swipeRefreshLayout.resetNoMoreData()
                    swipeRefreshLayout.finishRefresh()
                    val iterator = mHomeModelList.iterator()
                    while (iterator.hasNext()) {
                        val homeModel = iterator.next()
                        if (homeModel.type == 5) {
                            iterator.remove()
                        }
                    }
                    if (list.size >= 10) {
                        swipeRefreshLayout.setEnableLoadMore(true)
                    }
                    mHomeModelList.addAll(homeJobDetailsModelList)
                } else {
                    if (homeJobDetailsModelList.isEmpty()) {
                        swipeRefreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        swipeRefreshLayout.finishLoadMore()
                        mHomeModelList.addAll(homeJobDetailsModelList)
                    }
                }
                mAdapter.updateDatas(mHomeModelList)
            })
    }

    /**
     * 请求GPS权限
     */
    private fun requestGpsPermission() {
        mDisposable = RxPermissions(activity!!).request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe { aBoolean ->
                if (aBoolean!!) {
                    startLocation()
                }
            }
    }

    private fun checkAppUpdate() {
        /**
         * 更新app版本
         */
        activity?.updateApp(Api.HTTP_BASE_URL + Api.GET_APP_UPDATE, AppUpdateManager())
        {
            isPost = false
            themeColor = 0xff82A2FE.toInt()
            hideDialogOnDownloading()
        }?.check {
            parseJson {
                val response = it
                val appUpdateModel = Gson().fromJson<HttpResult<AppUpdateModel>>(
                    response!!,
                    object : TypeToken<HttpResult<AppUpdateModel>>() {
                    }.type
                )
                if (appUpdateModel.code == "0") {
                    val localVersionCode = Utils.getLocalVersion(mContext)
                    val isNeedUpdate = if (appUpdateModel.data.versionCode > localVersionCode)
                        "Yes" else "no"
                    UpdateAppBean()
                        .setUpdate(isNeedUpdate)
                        //（必须）新版本号，
                        .setNewVersion(appUpdateModel.data.versionName)
                        //（必须）下载地址
                        .setApkFileUrl(appUpdateModel.data.downloadUrl)
                        //（必须）更新内容
                        .setUpdateLog(appUpdateModel.data.modifyContent)
                        //大小，不设置不显示大小，可以不设置
                        .setTargetSize(appUpdateModel.data.apkSize)
                        //是否强制更新，可以不设置
                        .setConstraint(false)
                        //设置md5，可以不设置
                        .setNewMd5(appUpdateModel.data.apkMd5)
                } else {
                    UpdateAppBean()
                        .setUpdate("no")
                }
            }
        }
    }

    /**
     * 开始定位
     */
    private fun startLocation() {
        mLocationClient = LocationClient(mContext)
        mLocationClient.registerLocationListener(mLocationListener)
        //定位选项
        val option = LocationClientOption()
        option.setCoorType("bd09ll")
        // 设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true)
        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
        // 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true)
        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true)
        //设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps)模式 Hight_Accuracy 高精度模式
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        option.priority = LocationClientOption.GpsFirst //GPS优先
        // 设置是否打开gps进行定位
        option.isOpenGps = true
        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setScanSpan(1000)
        mLocationClient.locOption = option
        //开始定位
        mLocationClient.start()
    }

    override fun onStop() {
        super.onStop()
        mLocationClient.stop()
    }

    /**
     * 请求数据
     */
    override fun onFirstUserVisible() {
        mViewModel.getHomeData(isRefresh)
    }

    companion object {
        const val REQUEST_CODE_LOCATION_SETTING = 0x01
    }

}