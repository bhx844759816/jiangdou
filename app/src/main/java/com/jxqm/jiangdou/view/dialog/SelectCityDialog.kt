package com.jxqm.jiangdou.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.bhx.common.utils.FileUtils
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.google.gson.Gson
import com.jxqm.jiangdou.model.CityJsonModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import java.util.ArrayList

/**
 * 选择城市的Dialog
 * Created By bhx On 2019/8/20 0020 09:39
 */
@SuppressLint("CheckResult")
object SelectCityDialog {
    private var options1Items = ArrayList<CityJsonModel>()
    private val options2Items = ArrayList<ArrayList<String>>()
    private val options3Items = ArrayList<ArrayList<ArrayList<String>>>()


    public fun showDialog(context: Context, callBack: (String) -> Unit) {
        if (options1Items.isEmpty() || options2Items.isEmpty() || options3Items.isEmpty()) {
            Observable.create(ObservableOnSubscribe<Any> {
                val jsonData = FileUtils.getJsonFromAssets(context, "province.json")
                options1Items = parseData(jsonData)
                for (i in options1Items.indices) {//遍历省份
                    val cityList = ArrayList<String>()//该省的城市列表（第二级）
                    val province_AreaList = ArrayList<ArrayList<String>>()//该省的所有地区列表（第三极）
                    for (c in 0 until options1Items[i].city!!.size) {//遍历该省份的所有城市
                        val cityName = options1Items[i].city!![c].name
                        cityList.add(cityName)//添加城市
                        val city_AreaList = ArrayList<String>()//该城市的所有地区列表
                        city_AreaList.addAll(options1Items[i].city!![c].area!!)
                        province_AreaList.add(city_AreaList)//添加该省所有地区数据
                    }
                    /**
                     * 添加城市数据
                     */
                    options2Items.add(cityList)
                    /**
                     * 添加地区数据
                     */
                    options3Items.add(province_AreaList)
                }
                it.onNext(Any())
                it.onComplete()
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe {
                    showRealDialog(context, callBack)
                }
        } else {
            showRealDialog(context, callBack)
        }
    }

    private fun parseData(result: String): ArrayList<CityJsonModel> {//Gson 解析
        val detail = ArrayList<CityJsonModel>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonModel::class.java)
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return detail
    }

    private fun showRealDialog(context: Context, callBack: (String) -> Unit) {
        val pvOptions: OptionsPickerView<Any> = OptionsPickerBuilder(context,
            OnOptionsSelectListener { options1, options2, options3, _ ->
                //返回的分别是三个级别的选中位置
                val opt1tx = if (options1Items.size > 0)
                    options1Items[options1].pickerViewText
                else
                    ""
                val opt2tx = if (options2Items.size > 0 && options2Items[options1].size > 0)
                    options2Items[options1][options2]
                else
                    ""
                val opt3tx = if (options2Items.size > 0
                    && options3Items[options1].size > 0
                    && options3Items[options1][options2].size > 0
                )
                    options3Items[options1][options2][options3]
                else
                    ""
                val tx = opt1tx + opt2tx + opt3tx
                callBack.invoke(tx)
            })
            .setTitleText("城市选择")
            .setDividerColor(Color.BLACK)
            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
            .setContentTextSize(20)
            .build()
        pvOptions.setPicker(
            options1Items as List<Any>?, options2Items as List<MutableList<Any>>?,
            options3Items as List<MutableList<MutableList<Any>>>?
        )//三级选择器
        pvOptions.show()
    }

}