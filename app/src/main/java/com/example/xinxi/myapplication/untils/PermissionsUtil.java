package com.example.xinxi.myapplication.untils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;



import java.util.ArrayList;
import java.util.List;

public class PermissionsUtil {
    private AlertDialog settingDialog;
    private boolean settingDialogShown;
    private boolean gotoSetting;

    private static class PermissionsHolder {
        private final static PermissionsUtil instance = new PermissionsUtil();
    }

    private PermissionsUtil() {

    }

    public static PermissionsUtil getInstance() {
        return PermissionsHolder.instance;
    }

    public void dimissAllDialog() {
        if (settingDialog != null && settingDialog.isShowing()) {
            settingDialog.dismiss();
        }
    }

    public void resetGotoSetting() {
        gotoSetting = false;
    }

    public boolean isGotoSetting() {
        return gotoSetting;
    }

    public boolean isSettingDialogShown() {
        return settingDialogShown;
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param
     * @return
     */
    public boolean checkPermissions(@NonNull Context context, @NonNull String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param
     * @return
     */
    public boolean checkPermissions(@NonNull Context context, @NonNull String permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(context, permissions) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    public List<String> getDeniedPermissions(@NonNull Context context, @NonNull String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


    /**
     * 再次申请权限弹窗，条件：拒绝过权限申请且点击了"不再询问"
     *
     * @param context         Context
     * @param permissionsName 需求的权限名
     */
    public void showSettingPermissionsDialo(final Context context, String[] permissionsName) {
        showSettingPermissionsDialo(context, permissionsName, null);
    }

    /**
     * 再次申请权限弹窗，条件：拒绝过权限申请且点击了"不再询问"
     *
     * @param context         Context
     * @param permissionsName 需求的权限名
     */
    public void showSettingPermissionsDialo(final Context context, String[] permissionsName, final OnGoPermissionSettingListener listener) {
        if (context == null || permissionsName == null) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < permissionsName.length; i++) {
            String permisssionName = getPermissionsName(permissionsName[i]);
            buffer.append(permisssionName).append(",");
        }
        String permissions = buffer.toString();
        if (permissions.length() > 0 && permissions.endsWith(",")) {
            permissions = permissions.substring(0, permissions.length() - 1);
        }
    }


    /**
     * 启动当前应用设置页面
     *
     * @param context
     */
    private void startAppSettings(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 获取权限组名
     *
     * @param manifestPermissionsName manifestPermissionsName
     * @return 权限组名
     */
    private String getPermissionsName(@NonNull String manifestPermissionsName) {
        String permissionsname = "";
        if (TextUtils.isEmpty(manifestPermissionsName)) {
            return permissionsname;
        }
        switch (manifestPermissionsName) {
            case Manifest.permission.READ_CALENDAR:
            case Manifest.permission.WRITE_CALENDAR:
                permissionsname = "日历";
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                permissionsname = "存储";
                break;
            case Manifest.permission.CAMERA:
                permissionsname = "相机";
                break;
            case Manifest.permission.READ_CONTACTS:
            case Manifest.permission.WRITE_CONTACTS:
            case Manifest.permission.GET_ACCOUNTS:
                permissionsname = "通讯录";
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                permissionsname = "位置";
                break;
            case Manifest.permission.RECORD_AUDIO:
                permissionsname = "麦克风";
                break;
            case Manifest.permission.BODY_SENSORS:
                permissionsname = "传感器";
                break;
            case Manifest.permission.READ_PHONE_STATE:
            case Manifest.permission.CALL_PHONE:
            case Manifest.permission.READ_CALL_LOG:
            case Manifest.permission.WRITE_CALL_LOG:
            case Manifest.permission.ADD_VOICEMAIL:
            case Manifest.permission.USE_SIP:
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                permissionsname = "电话";
                break;
            case Manifest.permission.SEND_SMS:
            case Manifest.permission.RECEIVE_SMS:
            case Manifest.permission.READ_SMS:
            case Manifest.permission.RECEIVE_WAP_PUSH:
            case Manifest.permission.RECEIVE_MMS:
                permissionsname = "短信";
                break;
            default:
                permissionsname = "";
                break;
        }
        return permissionsname;
    }

    public interface OnGoPermissionSettingListener {
        void onCancel();
    }


}
