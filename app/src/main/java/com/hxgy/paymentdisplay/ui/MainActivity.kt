package com.hxgy.paymentdisplay.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.dz.utlis.StatusBarUtil
import com.dz.utlis.TimeUtil
import com.google.gson.reflect.TypeToken
import com.hxgy.paymentdisplay.R
import com.hxgy.paymentdisplay.api.Router
import com.hxgy.paymentdisplay.bean.FeeItemBean
import com.hxgy.paymentdisplay.databinding.ActivityMainBinding
import com.hxgy.paymentdisplay.dialog.ConfigDialog
import com.hxgy.paymentdisplay.server.HttpServer.Companion.gson
import com.hxgy.paymentdisplay.service.BackEndHttpService
import dz.solc.viewtool.adapter.CommonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
* create_user: zhengzaihong
* email:1096877329@qq.com
* create_date: 2024/5/31
* create_time: 9:22
* describe:
*/
class MainActivity : BaseActivity(){

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mContext:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        StatusBarUtil.immersive(this)

        Glide.with(this)
            .load(R.mipmap.qrcode)
            .into(mBinding.ivMarkCode)


        mBinding.ivIcon.setOnClickListener {
          ConfigDialog(this).showDialog()
        }

        getCurrentDateTime()
        startService(Intent(this, BackEndHttpService::class.java))

    }


    override fun receiveMessage(msg:String?){
        if (TextUtils.isEmpty(msg)) {
            return
        }
        launch(Dispatchers.Main){
            val requestParams =  gson.fromJson(msg,Map::class.java)
            val router = ( requestParams["router"]?:"").toString()
            if (router == Router.sendMessage){
                val status =( requestParams["status"]?:-1) as Double
                val listItems = requestParams["list"]
                val typeToken = object : TypeToken<List<FeeItemBean>>() {}.type
                val list = gson.fromJson<List<FeeItemBean>>(listItems.toString(),typeToken)
                if(status.toInt() ==0){
                    showPayView(false,null)
                    return@launch
                }
                if (status.toInt()==1){
                    showPayView(true,list)
                    return@launch
                }
            }
        }
    }


    private fun showPayView(showPay: Boolean = false,list:List<FeeItemBean>?) {
        if(showPay){
            mBinding.llNormalView.visibility = View.GONE
            mBinding.llPayView.visibility = View.VISIBLE
            mBinding.listView.adapter = object : CommonAdapter<FeeItemBean>(this,
                R.layout.layout_fee_item,list) {
                override fun convert(holder: ViewHolder?, position: Int, entity: FeeItemBean?) {
                    holder!!.setText(R.id.tvTitle,entity?.title)
                            .setText(R.id.tvContent,"费用：${entity?.amount.toString()}")
                }
            }
            return
        }
        mBinding.llNormalView.visibility = View.VISIBLE
        mBinding.llPayView.visibility = View.GONE
    }

    private fun getCurrentDateTime() {
        launch(Dispatchers.IO) {
            while (true){
                delay(1000)
                val dataTime = TimeUtil.stampstoTime(System.currentTimeMillis()/1000, "yyyy-MM-dd HH:mm:ss")
                withContext(Dispatchers.Main) {
                    mBinding.tvTime.text = dataTime
                }
            }
        }
    }


}