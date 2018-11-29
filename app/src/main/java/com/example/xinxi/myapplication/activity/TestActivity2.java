package com.example.xinxi.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.xinxi.myapplication.R;
import com.example.xinxi.myapplication.model.TestDataModel;


public class TestActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView textView = (TextView) findViewById(R.id.test_text_view);
        TestDataModel testDataModel = new  TestDataModel();
        testDataModel.setRetainedTextView(textView);
    }
}