package com.jxqm.jiangdou.ui.map

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.location.Location
import android.opengl.Visibility
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.*
import com.bhx.common.base.BaseActivity
import com.jxqm.jiangdou.R
import kotlinx.android.synthetic.main.activity_map.*
import com.baidu.location.LocationClient
import com.baidu.mapapi.map.MyLocationConfiguration
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.geocode.*
import com.bhx.common.utils.LogUtils
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption
import com.baidu.mapapi.search.poi.*
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.adapter.rv.listener.OnItemClickListener
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.FileUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.hideKeyboard
import com.jxqm.jiangdou.ui.map.adapter.MapAdapter
import com.jxqm.jiangdou.ui.map.adapter.MapSearchAdapter
import com.jxqm.jiangdou.utils.clickWithTrigger


/**
 * 显示地图列表
 * Created by Administrator on 2019/9/9.
 */
class MapActivity : BaseActivity() {
    private lateinit var mMap: BaiduMap
    private lateinit var mLocationClient: LocationClient
    private lateinit var mGeoCoder: GeoCoder //获取定位地址附近得地址信息
    private var mLocationCity: String? = null//定位得城市
    private var mLocationProvince: String? = null//定位得省
    private var mLocationArea: String? = null//定位得区
    private var isFirstLocation = true //是否是第一次定位刷新地图显示
    private var mLocationLatLng: LatLng? = null//定位的经纬度信息
    private var mPoiInfoList = mutableListOf<PoiInfo>() //poi检索的结果集
    private var mSearchPoiInfoList = mutableListOf<PoiInfo>() //poiSearch检索的结果集
    private var mMarker: Marker? = null
    private lateinit var mAdapter: MapAdapter
    private lateinit var mSearchAdapter: MapSearchAdapter
    private var mPoiSearch: PoiSearch? = null
    private var mSelectPoiInfo: PoiInfo? = null

    private val mMapStatusChangeListener = object : BaiduMap.OnMapStatusChangeListener {
        override fun onMapStatusChangeStart(p0: MapStatus?) {
        }

        override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {
        }

        override fun onMapStatusChange(p0: MapStatus?) {
        }

        override fun onMapStatusChangeFinish(mapStatus: MapStatus?) {
            mapStatus?.let {
                if (!isFirstLocation) {
                    mSelectPoiInfo = null
                    mMarker?.startAnimation()
                    mMarker?.position = it.target
                    val cenpt = it.target
                    LogUtils.i("onMapStatusChangeFinish${it.target}")
                    mGeoCoder.reverseGeoCode(
                        ReverseGeoCodeOption()
                            .pageNum(0)
                            .pageSize(30)
                            .newVersion(1)
                            .location(cenpt)
                    )
                }
            }
        }

    }

    private val mLocationListener = object : BDAbstractLocationListener() {
        override fun onReceiveLocation(bdLocation: BDLocation?) {
            bdLocation?.let {
                LogUtils.i("bdLocation$bdLocation")
                // 定位数据
                val locationData = MyLocationData.Builder()
                    .direction(it.direction)//设置方向信息
                    .latitude(it.latitude) //经度
                    .longitude(it.longitude)//纬度
                    .build()
                mMap.setMyLocationData(locationData)
                mLocationLatLng = LatLng(it.latitude, it.longitude)
                // 获取城市，待会用于POISearch
                mLocationCity = bdLocation.city
                mLocationProvince = bdLocation.province
                mLocationArea = bdLocation.district
                if (isFirstLocation) {
                    isFirstLocation = false
                    val msu = MapStatusUpdateFactory.newLatLngZoom(mLocationLatLng, 15f)
                    mMap.animateMapStatus(msu)
                    //添加Marker
                    val ooA = MarkerOptions().position(mLocationLatLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_logo))
                        .zIndex(18).animateType(MarkerOptions.MarkerAnimateType.jump)
                        .scaleX(1.3f)
                        .scaleY(1.3f)
                        .draggable(true)
                    mMarker = mMap.addOverlay(ooA) as Marker
                    //
                    mGeoCoder = GeoCoder.newInstance()
                    mGeoCoder.setOnGetGeoCodeResultListener(mGeoCoderListener)
                    val reverseGeoCodeOption = ReverseGeoCodeOption()
                    reverseGeoCodeOption.location(mLocationLatLng)
                    reverseGeoCodeOption.pageNum(0)
                    reverseGeoCodeOption.pageSize(30)
                    reverseGeoCodeOption.newVersion(1)
                    mGeoCoder.reverseGeoCode(reverseGeoCodeOption)
                }
            }
        }

