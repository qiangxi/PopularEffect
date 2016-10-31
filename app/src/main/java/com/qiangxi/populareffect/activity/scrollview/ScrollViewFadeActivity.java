package com.qiangxi.populareffect.activity.scrollview;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.base.BaseActivity;
import com.qiangxi.populareffect.view.FadeScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ScrollView滑动,标题栏颜色渐变.
 */
public class ScrollViewFadeActivity extends BaseActivity {

    @Bind(R.id.scrollViewTitleLayout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.fadeScrollView)
    FadeScrollView mFadeScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_fade);
        ButterKnife.bind(this);
        setTranslucentStatus(true);
        mFadeScrollView.bindView(mTitleLayout, Color.RED);
    }
}
