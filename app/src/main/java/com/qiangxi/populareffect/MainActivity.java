package com.qiangxi.populareffect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.title);
        mScrollView = (ScrollView) findViewById(R.id.activity_main);

    }
}
