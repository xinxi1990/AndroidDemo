package com.example.xinxi.myapplication.blockscanner;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;
import com.example.xinxi.myapplication.base.MyApplication;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class AppBlockCanaryContext extends BlockCanaryContext {

    private static final String TAG = "AppBlockCanaryContext";

    // 实现各种上下文，包括应用标示符，用户uid，网络类型，卡慢判断阙值，Log保存位置等


    /**
     * 获取应用版本信息
     * @return
     */
    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = MyApplication.getAppContext().getPackageManager()
                    .getPackageInfo(MyApplication.getAppContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return qualifier;
    }


    /**
     * 获取用户uid
     * @return
     */
    @Override
    public String provideUid() {
        int uid = 1233321;
        // 获取用户uid,uid应该调用业务代码中getuid方法
        return String.valueOf(uid);
    }


    /**
     * 网络类型
     * @return
     */
    @Override
    public String provideNetworkType() {
        //String Networktype = new IntenetUtil().getNetworktype(MyApplication.getAppContext());
        String Networktype = "wifi";
        return Networktype;
    }


    /**
     * 监控时长
     * @return
     */
    @Override
    public int provideMonitorDuration() {
        return BlockConstans.MonitorDuration;
    }

    /**
     * 设置卡顿阀值
     * @return
     */
    @Override
    public int provideBlockThreshold() {
        return BlockConstans.BlockThreshold;
    }


    /**
     * 堆栈储存间隔
     * @return
     */
    @Override
    public int provideDumpInterval() {
        return provideBlockThreshold();
    }


    /**
     * log保存地址
     * @return
     */
    @Override
    public String providePath() {
        return BlockConstans.blockpath;
    }


    /**
     * 设置通知展示
     * @return
     */
    @Override
    public boolean displayNotification() {
        return BlockConstans.displayNotification;
    }

    /**
     * 使用压缩文件
     * @param src
     * @param dest
     * @return
     */
    @Override
    public boolean zip(File[] src, File dest) {
        return false;
    }

    /**
     * 上传zip文件
     * @param zippedFile
     */
    @Override
    public void upload(File zippedFile) {
        throw new UnsupportedOperationException();
    }


    /**
     * Packages that developer concern, by default it uses process name,
     * put high priority one in pre-order.
     *
     * @return null if simply concern only package with process name.
     */
    @Override
    public List<String> concernPackages() {
        return null;
    }

    /**
     * 设置过滤堆栈信息
     * @return
     */
    @Override
    public boolean filterNonConcernStack() {
        return false;
    }

    /**
     * 设置白名单
     * @return
     */
    @Override
    public List<String> provideWhiteList() {
        LinkedList<String> whiteList = new LinkedList<>();
        whiteList.add("org.chromium");
        return whiteList;
    }

    /**
     * 删除白名单
     * @return
     */
    @Override
    public boolean deleteFilesInWhiteList() {
        return true;
    }

    /**
     * Block拦截器
     */
    @Override
    public void onBlock(Context context, BlockInfo blockInfo) {

    }

}
