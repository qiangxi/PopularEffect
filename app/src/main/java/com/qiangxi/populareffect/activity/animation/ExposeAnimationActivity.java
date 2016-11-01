package com.qiangxi.populareffect.activity.animation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import immortalz.me.library.TransitionsHeleper;

/**
 * 揭露动画效果
 * 本效果直接使用的是github上的一个开源工具库,地址:https://github.com/ImmortalZ/TransitionHelper.
 */
public class ExposeAnimationActivity extends BaseActivity {

    @Bind(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expose_animation);
        setTitle("ExposeAnimationActivity");
        ButterKnife.bind(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionsHeleper.startAcitivty(ExposeAnimationActivity.this, ExposeAnimationActivity2.class, mButton);
            }
        });
    }
}
