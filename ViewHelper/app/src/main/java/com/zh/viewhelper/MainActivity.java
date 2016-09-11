package com.zh.viewhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zh.viewhelperlib.Bind;
import com.zh.viewhelperlib.OnClick;
import com.zh.viewhelperlib.ViewHelper;

public class MainActivity extends Activity {
    @Bind(R.id.tv_1)
    TextView tv1;

    @Bind(R.id.btn_test)
    Button btnTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewHelper.bind(this);
    }

    @OnClick(R.id.btn_test)
    private void onTest(View view){
        tv1.setText("这是一个测试文本");
    }



}
