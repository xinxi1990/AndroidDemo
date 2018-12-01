package com.example.xinxi.myapplication.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
//
//import com.github.moduth.blockcanary.BlockCanary;
//import com.squareup.leakcanary.AndroidExcludedRefs;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
//import com.squareup.leakcanary.internal.DisplayLeakActivity;
//import com.squareup.leakcanary.internal.LeakCanaryInternals;
//import com.tencent.wstt.gt.GTRLog;
//import com.tencent.wstt.gt.controller.GTRController;

import com.example.xinxi.myapplication.blockscanner.AppBlockCanaryContext;
import com.example.xinxi.myapplication.blockscanner.BlockConstans;
import com.example.xinxi.myapplication.blockscanner.BlockThread;
import com.example.xinxi.myapplication.info.GetWifi;
import com.example.xinxi.myapplication.untils.CrashHandler;
import com.example.xinxi.myapplication.untils.JacocoUtils;
import com.example.xinxi.myapplication.untils.SystemUtil;
import com.github.moduth.blockcanary.BlockCanary;
import com.tencent.bugly.crashreport.CrashReport;

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

//import com.readystatesoftware.chuck.ChuckInterceptor;

/**
 * Created by xinxi on 2018/3/30.
 */

public class MyApplication extends Application {

    private static MyApplication mAppCotext;
    String TAG = "MyApplication";
    String ip = "";
    private Activity lastStartedActivity = null;
    public boolean isBackground = false;//标记是否在后台
    private static MyApplication mInstance = null;

//    private RefWatcher refWatcher;
//    protected RefWatcher installLeakCanary(){
//        Log.d(TAG,"RefWatcher: LeakCanary");
//        return LeakCanary.install(this);
//    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        CrashReport.initCrashReport(getApplicationContext(), "803eb6155d", false);

        BlockCanary.install(this, new AppBlockCanaryContext()).start();

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
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

    }

    //判断是否是后台
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackground = true;
            notifyBackground();
        }

    }


    private void notifyForeground() {
        // This is where you can notify listeners, handle session tracking, etc
    }

    private void notifyBackground() {
        // This is where you can notify listeners, handle session tracking, etc
        Log.d(TAG, "切到后台");
        BlockThread blockThread = new BlockThread();
        blockThread.scannerlog(blockThread.sdcardpath);
        Log.d(TAG,"切到后台后,上报卡顿日志!");
//        JacocoUtils.generateEcFile(true);

    }


    public boolean isInBackground = true;//标记是否在后台
    public boolean isFromPushLaunch = false;//标记是否从 push 启动


    // 使用ActivityLifecycleCallbacks可以监听每个页面的声明周期
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        private int compatStartCount = 0;
        private boolean isCompatForeground = false;//默认在后台

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            System.out.println("onActivityCreated");
        }

        @Override
        public void onActivityDestroyed(Activity activity){

        }

        @Override
        public void onActivityStopped(Activity activity) {
        }


        @Override
        public void onActivityPaused(Activity activity) {
        }


        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            System.out.println("onActivityStarted");
            lastStartedActivity = activity;
            System.out.println(isCompatForeground);
            if (!isCompatForeground) {
                isCompatForeground = true;
                onForegroundToBackground(activity);
            }
        }

        private void onForegroundToBackground(final Activity activity) {
            Log.d(TAG, "切到前台");
            //JacocoUtils.generateEcFile(true);

        }
    };

    /**
     * @return Application
     * 曾试返回 BaseApplication 而非 Application，结果运行不起来
     * 报libsupport.so里面的错
     * 不知道为啥
     */
    @NonNull
    public static Application getAppContext() {
        return mAppCotext;
    }

}
