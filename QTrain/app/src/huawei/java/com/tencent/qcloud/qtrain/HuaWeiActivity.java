package com.tencent.qcloud.qtrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tencent.qcloud.commonutils.log.LogUtils;

public class HuaWeiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.d("XIAO", ChannelType.getType());
    }
}
