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
    private int mFadeColor;
    private float mOffsetY;
    private static final int DEFAULT_OFFSET = 150;//默认Y轴方向滑动的限定值

    public FadeScrollView(Context context) {
        this(context, null);
    }

    public FadeScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FadeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FadeScrollView);
        mFadeColor = a.getColor(R.styleable.FadeScrollView_fadeColor, context.getResources().getColor(R.color.colorPrimary));
        mOffsetY = a.getDimension(R.styleable.FadeScrollView_fadeHeight, dpToPx(100));
        int viewId = a.getResourceId(R.styleable.FadeScrollView_fadeView, -1);
        if (viewId != -1) {
            mView = findViewById(viewId);
            setFadeView(mView, mFadeColor, mOffsetY);
        }
        a.recycle();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("tag", "t =" + t);
        if (null != mView) {
            if (t <= mOffsetY) {
                mView.setAlpha(t / mOffsetY);
            } else {
                mView.setAlpha(1);
            }
        }
    }

    /**
     * 绑定目标view
     *
     * @param view       要实现颜色渐变效果的view
     * @param colorResId 要进行渐变的颜色
     */
    public void setFadeView(View view, int colorResId) {
        setFadeView(view, colorResId, ScreenHelper.dpToPx(getContext(), DEFAULT_OFFSET));
    }

    /**
     * 绑定目标view
     *
     * @param view       要实现颜色渐变效果的view
     * @param colorResId 要进行渐变的颜色
     * @param offsetY    Y轴方向滑动的限定值,颜色渐变在0到offsetY之间渐变,单位:px
     */
    public void setFadeView(View view, int colorResId, float offsetY) {
        this.mView = view;
        this.mFadeColor = colorResId;
        this.mOffsetY = offsetY;
        mView.setBackgroundColor(mFadeColor);
        mView.setAlpha(0);
    }

    private int dpToPx(int dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }
}
