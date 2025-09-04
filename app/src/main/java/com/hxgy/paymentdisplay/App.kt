package com.hxgy.paymentdisplay

import android.app.Application
import android.graphics.Color
import android.speech.tts.TextToSpeech
import android.view.Gravity
import androidx.multidex.MultiDex
import com.cczhr.TTS
import com.dz.utlis.ScreenUtils.dip2px
import com.dz.utlis.ToastTool
import java.util.Locale


/**
* create_user: zhengzaihong
* email:1096877329@qq.com
* create_date: 2024/5/29
* create_time: 17:37
* describe: 程序入口
*/
class App : Application(){

    companion object {
        @JvmStatic var appContext: Application? = null
        lateinit var tts: TextToSpeech
    }

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)


        appContext = this

        ToastTool.options()
            .setInterval(2000)
            .setRadius(dip2px(this, 30f).toInt())
            .setTextColor(Color.WHITE)
            .setBackGroundColor(Color.BLUE)
            .setTextSize(16)
            .setPadding(dip2px(this, 15f).toInt())
            .setGravity(Gravity.CENTER)
            .setLongTime(false)
            .setStrokeWidth(0)
            .setRadiusType(ToastTool.RadiusType.ALL_RADIUS)
            .setStrokeColor(Color.TRANSPARENT)
            .build(this)


        initTTS()
    }

    private fun initTTS(){
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.CHINESE
                tts.setSpeechRate(0.7f)
            }
        }
        TTS.getInstance().init(this)
    }
}