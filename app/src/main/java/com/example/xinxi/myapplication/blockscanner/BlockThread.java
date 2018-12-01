package com.example.xinxi.myapplication.blockscanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.example.xinxi.myapplication.base.MyApplication;
import com.example.xinxi.myapplication.untils.PermissionsUtil;
import static com.example.xinxi.myapplication.blockscanner.BlockConstans.blockpath;

/**
 * @author xinxi
 * block子线程采集sdcard下的log日志,把log日志上报
 */


public class BlockThread implements Runnable {

    private static final String TAG = "BlockThread";
    private static boolean mIsRunning;
    private PackageManager pm;

    FileTools fileTools = new FileTools();
    public final String sdcardpath = fileTools.getSdCardPath() + blockpath;

    public void start() {
        mIsRunning = true;
        new Thread(this).start();
    }


    /**
     * 规则:
     * 1.初始化运行之前先把目录下缓存文件删除
     * 2.启动app以后,根据looptime循环查找卡顿文件，并上报log日志
     * 3.上报log日志成功后,删除日志,避免下次重新上传日志
     * 4.如果上传失败,不删除,继续放到sdcard下
     */

    @Override
    public void run() {

        fileTools.deleteDirectory(sdcardpath);

        int looptime = 0;

        if (BlockConstans.dev){
            looptime = BlockConstans.dev_looptime;
        }else {
            looptime = BlockConstans.online_looptime;
        }

        String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        while (mIsRunning) {
            try {
                Thread.sleep(looptime);
                Log.d(TAG, "sdcard路径是:" + sdcardpath);
                //boolean hasPermissoon = PermissionsUtil.getInstance().checkPermissions(MyApplication.getInstance(), permissions);
                boolean hasPermissoon = true;
                if (hasPermissoon) {
                    this.scannerlog(sdcardpath);
                }else{
                    Log.d(TAG, "没有权限");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /*
     *#解析log日志
     *@retrun 拼接完成的json数据
     */
    public JSONObject reslovefile(String filename) throws FileNotFoundException {

        List keylist = new ArrayList();
        keylist.add("versionName");
        keylist.add("imei");
        keylist.add("uid");
        keylist.add("network");
        keylist.add("model");
        keylist.add("api-level");
        keylist.add("cpu-core");
        keylist.add("process");
        keylist.add("freeMemory");
        keylist.add("totalMemory");
        keylist.add("time");
        keylist.add("thread-time");
        keylist.add("time-start");
        keylist.add("time-end");
        keylist.add("cpu-busy");
        keylist.add("cpu-rate");
        keylist.add("stack");

        JSONObject body = new JSONObject();
        FileReader fr = new FileReader(filename);
        BufferedReader r = new BufferedReader(fr);

        String result = "";
        String line = null;

        try {
            while ((line = r.readLine()) != null) {
                for (Object key : keylist) {
                    if (key == "stack") {
                        if (fileTools.readlines(filename, "stack") != "") {
                            body.put("stack", fileTools.readlines(filename, "stack"));
                        }
                    } else if (line.startsWith(key.toString())) {
                        String[] splitArray = line.split(" = ");
                        if (splitArray.length > 1) {
                            body.put(splitArray[0], splitArray[1]);
                        } else {
                            body.put(splitArray[0], "");
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Throwable te) {
            te.printStackTrace();
        } finally {
            Log.d(TAG, "body:" + body);
            return body;
        }
    }


    /*
     *在sdcard扫描block的log日志
     */
    public void scannerlog(String sdcardpath) {
        BlockUploadService service = new BlockUploadService();
        List filelist = fileTools.getFiles(sdcardpath);
        try {
            if (filelist.size() != 0) {
                for (Object filepath : filelist) {
                    JSONObject body = this.reslovefile(filepath.toString());
                    if (body.length()!=0){
                        if (service.reqBlockInfo(body).toString().contains("ok")) {
                            fileTools.delFiles(filepath.toString());
                            Log.d(TAG, String.format("上传日志:%s成功", filepath));
                        } else {
                            Log.d(TAG, String.format("上传日志:%s失败", filepath));
                            continue;
                        }
                    }
                }
            } else {
                Log.d(TAG, sdcardpath + "未找到文件!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
