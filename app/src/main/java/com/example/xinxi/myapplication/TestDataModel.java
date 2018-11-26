package com.example.xinxi.myapplication;

import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TestDataModel {

    private static TestDataModel sInstance;
    private TextView mRetainedTextView;

    public static TestDataModel getInstance() {
        if (sInstance == null) {
            sInstance = new TestDataModel();
        }
        return sInstance;
    }

    public void setRetainedTextView(TextView textView) {

        mRetainedTextView = textView;
    }
}

