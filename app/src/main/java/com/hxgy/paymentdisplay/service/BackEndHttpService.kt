package com.hxgy.paymentdisplay.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.hxgy.paymentdisplay.server.HttpServer
import com.hxgy.paymentdisplay.utils.IPAddressUtil

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2024/6/3
 * create_time: 15:53
 * describe:后端服务
 */
class BackEndHttpService : Service() {

    private var httpServer:HttpServer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        try {
            val address = IPAddressUtil.getWifiIPAddress(this)
            httpServer = HttpServer.getInstance(address)
            httpServer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
            startService(Intent(this, BackEndHttpService::class.java))
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        httpServer?.stop()
    }
}