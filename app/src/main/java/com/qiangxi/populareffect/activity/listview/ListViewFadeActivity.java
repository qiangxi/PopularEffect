package com.qiangxi.populareffect.activity.listview;

import android.os.Bundle;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.base.BaseActivity;

public class ListViewFadeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_fade);
        setTranslucentStatus(true);
    }
}
