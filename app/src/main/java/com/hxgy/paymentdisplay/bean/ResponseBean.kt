package com.hxgy.paymentdisplay.bean

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2024/6/4
 * create_time: 11:06
 * describe: 响应
 */
class ResponseBean internal constructor(
    private val code: Int,
    private val msg: String,
    private val data: Any,
) {
    companion object {
        fun success(data: Any = {}, msg: String = "请求成功"): ResponseBean {
            return ResponseBean(200, msg, data)
        }

        fun error(errorCode: Int = 0, msg: String = "请求失败",data: Any = mutableMapOf<String,Any>()): ResponseBean {
            return ResponseBean(errorCode, msg, data)
        }
    }
}