package com.example.xinxi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by xinxi on 2018/3/29.
 */

public class JumpActivity extends AppCompatActivity {
    public Button button1;
    String TAG = "JumpActivity";
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        Toolbar toolbar=(Toolbar)findViewById(R.id.towbar);
        //需要调用该函数才能设置toolbar的信息
        this.setSupportActionBar(toolbar);
        //显示向上箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置title，我直接用toolbar会出错
        getSupportActionBar().setTitle(TAG);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        addListenerOnButton1();
    }

    private void setNavigationOnClickListener(){

    }


    private void addListenerOnButton1() {
        button1 = (Button)findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "JumpActivity back");
                Intent intent = new Intent();
                intent.setClass(JumpActivity.this,MainActivity.class);
                startActivity(intent);

            }

        });


    }

    private void addListenerOnButton2() {
        button1 = (Button)findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "JumpActivity button");
                Intent intent = new Intent();
                intent.setClass(JumpActivity.this,MainActivity.class);
                startActivity(intent);

            }

        });


    }



}
