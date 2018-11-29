package com.example.xinxi.myapplication.untils;

import android.util.Log;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MonitorUtil {

    public static final String TAG = "MonitorUtil";

    public static void monitorThis(Object arg1,Object arg2,Object arg3){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

        if (arg1 !=null){
            Log.i(TAG,arg1.toString() +'\n' + date);
        }
        if (arg2 !=null){
            Log.i(TAG,arg2.toString() +'\n' + date);
        }
        if (arg3 !=null){
            Log.i(TAG,arg3.toString() +'\n' + date);
        }

    }

}
