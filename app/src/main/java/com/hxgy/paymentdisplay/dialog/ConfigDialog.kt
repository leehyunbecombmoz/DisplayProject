package com.hxgy.paymentdisplay.dialog

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.dz.utlis.ToastTool
import com.hxgy.paymentdisplay.App.Companion.appContext
import com.hxgy.paymentdisplay.R
import com.hxgy.paymentdisplay.utils.IPAddressUtil.getWifiIPAddress
import com.hxgy.paymentdisplay.utils.SPUser


/**
* create_user: zhengzaihong
* email:1096877329@qq.com
* create_date: 2024/6/4
* create_time: 11:06
* describe: 配置信息展示界面
*/
class ConfigDialog(context: Context?) {

    private val dialog: Dialog?
    private val view: View

    init {
        dialog = Dialog(context!!, R.style.dialogStyle)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        view = LayoutInflater.from(context).inflate(R.layout.config_dialog_layout, null)
        dialog.setContentView(view)
        val outMetrics = DisplayMetrics()
        dialog.setCanceledOnTouchOutside(true)
        window.windowManager.defaultDisplay.getMetrics(outMetrics)
        val width = outMetrics.widthPixels
        val height = outMetrics.heightPixels
        window.attributes.width = width / 3+width / 5
        window.attributes.height = height / 2
        val tvIpAddress = window.findViewById<TextView>(R.id.tvIpAddress)
        val tvPort = window.findViewById<EditText>(R.id.tv_port)
        tvIpAddress.text = getWifiIPAddress(context)


        tvPort.setText(SPUser.getUser(appContext).port.toString())
        val tvCancel= window.findViewById<TextView>(R.id.tv_cance)
        val tvSuer = window.findViewById<TextView>(R.id.tv_suer)
        tvSuer.setOnClickListener {
            try {
                SPUser.getUser(appContext).port = tvPort.text.toString().toInt()
                ToastTool.show("保存成功，请重启应用")
            } catch (e: Exception) {
                ToastTool.show("端口号输入错误")
            }
        }
        tvCancel.setOnClickListener { dialog.dismiss() }
    }

    fun showDialog(): ConfigDialog {
        if (null != dialog && !dialog.isShowing) {
            dialog.show()
        }
        return this
    }

    val isShowing: Boolean
        get() = null != dialog && dialog.isShowing

    fun dismiss() {
        if (isShowing) {
            dialog!!.dismiss()
        }
    }
}