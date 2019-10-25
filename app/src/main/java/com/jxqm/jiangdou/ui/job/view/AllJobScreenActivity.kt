package com.jxqm.jiangdou.ui.job.view

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.FileUtils
import com.bhx.common.utils.LogUtils
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.listener.OnCompatRetryListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.adapter.JobItemAdapter
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.CityJsonModel
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.ui.job.vm.AllJobScreenViewModel
import com.jxqm.jiangdou.ui.job.widget.JobScreenByAreaPopupWindow
import com.jxqm.jiangdou.ui.job.widget.JobScreenBySortPopupWindow
import com.jxqm.jiangdou.ui.job.widget.JobScreenByTypePopupWindow
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.tencent.mm.opensdk.openapi.IWXAPI
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_all_job_screen.*
import org.json.JSONArray
import java.util.ArrayList

/**
 * 全部兼职--检索
 * Created By bhx On 2019/9/6 0006 10:36
 */
class AllJobScreenActivity : BaseDataActivity<AllJobScreenViewModel>() {

    private var mTypePopupWindow: JobScreenByTypePopupWindow? = null
    private var mSortPopupWindow: JobScreenBySortPopupWindow? = null
    private var mAreaPopupWindow: JobScreenByAreaPopupWindow? = null
    private val mJobTypeList = mutableListOf<JobTypeModel>()
    private val mJobItemList = mutableListOf<JobDetailsModel>()
    private val mAreaItemList = mutableListOf<String>()
    private val mParamsMap = mutableMapOf<String, String>()
    private var mScreenResult: String? = null
    private var mJobTypeId: String? = null
    private var isRefresh = true

    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mAdapter: JobItemAdapter

    private val mGson: Gson by lazy {
        Gson()
    }

    override fun getEventKey(): Any = Constants.EVENT_KEY_ALL_JOB_SCREEN

    override fun getLayoutId(): Int = R.layout.activity_all_job_screen

