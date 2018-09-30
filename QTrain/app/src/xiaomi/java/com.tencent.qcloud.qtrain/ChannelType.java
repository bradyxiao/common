package com.tencent.qcloud.qtrain;

/**
 * Created by bradyxiao on 2018/8/6.
 * Copyright 2010-2018 Tencent Cloud. All Rights Reserved.
 */

public class ChannelType {
    public static String getType(){
        return BuildConfig.TYPE;
    }

    public static String getApplicationId(){
        return BuildConfig.APPLICATION_ID;
    }
}
