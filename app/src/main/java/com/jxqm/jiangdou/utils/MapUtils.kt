package com.jxqm.jiangdou.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.bhx.common.utils.LogUtils
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import androidx.core.content.ContextCompat.startActivity
import com.jxqm.jiangdou.R


object MapUtils {


    private fun isInstalled(context: Context, packageName: String): Boolean {
        val installedPackages = context.packageManager.getInstalledPackages(0)
        if (installedPackages != null && installedPackages.isNotEmpty()) {
            installedPackages.forEach {
                if (it.packageName == packageName) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 跳转到百度地图
     */
    fun goToBaidu(context: Context, lat: String, lng: String, address: String): Boolean {
        if (!isInstalled(context, "com.baidu.BaiduMap")) {
            return false
        }
        val intent = Intent()
        LogUtils.i("goToBaidu lat=$lat lon=$lng")
        intent.data = Uri.parse(
            "baidumap://map/direction?destination=latlng:"
                    + lat + ","
                    + lng + "|name:" + address +
                    "&mode=driving" + // 导航路线方式
                    "&src=" + context.packageName
        )
        context.startActivity(intent) // 启动调用
        return true
    }

    /**
     * 跳转到高德地图
     */
    fun goToGaode(context: Context, lat: String, lng: String, address: String): Boolean {
        if (!isInstalled(context, "com.autonavi.minimap")) {
            return false
        }
        val array = bd_decrypt(lat.toDouble(), lng.toDouble())
        val stringBuffer = StringBuffer("androidamap://route?sourceApplication=").append(
            context.resources.getString(
                R.string.app_name
            )
        )
        stringBuffer.append("&dlat=").append(array[0])
            .append("&dlon=").append(array[1])
            .append("&dname=").append(address)
            .append("&dev=").append(0)
            .append("&t=").append(0)
        val intent =
            Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()))
        intent.setPackage("com.autonavi.minimap")
        context.startActivity(intent)
        return true
    }

    //百度转高德
    private fun bd_decrypt(bd_lat: Double, bd_lon: Double): Array<Double> {
        val x = bd_lon - 0.0065
        val y = bd_lat - 0.006
        val z = sqrt(x * x + y * y) - 0.00002 * sin(y * Math.PI)
        val theta = atan2(y, x) - 0.000003 * cos(x * Math.PI)
        return arrayOf(z * sin(theta), z * cos(theta))
    }
}