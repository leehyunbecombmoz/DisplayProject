package com.hxgy.paymentdisplay.bean;

import com.google.gson.annotations.SerializedName;

/**
* create_user: zhengzaihong
* email:1096877329@qq.com
* create_date: 2024/7/17
* create_time: 17:29
* describe: 显示费用实体
*/
public class FeeItemBean  {

    @SerializedName("title")
    private String title;
    @SerializedName("amount")
    private String amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
