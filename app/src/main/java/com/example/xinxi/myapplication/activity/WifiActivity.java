package com.example.xinxi.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.xinxi.myapplication.R;
import com.example.xinxi.myapplication.info.WifiConnector;
import com.example.xinxi.myapplication.info.WifiConnector.WifiCipherType;


public class WifiActivity  extends AppCompatActivity {

    String TAG = "WifiActivity ";
    Button btnConnect;
    WifiManager wifiManager;
    WifiConnector wac;
    TextView textView1;
    EditText editPwd;
    EditText editSSID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        textView1 = (TextView) findViewById(R.id.txtMessage);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wac = new WifiConnector(wifiManager);

        editPwd=(EditText) findViewById(R.id.editPwd);
        editSSID=(EditText) findViewById(R.id.editSSID);

        wac.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 操作界面
                textView1.setText(textView1.getText()+"\n"+msg.obj+"");
                super.handleMessage(msg);
            }
        };
        btnConnect.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    wac.connect(editSSID.getText().toString(), editPwd.getText().toString(),
                            editPwd.getText().toString().equals("")?WifiCipherType.WIFICIPHER_NOPASS:WifiCipherType.WIFICIPHER_WPA);
                    Log.d(TAG,"SSID: "+editSSID.getText().toString());
                    Log.d(TAG,"pwd: " + editPwd.getText().toString());
                    Log.d(TAG,"type: " + (editPwd.getText().toString().equals("")?WifiCipherType.WIFICIPHER_NOPASS:WifiCipherType.WIFICIPHER_WPA).toString());

                } catch (Exception e) {
                    textView1.setText(e.getMessage());
                }

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }



}

