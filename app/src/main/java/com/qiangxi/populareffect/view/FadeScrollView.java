package com.qiangxi.populareffect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.qiangxi.populareffect.utli.ScreenUtils;

/**
 * 作者 qiang_xi on 2016/10/31 17:37.
 * 让指定view的背景颜色跟随滑动渐变的ScrollView
 */

public class FadeScrollView extends ScrollView {
    private View mView;
    private int mColorResId = -1;
    private float mOffsetY;
    private static final int DEFAULT_OFFSET = 150;//默认Y轴方向滑动的限定值

    public FadeScrollView(Context context) {
        super(context);
    }

    public FadeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != mView && mColorResId != -1) {
            if (t <= mOffsetY) {
                mView.setBackgroundColor(mColorResId);
                mView.setAlpha(t / mOffsetY);
            }
        }
    }

    /**
     * 绑定目标view
     *
     * @param view       要实现颜色渐变效果的view
     * @param colorResId 要进行渐变的颜色
     */
    public void bindView(View view, int colorResId) {
        bindView(view, colorResId, ScreenUtils.dpToPx(getContext(), DEFAULT_OFFSET));
    }

    /**
     * 绑定目标view
     *
     * @param view       要实现颜色渐变效果的view
     * @param colorResId 要进行渐变的颜色
     * @param offsetY    Y轴方向滑动的限定值,颜色渐变在0到offsetY之间渐变,单位:px
     */
    public void bindView(View view, int colorResId, float offsetY) {
        this.mView = view;
        this.mColorResId = colorResId;
        this.mOffsetY = offsetY;
    }
}
