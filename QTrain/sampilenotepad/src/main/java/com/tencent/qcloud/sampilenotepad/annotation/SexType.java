package com.tencent.qcloud.sampilenotepad.annotation;

/**
 * Created by bradyxiao on 2018/7/10.
 */
enum SexType{
    MALE("男"),
    FEMALE("女");

    String desc;

    SexType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
