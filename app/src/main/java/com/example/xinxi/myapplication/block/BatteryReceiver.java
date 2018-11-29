package com.example.xinxi.myapplication.block;

/**
 * Created by xinxi on 2018/4/24.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.example.xinxi.myapplication.untils.Utils;


/**
 * 广播接受者
 */

public class BatteryReceiver extends BroadcastReceiver {

    public int BatteryN;
    public String BatteryStatus;
    public int BatteryV;
    public String BatteryTemp;
    public double BatteryT;

    String TAG = "MainActivity";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d(TAG, action);
			/*
			 * 如果捕捉到的action是ACTION_BATTERY_CHANGED， 就运行onBatteryInfoReceiver()
			 */
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            BatteryN = intent.getIntExtra("level", 0);      //目前电量
            BatteryV = intent.getIntExtra("voltage", 0);  //电池电压
            BatteryT = intent.getIntExtra("temperature", 0);  //电池温度

            switch (intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    BatteryStatus = "充电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    BatteryStatus = "放电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    BatteryStatus = "未充电";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    BatteryStatus = "充满电";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    BatteryStatus = "未知道状态";
                    break;
            }

            switch (intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    BatteryTemp = "未知错误";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    BatteryTemp = "状态良好";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    BatteryTemp = "电池没有电";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    BatteryTemp = "电池电压过高";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    BatteryTemp = "电池过热";
                    break;
            }

            Utils utils = new Utils();

            String text = "目前电量为" + BatteryN + "% --- " + BatteryStatus + "\n" + "电压为" + BatteryV + "mV --- " + BatteryTemp + "\n" + "温度为" + (BatteryT * 0.1) + "℃";

            utils.writeDataToSD(text);

            Log.d(TAG, "目前电量为" + BatteryN + "%");
            //tv.setText("目前电量为" + BatteryN + "% --- " + BatteryStatus + "\n" + "电压为" + BatteryV + "mV --- " + BatteryTemp + "\n" + "温度为" + (BatteryT*0.1) + "℃");
        }
    }
}
