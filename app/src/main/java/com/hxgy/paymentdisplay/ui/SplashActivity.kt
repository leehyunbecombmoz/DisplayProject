package com.hxgy.paymentdisplay.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.hxgy.paymentdisplay.databinding.ActivitySplashLayoutBinding
import dz.solc.uihandler.Run

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2024/5/31
 * create_time: 16:21
 * describe: 开屏页
 */
@SuppressLint("all")
class SplashActivity : BaseActivity() {

    private lateinit var mBinding: ActivitySplashLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashLayoutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Run.getUiHandler().postDelayed({
            startActivity(
                Intent(
                    this@SplashActivity,
                    MainActivity::class.java
                )
            )
            finish()
        }, 1400)
    }

}