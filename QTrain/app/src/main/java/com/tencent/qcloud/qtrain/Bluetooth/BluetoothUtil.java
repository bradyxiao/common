package com.tencent.qcloud.qtrain.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.qcloud.commonutils.log.LogUtils;

/**
 * Created by bradyxiao on 2018/8/1.
 * Copyright 2010-2018 Tencent Cloud. All Rights Reserved.
 */

public class BluetoothUtil {

    BluetoothAdapter bluetoothAdapter;
    public BluetoothUtil(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void openBlue( Context context){
        if(!bluetoothAdapter.isEnabled()){
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivity(enabler);
        }
    }

    public void printInfo(){
        if(bluetoothAdapter.isEnabled()){
            StringBuilder stringBuilder = new StringBuilder("Bluth info:\n");
            stringBuilder.append(bluetoothAdapter.getAddress()).append("|")
                    .append(bluetoothAdapter.getName()).append("|");
            LogUtils.d("XIAO", stringBuilder.toString());
        }
    }



}
