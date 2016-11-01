package com.qiangxi.populareffect.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.view.SwipeBackLayout;

public class SwipeBackActivity extends AppCompatActivity {

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back);
        mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipe_back_layout, null);
        mSwipeBackLayout.attachToActivity(this);
    }
}
