package com.hxgy.paymentdisplay.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Collections

/**
* create_user: zhengzaihong
* email:1096877329@qq.com
* create_date: 2024/6/3
* create_time: 15:45
* describe: 获取ip地址
*/
object IPAddressUtil {


    @SuppressLint("WifiManagerPotentialLeak")
    fun getWifiIPAddress(context: Context): String {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return Formatter.formatIpAddress(ipAddress)
    }


    /**
     * 将得到的int类型的IP转换为String类型
     * @param ip
     * @return
     */
    fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }
}