        override fun onLocDiagnosticMessage(
            locType: Int,
            diagnosticType: Int,
            diagnosticMessage: String?
        ) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage)
            val sb = StringBuffer(256)
            sb.append("诊断结果: ")
            if (locType === BDLocation.TypeNetWorkLocation) {
                if (diagnosticType === 1) {
                    sb.append("网络定位成功，没有开启GPS，建议打开GPS会更好")
                    sb.append("\n" + diagnosticMessage)
                } else if (diagnosticType === 2) {
                    sb.append("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好")
                    sb.append("\n" + diagnosticMessage)
                }
            } else if (locType === BDLocation.TypeOffLineLocationFail) {
                if (diagnosticType === 3) {
                    sb.append("定位失败，请您检查您的网络状态")
                    sb.append("\n" + diagnosticMessage)
                }
            } else if (locType === BDLocation.TypeCriteriaException) {
                if (diagnosticType === 4) {
                    sb.append("定位失败，无法获取任何有效定位依据")
                    sb.append("\n" + diagnosticMessage)
                } else if (diagnosticType === 5) {
                    sb.append("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位")
                    sb.append(diagnosticMessage)
                } else if (diagnosticType === 6) {
                    sb.append("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试")
                    sb.append("\n" + diagnosticMessage)
                } else if (diagnosticType === 7) {
                    sb.append("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试")
                    sb.append("\n" + diagnosticMessage)
                } else if (diagnosticType === 9) {
                    sb.append("定位失败，无法获取任何有效定位依据")
                    sb.append("\n" + diagnosticMessage)
                }
            } else if (locType === BDLocation.TypeServerError) {
                if (diagnosticType === 8) {
                    sb.append("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限")
                    sb.append("\n" + diagnosticMessage)
                }
            }
            LogUtils.i("定位失败$sb")
        }
    }

    private val mGeoCoderListener = object : OnGetGeoCoderResultListener {
        override fun onGetGeoCodeResult(geoCodeResult: GeoCodeResult?) {

        }

        override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult?) {
            reverseGeoCodeResult?.let {
                mLocationClient.stop()
                mLocationCity = it.addressDetail.city
                mLocationProvince = it.addressDetail.province
                mLocationArea = it.addressDetail.district
                val poiInfoList = it.poiList
                if (poiInfoList != null) {
                    LogUtils.i("poiInfoList$poiInfoList")
                    mPoiInfoList.clear()
                    if (mSelectPoiInfo != null) {
                        mPoiInfoList.add(mSelectPoiInfo!!)
                    }
                    mPoiInfoList.addAll(poiInfoList)
                    mAdapter.updateDatas(mPoiInfoList)
                }

            }
        }
    }

    private val mGetPoiSearchListener = object : OnGetPoiSearchResultListener {
        override fun onGetPoiResult(p0: PoiResult?) {
            p0?.let {
                val list = it.allPoi
                LogUtils.i("搜索关键字结果:$list")
                mSearchPoiInfoList.clear()
                if (list != null && list.isNotEmpty()) {
                    mSearchPoiInfoList.addAll(list)
                }
                mSearchAdapter.updateDatas(mSearchPoiInfoList, mLocationLatLng)
            }
        }

        override fun onGetPoiDetailResult(p0: PoiDetailResult?) {
        }

        override fun onGetPoiDetailResult(p0: PoiDetailSearchResult?) {
        }

        override fun onGetPoiIndoorResult(p0: PoiIndoorResult?) {

        }

    }

    override fun getLayoutId(): Int = R.layout.activity_map

    override fun initView() {
        toolBar.setNavigationOnClickListener {
            finish()
        }
        initRecyclerView()
        initMap()
        initSearch()
    }

    private fun initSearch() {
//        etSearchPoiInfo.addTextChangedListener {
//            afterTextChanged {
//
//            }
//        }
        etSearchPoiInfo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchKey = etSearchPoiInfo.text.toString()
                if (searchKey.isNotEmpty()) {
                    tvCancelSearch.visibility = View.VISIBLE
                    flMapParent.visibility = View.GONE
                    flSearchParent.visibility = View.VISIBLE
                    searchPoiInfo(searchKey)
                    return@setOnEditorActionListener true
                } else {
                    ToastUtils.toastShort("请输入搜索关键词")
                }
            }
            return@setOnEditorActionListener false
        }
