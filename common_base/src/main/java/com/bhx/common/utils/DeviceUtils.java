package com.bhx.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

/**
 * Created By bhx On 2019/9/9 0009 16:35
 */
public class DeviceUtils {

    public static String getDeviceId(Context context) {
        String id;
        id = getIMEI(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        id = getAndroidId(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        id = getWlanId(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        return UUID.randomUUID().toString();
    }

    public static String getAndroidId(Context context) {
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    private static String getWlanId(Context context) {
        WifiManager wm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            @SuppressLint("HardwareIds") String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            return m_szWLANMAC;
        }
        return null;
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
