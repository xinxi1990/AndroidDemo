package com.example.xinxi.myapplication;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.os.Environment;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Created by xinxi on 2018/4/23.
 */

public class Utils {

    /* 将文件数据写入sdcard中，保存数据 */
    public void writeDataToSD(String text) {
        try {
            Log.d("writeDataToSD","go to");
            /* 获取File对象，确定数据文件的信息 */
            File file  = new File(Environment.getExternalStorageDirectory()+"/battery.txt");

            /* 判断sd的外部设置状态是否可以读写 */
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                 /* 流的对象 *//*  */
                FileOutputStream fos = new FileOutputStream(file,true);

                /* 需要写入的数据 */
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                String message =df.format(new Date())+" , "+text + "\n";

                 /* 将字符串转换成字节数组 */
                byte[] buffer = message.getBytes();

                 /* 开始写入数据 */
                fos.write(buffer);

                /* 关闭流的使用 */
                fos.close();
                //Toast.makeText(activity, "文件写入成功", Toast.LENGTH_LONG).show();
                Log.d("writeDataToSD","sucess");
            }

        } catch (Exception e) {
            Log.d("writeDataToSD","fail: " + e);
            //Toast.makeText(activity, "文件写入失败", Toast.LENGTH_LONG).show();
        }
    }
}