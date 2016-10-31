package com.qiangxi.populareffect.activity.recyclerview;

import android.os.Bundle;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.base.BaseActivity;

public class RecyclerViewFadeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_fade);
        setTranslucentStatus(true);
    }
}
