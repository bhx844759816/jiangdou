package com.jxqm.jiangdou.model

import com.contrarywind.interfaces.IPickerViewData

/**
 * Created By bhx On 2019/8/20 0020 09:57
 */
class CityJsonModel : IPickerViewData {
    var name: String = ""
    var city: ArrayList<CityBean>? = null
    override fun getPickerViewText(): String  = name

    class CityBean {
        var name: String = ""
        var area: ArrayList<String>? = null
    }
}