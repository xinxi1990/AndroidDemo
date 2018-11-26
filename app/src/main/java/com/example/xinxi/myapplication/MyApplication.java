package com.example.xinxi.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Observable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.TextView;
//
//import com.github.moduth.blockcanary.BlockCanary;
//import com.squareup.leakcanary.AndroidExcludedRefs;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
//import com.squareup.leakcanary.internal.DisplayLeakActivity;
//import com.squareup.leakcanary.internal.LeakCanaryInternals;
//import com.tencent.wstt.gt.GTRLog;
//import com.tencent.wstt.gt.controller.GTRController;

import java.util.Map;

//import cn.hikyson.android.godeye.toolbox.crash.CrashFileProvider;
//import cn.hikyson.android.godeye.toolbox.rxpermission.RxPermissions;
//import cn.hikyson.godeye.core.GodEye;
//import cn.hikyson.godeye.core.helper.PermissionRequest;
//import cn.hikyson.godeye.core.internal.modules.battery.Battery;
//import cn.hikyson.godeye.core.internal.modules.battery.BatteryContextImpl;
//import cn.hikyson.godeye.core.internal.modules.cpu.Cpu;
//import cn.hikyson.godeye.core.internal.modules.cpu.CpuContextImpl;
//import cn.hikyson.godeye.core.internal.modules.crash.Crash;
//import cn.hikyson.godeye.core.internal.modules.fps.Fps;
//import cn.hikyson.godeye.core.internal.modules.fps.FpsContextImpl;
//import cn.hikyson.godeye.core.internal.modules.leakdetector.LeakContextImpl2;
//import cn.hikyson.godeye.core.internal.modules.leakdetector.LeakDetector;
//import cn.hikyson.godeye.core.internal.modules.memory.Heap;
//import cn.hikyson.godeye.core.internal.modules.memory.Pss;
//import cn.hikyson.godeye.core.internal.modules.memory.PssContextImpl;
//import cn.hikyson.godeye.core.internal.modules.memory.Ram;
//import cn.hikyson.godeye.core.internal.modules.memory.RamContextImpl;
//import cn.hikyson.godeye.core.internal.modules.pageload.Pageload;
//import cn.hikyson.godeye.core.internal.modules.pageload.PageloadContextImpl;
//import cn.hikyson.godeye.core.internal.modules.sm.Sm;
//import cn.hikyson.godeye.core.internal.modules.sm.SmContextImpl;
//import cn.hikyson.godeye.core.internal.modules.thread.ThreadContextImpl;
//import cn.hikyson.godeye.core.internal.modules.thread.ThreadDump;
//import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadLock;
//import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadLockContextImpl;
//import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadlockDefaultThreadFilter;
//import cn.hikyson.godeye.core.internal.modules.traffic.Traffic;
//import cn.hikyson.godeye.core.internal.modules.traffic.TrafficContextImpl;
//import cn.hikyson.godeye.monitor.GodEyeMonitor;

import okhttp3.OkHttpClient;
//import com.readystatesoftware.chuck.ChuckInterceptor;

/**
 * Created by xinxi on 2018/3/30.
 */

public class MyApplication extends Application {

    String TAG = "MyApplication";
    String ip = "";

//    private RefWatcher refWatcher;
//    protected RefWatcher installLeakCanary(){
//        Log.d(TAG,"RefWatcher: LeakCanary");
//        return LeakCanary.install(this);
//    }

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 安装LeakCanary
//        refWatcher = installLeakCanary();
//        LeakCanaryInternals.setEnabled(this, DisplayLeakActivity.class, true);//true展示 false不展示
//        Log.d(TAG, "onCreate: LeakCanary");


//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new ChuckInterceptor(context))
//                .build();
//
//        GTRLog.isOpen = true;
//        GTRController.init(getApplicationContext());
//
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
//
//        GodEyeMonitor.injectAppInfoConext(
//                new GodEyeMonitor.AppInfoConext() {
//            @Override
//            public Context getContext() {
//
//                return MyApplication.this;
//            }
//
//            @Override
//            public Map<String, Object> getAppInfo() {
//                Map<String, Object> appInfo = new ArrayMap<>();
//                appInfo.put("ApplicationID", BuildConfig.APPLICATION_ID);
//                appInfo.put("VersionName", BuildConfig.VERSION_NAME);
//                appInfo.put("VersionCode", BuildConfig.VERSION_CODE);
//                appInfo.put("BuildType", BuildConfig.BUILD_TYPE);
//                return appInfo;
//            }
//        });
//        GodEye.instance().install(Cpu.class, new CpuContextImpl())
//                .install(Battery.class, new BatteryContextImpl(this))
//                .install(Fps.class, new FpsContextImpl(this))
//                .install(Heap.class, Long.valueOf(2000))
//                .install(Pss.class, new PssContextImpl(this))
//                .install(Ram.class, new RamContextImpl(this))
//                .install(Sm.class, new SmContextImpl(this, 1000, 300, 800))
//                .install(Traffic.class, new TrafficContextImpl())
//                .install(Crash.class, new CrashFileProvider(this))
//                .install(ThreadDump.class, new ThreadContextImpl())
//                .install(DeadLock.class, new DeadLockContextImpl(GodEye.instance().getModule(ThreadDump.class).subject(), new DeadlockDefaultThreadFilter()))
//                .install(Pageload.class, new PageloadContextImpl(this))
//                .install(LeakDetector.class, new LeakContextImpl2(this, new PermissionRequest() {
//                    @Override
//                    public io.reactivex.Observable<Boolean> dispatchRequest(Activity activity, String... permissions) {
//                        return new RxPermissions(activity).request(permissions);
//                    }
//                }));
//
//        GodEyeMonitor.work(context);

        GetWifi getWifi = new GetWifi();
        ip = getWifi.getWIFILocalIpAdress(context);
        //获取wifi地址
        Log.d(TAG,"ip地址是" + ip);

        new SystemUtil().showSystemParameter();


    }
}
