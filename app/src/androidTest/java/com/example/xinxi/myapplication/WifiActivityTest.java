package com.example.xinxi.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.uiautomator.Until.findObject;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by xinxi on 2018/5/8.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class WifiActivityTest{
    private UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;
    private final String BASIC_SAMPLE_PACKAGE = "";

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // 启动com.example.xinxi.myapplication
        Context context = InstrumentationRegistry.getContext();
        Intent launchIntent = new Intent();
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        launchIntent.setComponent(new ComponentName("com.example.xinxi.myapplication", "com.example.xinxi.myapplication.activity.WifiActivity"));
        context.startActivity(launchIntent);
    }

    @Test
    public void testwifi() {
        try {
            Thread.sleep(4000);
            UiObject2 wifi_button = mDevice.wait(findObject(By.res("com.example.xinxi.myapplication", "btnConnect")), 2000);
            wifi_button.click();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
