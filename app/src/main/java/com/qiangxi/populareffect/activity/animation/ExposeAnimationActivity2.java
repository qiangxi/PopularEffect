package com.qiangxi.populareffect.activity.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.base.BaseActivity;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;

public class ExposeAnimationActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expose_animation2);
        setTitle("ExposeAnimationActivity2");
        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.colorAccent, R.color.colorAccent) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(copyView, "rotation", 0, 180),
                                ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(duration / 4 * 5).start();
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                    }
                })
                .show(this, null);
    }
}
