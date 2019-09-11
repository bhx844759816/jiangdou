package com.jxqm.jiangdou.ui.map

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
import com.jxqm.jiangdou.ui.map.adapter.MapAdapter


/**
 * 显示地图列表
 * Created by Administrator on 2019/9/9.
 */
class MapActivity : BaseActivity() {
    private lateinit var mMap: BaiduMap
    private lateinit var mLocationClient: LocationClient
    private lateinit var mGeoCoder: GeoCoder //获取定位地址附近得地址信息
    private var mLocationCity: String? = null//定位得城市
    private var isFirstLocation = true //是否是第一次定位刷新地图显示
    private var mLocationLatLng: LatLng? = null//定位的经纬度信息
    private var mPoiInfoList = mutableListOf<PoiInfo>() //poi检索的结果集
    private var mMarker: Marker? = null
    private lateinit var mAdapter: MapAdapter

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
                LogUtils.i("bdLocation${bdLocation.latitude}")
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
    }

    private val mGeoCoderListener = object : OnGetGeoCoderResultListener {
        override fun onGetGeoCodeResult(geoCodeResult: GeoCodeResult?) {

        }

        override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult?) {
            reverseGeoCodeResult?.let {
                mLocationClient.stop()
                //
                val poiInfoList = it.poiList
                if (poiInfoList != null) {
                    LogUtils.i("poiInfoList$poiInfoList")
                    mPoiInfoList.clear()
                    mPoiInfoList.addAll(poiInfoList)
                    mAdapter.updateDatas(mPoiInfoList)
                }

            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_map

    override fun initView() {
        initRecyclerView()
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