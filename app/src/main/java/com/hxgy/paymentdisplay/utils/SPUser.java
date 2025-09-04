package com.hxgy.paymentdisplay.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
* create_user: zhengzaihong
* email:1096877329@qq.com
* create_date: 2024/6/4
* create_time: 9:24
* describe: 获取或保存用户的信息
*/

@SuppressWarnings("all")
public class SPUser {
    private static volatile SPUser user;

    public static final String PORT = "PORT";
    private static final String TOKEN = "TOKEN";


    //xml 配置文件
    private static final String sp_config = "_UserTable";


    private Context context;
    private SharedPreferences preferences;

    public static SPUser getUser(Application context) {
        if (user == null) {
            synchronized (SPUser.class) {
                if (user == null)
                    user = new SPUser(context);
            }
        }
        return user;
    }

    private SPUser(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
    }



    public SPUser setPort(int port) {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        preferences.edit().putInt(PORT, port).commit();
        return this;
    }


    public int getPort() {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        return preferences.getInt(PORT, 8009);
    }


    public SPUser setToken(String token) {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        preferences.edit().putString(TOKEN, token).commit();
        return this;
    }

    public String getToken() {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        return preferences.getString(TOKEN, "");
    }


    public void setKeyValue(String key,String value) {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }


    public String getValue(String key) {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public void remove(String key) {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        preferences.edit().remove(key).commit();
    }

    public SPUser clearUserInfo() {
        preferences = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
        return this;
    }

}
