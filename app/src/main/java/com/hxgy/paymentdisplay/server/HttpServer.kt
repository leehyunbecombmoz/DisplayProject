package com.hxgy.paymentdisplay.server

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.cczhr.TTS
import com.dz.utlis.StringUtils
import com.google.gson.Gson
import com.hxgy.paymentdisplay.App
import com.hxgy.paymentdisplay.api.Router
import com.hxgy.paymentdisplay.bean.ResponseBean.Companion.error
import com.hxgy.paymentdisplay.bean.ResponseBean.Companion.success
import com.hxgy.paymentdisplay.receiver.ReceiverConst
import com.hxgy.paymentdisplay.utils.SPUser
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2024/6/3
 * create_time: 15:56
 * describe: 接口通信数据处理
 */
class HttpServer(hostname: String?, port: Int) : NanoHTTPD(hostname, port) {

    companion object {
        val gson = Gson()
        val localBroadcastManager = LocalBroadcastManager.getInstance(App.appContext!!)
        val voiceMsgList = mutableListOf<String>()
        @Volatile
        private var httpServer: HttpServer? = null
        fun getInstance(ipAddress: String?): HttpServer? {
            if (httpServer == null) {
                synchronized(HttpServer::class.java) {
                    if (httpServer == null) {
                        httpServer = HttpServer(ipAddress,SPUser.getUser(App.appContext!!).port)
                    }
                }
            }
            return httpServer
        }
    }


    override fun serve(session: IHTTPSession): Response {
        val ct = ContentType(session.headers["content-type"]).tryUTF8()
        session.headers["content-type"] = ct.contentTypeHeader
        return dealWith(session)
    }

    private fun dealWith(session: IHTTPSession): Response {
        if (Method.GET == session.method) {
            return  dealWithGet(session)
        }
        if (Method.POST == session.method) {
            return dealWithPost(session)
        }

        return newFixedLengthResponse(gson.toJson(error(errorCode = 404, data = "没有该请求方式")))
    }


    private fun dealWithGet(session: IHTTPSession): Response {
        val parameters = session.parameters
        return newFixedLengthResponse(gson.toJson(success(msg = "请求成功", data = parameters)))
    }

    private fun dealWithPost(session: IHTTPSession): Response {
        //获取请求头数据
//        val header = session.headers
        val routePath = session.uri
//        Log.v("-routePath---", "-$routePath")
        //获取传参参数
        val params: Map<String, String> = HashMap()
        try {
            session.parseBody(params)
            val requestParams = params["postData"]
            if (StringUtils.isEmpty(requestParams)) {
                return newFixedLengthResponse(gson.toJson(error(msg = "请求参数错误")))
            }
            val requestData = gson.fromJson(requestParams, Map::class.java)
            if(routePath.equals(Router.sendMessage)){
               return postSendMessage(requestData)
            }
            if(routePath.equals(Router.playVoice)){
                return postPlayVoice(requestData)
            }

            return newFixedLengthResponse(gson.toJson(error(msg = "没有该请求路径")))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return newFixedLengthResponse(gson.toJson(error(errorCode = 404, msg = "没有该请求方式")))
    }

    private fun postSendMessage(requestData:Map<*,*>): Response {
        val status = requestData["status"]
        if(status==null|| StringUtils.isEmpty(status.toString())){
            return newFixedLengthResponse(gson.toJson(error(msg = "请求参数错误")))
        }
        sendBroadcast(requestData as MutableMap<String, Any?>,Router.sendMessage)
        return newFixedLengthResponse(gson.toJson(success(msg = "请求成功")))
    }


    private fun postPlayVoice(requestData:Map<*,*>): Response {
       val responseResult: MutableMap<String, Any?> = HashMap()
        val voiceText = requestData["voiceText"].toString()
        if(StringUtils.isEmpty(voiceText)){
            return newFixedLengthResponse(gson.toJson(error(msg = "语音播报内容不能为空")))
        }
        voiceMsgList.add(voiceText)
        queue()
        return newFixedLengthResponse(gson.toJson(success(data = responseResult)))
    }

    private fun sendBroadcast(requestData:MutableMap<String, Any?>,router:String) {
        val intent = Intent(ReceiverConst.ACTION_RECEIVER)
        requestData["router"] = router
        intent.putExtra("data", gson.toJson(requestData))
        requestData.remove("router")
        localBroadcastManager.sendBroadcast(intent)
    }


    private  var isLoop = false
    @OptIn(DelicateCoroutinesApi::class)
    private fun queue() {
        if(isLoop){
            return
        }
        isLoop = true
        GlobalScope.launch(Dispatchers.IO) {
            while (true){
                if (voiceMsgList.size > 0 && !TTS.getInstance().isSpeaking) {
                    TTS.getInstance().speakText(voiceMsgList[0])
                    voiceMsgList.removeAt(0)
                }
                delay(1000)
            }
        }
    }
}