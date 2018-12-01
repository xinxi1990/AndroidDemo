package com.example.xinxi.myapplication.blockscanner;

public class BlockConstans {

    public static Boolean dev = true;
    public static final int dev_looptime = 3000; //BlockThead的循环时间,正式使用建议改成3s执行一次
    public static final int online_looptime = 30000; //BlockThead的循环时间,正式使用建议改成3s执行一次
    public static final String blockpath = "/test_blockcanary/"; //保存log日志
    public  static int MonitorDuration = 100000;   //监控时间
    public  static int BlockThreshold = 100; //卡顿阀值
    public  static boolean displayNotification = true; //是否在桌面展示
    public static final String dev_urlString = "http://192.168.154.44:5000/block/blocksave";
    public static final String online_urlString = "https://xxxx.xxxxx.com/block/blocksave";



}