    override fun initView() {
        super.initView()
        mUiStatusController = UiStatusController.get().bind(swipeRefreshLayout)
        mJobTypeId = intent.getStringExtra("JobTypeId")
        mAdapter = JobItemAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        mJobTypeId?.let {
            mParamsMap["jobTypeId"] = it
        }
        flJobType.clickWithTrigger {
            rgJobParent.clearCheck()
            rgJobParent.check(R.id.rbJobType)
        }
        flJobArea.clickWithTrigger {
            rgJobParent.clearCheck()
            rgJobParent.check(R.id.rbJobArea)
        }
        flJobSort.clickWithTrigger {
            rgJobParent.clearCheck()
            rgJobParent.check(R.id.rbJobSort)
        }
        rgJobParent.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rbJobType -> {
                    showTypePopupWindow()
                }
                R.id.rbJobArea -> {
                    showAreaPopupWindow()
                }
                R.id.rbJobSort -> {
                    showSortPopupWindow()
                }
            }
        }

        rlScreenJob.clickWithTrigger {
            val intent = Intent(this, JobScreenActivity::class.java)
            intent.putExtra("ScreenResult", mScreenResult)
            startActivityForResult(intent, REQUEST_CODE_JOB_SCREEN)
        }
        aboutUsBack.clickWithTrigger {
            finish()
        }

        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.refreshAllJobList(mParamsMap, isRefresh)
        }

        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.refreshAllJobList(mParamsMap, isRefresh)
        }
        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                isRefresh = true
                mViewModel.refreshAllJobList(mParamsMap, isRefresh)
            }
    }

    override fun initData() {
        //解析获取城市数据
        parseCityData()
        //获取全部兼职列表
        mViewModel.getAllJobType()
        //获取全部兼职
        mViewModel.refreshAllJobList(mParamsMap, isRefresh)
    }

    /**
     * 解析城市数据
     */
    private fun parseCityData() {
        addDisposable(
            Observable.create<Any> {
                val cityItemList = mutableListOf<CityJsonModel>()
                val jsonData = FileUtils.getJsonFromAssets(this, "province.json")
                try {
                    val data = JSONArray(jsonData)
                    for (i in 0 until data.length()) {
                        val entity = mGson.fromJson(
                            data.optJSONObject(i).toString(),
                            CityJsonModel::class.java
                        )
                        cityItemList.add(entity)
                    }
                    cityItemList.forEach { cityJsonModel ->
                        cityJsonModel.city?.forEach { cityBean ->
                            if (cityBean.name == MyApplication.instance().locationModel?.city) {
                                val list = cityBean.area?.map { area ->
                                    area
                                }?.filter { area ->
                                    area != "其他"
                                }
                                list?.let {
                                    mAreaItemList.addAll(list)
                                }
                                mAreaItemList.add(
                                    0,
                                    "全${MyApplication.instance().locationModel?.city}"
                                )
                                return@forEach
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    LogUtils.i("mAreaItemList=$mAreaItemList")
                    it.onComplete()
                }
            }.subscribe()
        )
    }

    /**
     *
     * 接收LiveData数据
     */
    override fun dataObserver() {
        //获取兼职类型成功
        registerObserver(Constants.TAG_GET_JOB_TYPE_SUCCESS, List::class.java).observe(
            this,
            Observer {
                val list = it as List<JobTypeModel>
                mJobTypeList.clear()
                mJobTypeList.addAll(list)
            })
        //获取兼职列表成功
        registerObserver(Constants.TAG_GET_JOB_ITEM_LIST_SUCCESS, List::class.java).observe(
            this,
            Observer {
                val list = it as List<JobDetailsModel>
                if (isRefresh) {
                    if (list.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                    }
                    mJobItemList.clear()
                    mJobItemList.addAll(list)
                    mAdapter.setDataList(mJobItemList)
                    if (swipeRefreshLayout.isRefreshing)
                        swipeRefreshLayout.finishRefresh()
                    swipeRefreshLayout.resetNoMoreData()
                } else {
                    mJobItemList.addAll(list)
                    if (list.size < Constants.PAGE_SIZE) {
                        swipeRefreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        swipeRefreshLayout.finishLoadMore()
                        mAdapter.setDataList(mJobItemList)
                    }
                }
            })
        //获取兼职列表失败
        registerObserver(Constants.TAG_GET_JOB_ITEM_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                if(swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.finishLoadMore()
                    swipeRefreshLayout.finishRefresh()
                }
                if (mJobItemList.isEmpty()) {
                    mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_JOB_SCREEN) {
            mScreenResult = data?.getStringExtra("ScreenResult")
            LogUtils.i("mScreenResult=$mScreenResult")
            mScreenResult?.let {
                val params = mGson.fromJson<Map<String, String>>(
                    it,
                    object : TypeToken<Map<String, String>>() {
                    }.type
                )
                mParamsMap.remove("year")
                mParamsMap.remove("month")
                mParamsMap.remove("day")
                mParamsMap.remove("gender")
                mParamsMap.remove("times")
                mParamsMap.remove("date")
                mParamsMap.putAll(params)
                isRefresh = true
                mViewModel.getAllJobList(mParamsMap, isRefresh)
            }
        }
    }

    /**
     * 展示工作类型
     */
    private fun showTypePopupWindow() {
        if (mTypePopupWindow == null) {
            mTypePopupWindow = JobScreenByTypePopupWindow(this)
            mTypePopupWindow!!.mConfirmCallBack = {
                LogUtils.i("showTypePopupWindow mConfirmCallBack$it")
                mParamsMap["jobTypeId"] = it.joinToString(",")
                isRefresh = true
                mViewModel.getAllJobList(mParamsMap, isRefresh)
            }
        }
        mTypePopupWindow!!.showPopup(line, mJobTypeList, mParamsMap["jobTypeId"])
    }

    private fun showAreaPopupWindow() {
        if (mAreaPopupWindow == null) {
            mAreaPopupWindow = JobScreenByAreaPopupWindow(this)
            mAreaPopupWindow!!.mConfirmCallBack = {
                if (it == "全郑州市") {
                    mParamsMap.remove("area")
                    LogUtils.i("city=${it.substring(1)}")
                    mParamsMap["city"] = it.substring(1)
                } else {
                    mParamsMap.remove("city")
                    mParamsMap["area"] = it
                }
                isRefresh = true
                mViewModel.getAllJobList(mParamsMap, isRefresh)
            }
        }

        mAreaPopupWindow!!.showPopup(line, mAreaItemList)
    }

    /**
     * 展示排序列表
     */
    private fun showSortPopupWindow() {
        if (mSortPopupWindow == null) {
            mSortPopupWindow = JobScreenBySortPopupWindow(this)
            mSortPopupWindow!!.mCallBack = {
                if (it.isEmpty()) {
                    mParamsMap.remove("jobSort")
                    mParamsMap.remove("latitude")
                    mParamsMap.remove("longitude")
                } else {
                    if (it == "instance") {
                        mParamsMap["latitude"] =
                            MyApplication.instance().locationModel?.latitude.toString()
                        mParamsMap["longitude"] =
                            MyApplication.instance().locationModel?.longitude.toString()
                    } else {
                        mParamsMap.remove("latitude")
                        mParamsMap.remove("longitude")
                    }
                    mParamsMap["jobSort"] = it
                }
                isRefresh = true
                mViewModel.getAllJobList(mParamsMap, isRefresh)
            }
        }
        mSortPopupWindow!!.showPopup(line)
    }

    companion object {
        const val REQUEST_CODE_JOB_SCREEN = 0x01
    }
}