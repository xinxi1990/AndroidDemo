package com.example.xinxi.myapplication.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.View.OnClickListener;
import android.view.View;
import android.support.v7.widget.Toolbar;

//import com.github.moduth.blockcanary.BlockCanary;
//import com.tencent.wstt.gt.client.AbsGTParaLoader;
//import com.tencent.wstt.gt.client.GT;
//import com.tencent.wstt.gt.client.InParaManager;
//import com.tencent.wstt.gt.client.OutParaManager;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;
import android.os.SystemClock;

import com.example.xinxi.myapplication.R;
import com.example.xinxi.myapplication.untils.JacocoUtils;
import com.example.xinxi.myapplication.untils.UserStrings;
import com.example.xinxi.myapplication.untils.Utils;


public class MainActivity extends AppCompatActivity implements UserStrings {
    private  static  MainActivity mainActivity;
    //静态mainActivity

    private Handler myHandler;
    //定义一个Intent，用于发送广播；
    private Intent myIntent;

    //子线程标志位；
    private boolean tag = false;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private TextView text;
    private Toolbar toolbar;
    private Context context;
    private TextView tv;


    String TAG = "MainActivity";

    /**
     * 广播接受者
     */

    public class BatteryReceiver extends BroadcastReceiver {

        public int BatteryN;
        public String BatteryStatus;
        public int BatteryV;
        public String BatteryTemp;
        public double BatteryT;

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
                tv.setText("目前电量为" + BatteryN + "% --- " + BatteryStatus + "\n" + "电压为" + BatteryV + "mV --- " + BatteryTemp + "\n" + "温度为" + (BatteryT*0.1) + "℃");
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity: 我是onCreate方法，我会在Activity第一次被创建是调用");

        addListenerOnButton1();
        addListenerOnButton2();
        addListenerOnButton3();
        addListenerOnButton4();
        addListenerOnButton5();
//        addListenerOnButton6();
        addListenerOnButton7();
        addListenerOnButton8();
        addListenerOnButton9();

        text = (TextView) findViewById(R.id.textView);

        Toolbar toolbar=(Toolbar)findViewById(R.id.mainbar);
        //需要调用该函数才能设置toolbar的信息
        this.setSupportActionBar(toolbar);
        //显示向上箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //设置title，我直接用toolbar会出错
        getSupportActionBar().setTitle(TAG);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        java.util.Date time = new  java.util.Date();
        Log.d(TAG, "onCreate的时间是" + time.getTime());

        //WebView webview = findViewById(R.id.webView1);
        //webview.loadUrl("http://www.baidu.com/");
        //webview.reload();// reload page

        tv = (TextView) findViewById(R.id.TV);
        //this.setContentView(tv
        //注册广播接受者java代码
        //IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //创建广播接受者对象
        //BatteryReceiver batteryReceiver = new BatteryReceiver();
        //注册receiver
        //registerReceiver(batteryReceiver, intentFilter);


        //实例化Intent
        myIntent = new Intent();
        //设置过滤条件；
        myIntent.setAction("android.intent.action.ACTION_POWER_CONNECTED");
//        myIntent.setAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        //此处我使用一个Handler，接收子线程每隔3秒发来一次消息，
        //就发送一个广播，并将值发出去；
        myHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                int count = msg.arg1;
                //注册广播接受者java代码
                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                //创建广播接受者对象
                BatteryReceiver batteryReceiver = new BatteryReceiver();
                //注册receiver
                registerReceiver(batteryReceiver, intentFilter);
            }
        };

        new Thread(new Runnable() {

            int count = 0;

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //将标志位值设为true;
                tag = true;
                while (tag) {
                    Message msg = Message.obtain();
                    msg.arg1 = count;
                    myHandler.sendMessage(msg);

                    //SystemClock.sleep(3000);
                    //3秒钟采集一次
                    SystemClock.sleep(60000);
                    count += 1;
                }
            }
        }).start();

        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memInfo);
        long availMem = memInfo.availMem/1000000;
        Log.d(TAG,availMem+"");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
        JacocoUtils.generateEcFile(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: 我是onStart方法，我会在Activity由不可见变为可见时调用");
        java.util.Date time = new  java.util.Date();
        Log.d(TAG, "onStart的时间是" + time.getTime());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle", "MainActivity: 我是onResume方法，我在Activity处于运行状态时调用");
        java.util.Date time = new  java.util.Date();
        Log.d(TAG, "onResume的时间是" + time.getTime());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle", "MainActivity: 我是onPause方法，我会在Activity暂停时调用");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle", "MainActivity: 我是onStop方法，我会在Activity停止时调用");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifeCycle", "MainActivity: 我是onRestart方法，我会在Activty从停止状态变为运行状态时调用");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeCycle", "MainActivity: 我是onDestroy方法，我会在Activty销毁之前调用");

    }



    private void addListenerOnButton1() {
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick1: ");
                Log.d(TAG, context.toString());
            }

        });
    }

    private void addListenerOnButton2() {
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick2: ");
                try {
                    sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                text.setText("任务完成");
            }
        });
    }

    private void addListenerOnButton3() {
        button1 = (Button)findViewById(R.id.button3);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick3: ");
                Intent intent = new Intent();
                //context = MainActivity.this;
                mainActivity = MainActivity.this;
                intent.setClass(mainActivity,JumpActivity.class);
                //intent.setClass(context,JumpActivity.class);
                startActivity(intent);
            }

        });

    }
    private void goToTest() {
        //this.context = context.getApplicationContext();
        //Intent intent = new Intent(this, TestActivity.class);
        context = getApplicationContext();
        Intent intent = new Intent(context, TestActivity.class);
        startActivity(intent);
    }

    private void addListenerOnButton4() {
        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick4: ");
                goToTest();
            }

        });

    }

    private void addListenerOnButton5() {
        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick5: ");

                mHandler.postDelayed(new test(),100);

            }

        });
    }

