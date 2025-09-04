package com.hxgy.paymentdisplay.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hxgy.paymentdisplay.receiver.ReceiverConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2024/5/31
 * create_time: 16:27
 * describe: 基类
 */
open class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            val insetsController = window.insetsController
//            if (insetsController != null) {
//                insetsController.hide(WindowInsets.Type.navigationBars())
//                insetsController.hide(WindowInsets.Type.statusBars())
//                insetsController.systemBarsBehavior =
//                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        } else {
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//        }
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        registerReceiver()

    }

    private fun registerReceiver(){
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (ReceiverConst.ACTION_RECEIVER == intent.action) {
                    val value = intent.getStringExtra("data")
                    receiveMessage(value)
                }
            }
        }
        val intentFilter = IntentFilter(ReceiverConst.ACTION_RECEIVER)
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter)
    }


    open fun receiveMessage(msg: String?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

}