package com.example.xinxi.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;
import com.example.xinxi.myapplication.WifiConnector.WifiCipherType;

/**
 * Created by xinxi on 2018/5/8.
 */

public class MyReceiverBack extends BroadcastReceiver {
    WifiManager wifiManager;
    WifiConnector wac;

    public MyReceiverBack() {
        Log.e("test", "------------------------------");
    }

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        Log.e("test", "--------------start broadcast---------------");
        // TODO Auto-generated method stub
        Toast.makeText(arg0,
                "接收到Intent的Action为：" + arg1.getAction() + "\n消息内容是："+arg1.getExtras()+
                        arg1.getStringExtra("string")+"+add:"+arg1.getFloatExtra("int", 1.0f), Toast.LENGTH_LONG).show();
    }
}
