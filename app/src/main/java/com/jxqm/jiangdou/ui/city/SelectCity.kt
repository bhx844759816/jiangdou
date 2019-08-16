package com.jxqm.jiangdou.ui.city

import android.graphics.Color
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.city.adapter.SectionItemDecoration
import com.jxqm.jiangdou.ui.city.db.DBManager
import com.jxqm.jiangdou.ui.city.model.City
import com.jxqm.jiangdou.ui.city.model.HotCity
import com.jxqm.jiangdou.ui.city.model.LocatedCity
import com.jxqm.jiangdou.ui.city.view.SideIndexBar
import kotlinx.android.synthetic.main.activity_select_city.*
import java.util.ArrayList

/**
 * Created By bhx On 2019/8/16 0016 08:51
 */
/**
 * 城市选择界面
 */
class SelectCity : BaseActivity(),SideIndexBar.OnIndexTouchedChangedListener{


    private lateinit var mDbManager: DBManager
    private lateinit var mAdapter: CityAdapter
    private var mAllCities: ArrayList<City> = arrayListOf()
    private lateinit var mLocatedCity: City
    private var mHotCities: ArrayList<HotCity> = arrayListOf()
    override fun getLayoutId(): Int = R.layout.activity_select_city

    override fun initView() {
        super.initView()
        rvCityList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        rvCityList.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        val sectionItemDecoration = SectionItemDecoration(this, mAllCities, Color.parseColor("#EDEDED"),
                DensityUtil.dip2px(this, 32f), DensityUtil.dip2px(this, 13f), Color.parseColor("#999999"))
        rvCityList.addItemDecoration(sectionItemDecoration)
        mAdapter = CityAdapter(this, mAllCities, mHotCities)
        rvCityList.adapter = mAdapter
        sideIndexBar.setOverlayTextView(tvOverlay)
                .setOnIndexChangedListener(this)
    }

    override fun initData() {
        super.initData()
        if (mHotCities.isNullOrEmpty()) {
            mHotCities.add(HotCity("北京", "北京", "101010100"))
            mHotCities.add(HotCity("上海", "上海", "101020100"))
            mHotCities.add(HotCity("广州", "广东", "101280101"))
            mHotCities.add(HotCity("深圳", "广东", "101280601"))
            mHotCities.add(HotCity("天津", "天津", "101030100"))
            mHotCities.add(HotCity("杭州", "浙江", "101210101"))
            mHotCities.add(HotCity("南京", "江苏", "101190101"))
            mHotCities.add(HotCity("成都", "四川", "101270101"))
            mHotCities.add(HotCity("武汉", "武汉", "101200101"))
            mHotCities.add(HotCity("西安", "西安", "101200101"))
            mHotCities.add(HotCity("青岛", "青岛", "101200101"))
            mHotCities.add(HotCity("郑州", "郑州", "101200101"))
        }
        mDbManager = DBManager(this)
        mAllCities.addAll(mDbManager.allCities)
        mLocatedCity = LocatedCity("正在定位", "未知", "0")
        mAllCities.add(0, mLocatedCity)
        mAllCities.add(1, HotCity("热门城市", "未知", "0"))
        mAdapter.notifyDataSetChanged()
    }

    override fun onIndexChanged(index: String?, position: Int) {

    }
}