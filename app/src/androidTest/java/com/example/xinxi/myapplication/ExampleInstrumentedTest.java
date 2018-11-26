package com.example.xinxi.myapplication;

import android.support.v7.app.AppCompatActivity;
import com.example.xinxi.myapplication.WifiConnector;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest  extends AppCompatActivity {
    WifiConnector wac;
    WifiManager wifiManager;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.xinxi.myapplication", appContext.getPackageName());
    }

    @Test
    public void testwifi() throws Exception{
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wac = new WifiConnector(wifiManager);
        wac.connect("FBI","",null);

    }


}