//        etSearchPoiInfo.setOnFocusChangeListener { _, t ->
//            if (t) {
//                tvCancelSearch.visibility = View.VISIBLE
//                flMapParent.visibility = View.GONE
//                flSearchParent.visibility = View.VISIBLE
//            }else{
//                tvCancelSearch.visibility = View.GONE
//                flMapParent.visibility = View.VISIBLE
//                flSearchParent.visibility = View.GONE
//            }
//        }
        tvCancelSearch.clickWithTrigger {
            tvCancelSearch.visibility = View.GONE
            flMapParent.visibility = View.VISIBLE
            flSearchParent.visibility = View.GONE
            etSearchPoiInfo.text = null
            etSearchPoiInfo.hideKeyboard()
        }
        flSearchParent.clickWithTrigger {
            tvCancelSearch.visibility = View.GONE
            flMapParent.visibility = View.VISIBLE
            flSearchParent.visibility = View.GONE
            etSearchPoiInfo.text = null
            etSearchPoiInfo.hideKeyboard()
        }

    }

    private fun initMap() {
        mapView.logoPosition = LogoPosition.logoPostionleftBottom
        mapView.showScaleControl(false)
        mapView.showZoomControls(false)
        mMap = mapView.map
        val mapStatus = MapStatus.Builder().zoom(35f).build()
        val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus)
        mMap.setMapStatus(mapStatusUpdate)
        mMap.setOnMapStatusChangeListener(mMapStatusChangeListener)
        //开启定位图层
        mMap.isMyLocationEnabled = true
        //设置定位模式
        mMap.setMyLocationConfiguration(
            MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,
                true,
                null
            )
        )
        mLocationClient = LocationClient(this)
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

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MapAdapter(this)
        recyclerView.adapter = mAdapter
        rvSearchRecyclerView.layoutManager = LinearLayoutManager(this)
        mSearchAdapter = MapSearchAdapter(this)
        rvSearchRecyclerView.adapter = mSearchAdapter

        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: ViewHolder?, position: Int) {
                val poiInfo = mPoiInfoList[position]
                selectArea(poiInfo)
            }

            override fun onItemLongClick(view: View?, holder: ViewHolder?, position: Int): Boolean {
                return false
            }
        })

        mSearchAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: ViewHolder?, position: Int) {
                tvCancelSearch.visibility = View.GONE
                flMapParent.visibility = View.VISIBLE
                flSearchParent.visibility = View.GONE
                etSearchPoiInfo.text = null
                mSelectPoiInfo = mSearchPoiInfoList[position]
                mSelectPoiInfo?.let {
                    val msu = MapStatusUpdateFactory.newLatLngZoom(it.location, 15f)
                    mMap.animateMapStatus(msu)
                }
            }

            override fun onItemLongClick(view: View?, holder: ViewHolder?, position: Int): Boolean {
                return false
            }
        })
    }

    private fun selectArea(poiInfo: PoiInfo) {
        val rect = Rect(
            0, DensityUtil.dip2px(this, 60f)
            , mapView.width, mapView.height - DensityUtil.dip2px(this, 60f)
        )
        //截图
        mMap.snapshotScope(rect) {
            // 保存图片
            FileUtils.saveBitmap(it, Constants.APP_SAVE_DIR, Constants.MAPVIEW_FILENAME)
            val intent = Intent()
            LogUtils.i("sleectArea$poiInfo")
            intent.putExtra("address", poiInfo.address)
            intent.putExtra("name", poiInfo.name)
            intent.putExtra("city", mLocationCity)
            intent.putExtra("area", mLocationArea)
            intent.putExtra("province", mLocationProvince)
            intent.putExtra("latLng", poiInfo.location)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }

    /**
     * 搜索指定得关键字信息
     */
    private fun searchPoiInfo(keyWords: String) {
        if (mPoiSearch == null) {
            mPoiSearch = PoiSearch.newInstance()
            mPoiSearch!!.setOnGetPoiSearchResultListener(mGetPoiSearchListener)
        }
        val mPoiSearchConfig = PoiCitySearchOption()
        mPoiSearchConfig.apply {
            keyword(keyWords) // 关键字
            city(mLocationCity)// 城市
            pageCapacity(20)  // 设置每页容量，默认为每页10条

        }
        LogUtils.i("开始搜索关键字:$keyWords")
        mPoiSearch!!.searchInCity(mPoiSearchConfig!!)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMap.isMyLocationEnabled = false
        mapView.onDestroy()

    }

}