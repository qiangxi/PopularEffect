package com.qiangxi.populareffect.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.qiangxi.populareffect.R;

public class BounceScrollViewAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_scroll_view_acivity);
    }

    public void click(View view) {
        Log.e("tag", "click点击事件");
    }
}