//    private void addListenerOnButton6() {
//        button6 = (Button)findViewById(R.id.button6);
//        button6.setOnClickListener(connect);
//    }

    private void addListenerOnButton7() {
        button7 = (Button)findViewById(R.id.button7);
        button7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick7: ");
                Intent intent = new Intent();
                //context = MainActivity.this;
                mainActivity = MainActivity.this;
                intent.setClass(mainActivity,WifiActivity.class);
                startActivity(intent);
            }

        });
    }


    private void addListenerOnButton8() {
        button1 = (Button)findViewById(R.id.button8);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick8: ");
                Intent intent = new Intent();
                mainActivity = MainActivity.this;
                intent.setClass(mainActivity,TestActivity1.class);
                startActivity(intent);
            }

        });
    }


    private void addListenerOnButton9() {
        button1 = (Button)findViewById(R.id.button9);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick9: ");
                Intent intent = new Intent();
                mainActivity = MainActivity.this;
                intent.setClass(mainActivity,TestActivity2.class);
                startActivity(intent);
            }

        });
    }




    Handler mHandler = new Handler();
    private List<byte[]> mlist = new ArrayList<>();
    private class test implements Runnable{
        @Override
        public void run() {
            mlist.add(new byte[1*1024*1024]);
            mHandler.postDelayed(new test(),100);
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                {
                    System.out.println("-->回到主线程刷新ui任务");
                    text.setText("任务完成");
                }
                break;

                default:
                    break;
            }
        };
    };

    private void startNewThread() throws InterruptedException {
        Log.d(TAG, "-->做一些耗时的任务");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handler.sendEmptyMessage(0);
        Log.d(TAG, "sendEmptyMessage");
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Thread.currentThread().sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };


    }

    /*
     * connect按钮按下后，会和GT控制台服务进行连接；
     * 如果GT控制台应用尚未启动，则会在连接过程中被启动；
     * 如果GT控制台已启动，并且没有与其他被测应用程序连接，则会成功与本应用连接上。
     */
//    View.OnClickListener connect = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//			/*
//			 *  GT usage
//			 * 与GT控制台连接，同时注册输入输出参数
//			 */
//            GT.connect(getApplicationContext(), new AbsGTParaLoader() {
//
//                @Override
//                public void loadInParas(InParaManager inPara) {
//					/*
//					 * 注册输入参数，将在GT控制台上按顺序显示
//					 */
//                    inPara.register(并发线程数, "TN", "1", "2", "3");
//                    inPara.register(KeepAlive, "KA", "false", "true");
//                    inPara.register(读超时, "超时", "5000", "10000","1000");
//                    inPara.register(连接超时, "连超时", "5000", "10000","1000");
//
//                    // 定义默认显示在GT悬浮窗的3个输入参数
//                    inPara.defaultInParasInAC(并发线程数, KeepAlive, 读超时);
//
//                    // 设置默认无效的一个入参（GT1.1支持）
//                    inPara.defaultInParasInDisableArea(连接超时);
//                }
//
//                @Override
//                public void loadOutParas(OutParaManager outPara) {
//					/*
//					 * 注册输出参数，将在GT控制台上按顺序显示
//					 */
//                    outPara.register(下载耗时, "耗时", false, "ms");
//                    outPara.register(实际带宽, "带宽", false, "KB/s");
//                    outPara.register(singlePicSpeed, "SSPD", false, "KB/s");
//                    outPara.register(NumberOfDownloadedPics, "NDP");
//                    outPara.register(NumberOfDownloadedPics, "NDP");
//
//                    // 定义默认显示在GT悬浮窗的3个输出参数
//                    outPara.defaultOutParasInAC(下载耗时, 实际带宽, singlePicSpeed);
//                }
//            });
//
//            // 默认在GT一连接后就展示悬浮窗（GT1.1支持）
//            GT.setFloatViewFront(true);
//
//            // 默认打开性能统计开关（GT1.1支持）
//            GT.setProfilerEnable(true);
//
//            //Toast.makeText(mainActivity,"已连接到GT控制台 ", Toast.LENGTH_SHORT).show();
//            //Toast.makeText(Test_Input_Output_Activity.this, getString(R.string.connect_gt), Toast.LENGTH_SHORT).show();
//        }
//    };



}
