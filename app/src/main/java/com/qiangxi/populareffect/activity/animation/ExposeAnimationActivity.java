package com.qiangxi.populareffect.activity.animation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.base.BaseActivity;
import com.qiangxi.populareffect.databinding.ActivityExposeAnimationBinding;

import immortalz.me.library.TransitionsHeleper;

/**
 * 揭露动画效果
 * 本效果直接使用的是github上的一个开源工具库,地址:https://github.com/ImmortalZ/TransitionHelper.
 */
public class ExposeAnimationActivity extends BaseActivity {
    private ActivityExposeAnimationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ExposeAnimationActivity");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_expose_animation);
    }

    public void animate(View view) {
        TransitionsHeleper.startAcitivty(ExposeAnimationActivity.this, ExposeAnimationActivity2.class, mBinding.button);
    }
}
