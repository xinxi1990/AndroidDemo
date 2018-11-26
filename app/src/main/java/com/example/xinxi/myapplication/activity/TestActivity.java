package com.example.xinxi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView textView = (TextView) findViewById(R.id.test_text_view);

        TestDataModel.getInstance().setRetainedTextView(textView);
        //内存泄漏

        //TestDataModel testDataModel = new  TestDataModel();
        //testDataModel.setRetainedTextView(textView);
        //不内存泄漏
    }
}