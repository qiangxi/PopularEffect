package com.qiangxi.populareffect.view;
/*
 * Copyright © qiangxi(任强强)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by qiangxi(任强强) on 2017/9/22.
 * <p>
 * 越界回弹ScrollView，采用offsetTopAndBottom() + ValueAnimator实现<br/>
 * - 支持阻尼系数<br/>
 * - 支持多指触控<br/>
 * - 支持上拉回弹、下拉回弹<br/>
 * <p>
 * 在子view较多时，快速滑动时，顶部或底部几率性空白（待修复）
 */

public class BounceScrollView extends ScrollView {
    private static final int ENABLED_ALL = 0;//默认
    private static final int ENABLED_TOP = 1;
    private static final int ENABLED_BOTTOM = 2;
    private static final int ENABLED_NONE = 3;

    private static final float arg = 1.01F;//加重阻尼效果，辅助手段

    private int mLastY;
    private int mActivePointerId;
    private View mChild;
    private boolean canPullDown;//是否可以下拉
    private boolean canPullUp;//是否可以上拉
    private int mLastFrameValue;//上一帧的值
    private ValueAnimator mAnimator;

    private int mBounceType = ENABLED_ALL;

    private float mBounceFraction = 0.6F;//阻尼系数 介于0~1之间，值越大，阻力越大

    private int maxDistance;//最大滑动距离，默认ScrollView的高度

    private TimeInterpolator mInterpolator;//插值器，默认OvershootInterpolator

    public BounceScrollView(Context context) {
        this(context, null);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mInterpolator = new OvershootInterpolator();
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(400).setInterpolator(mInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int animatedValue = (int) animation.getAnimatedValue();
                final int perFrameValue = animatedValue - mLastFrameValue;
                //mChild每次只移动每一帧的值
                mChild.offsetTopAndBottom(perFrameValue);
                mLastFrameValue = animatedValue;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChild = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) maxDistance = getHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mAnimator.isStarted()) mAnimator.cancel();
                mActivePointerId = ev.getPointerId(0);
                mLastY = (int) ev.getY();
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();
                break;
            case MotionEvent.ACTION_MOVE:
                final int y = (int) ev.getY(ev.findPointerIndex(mActivePointerId));
                int diffY = y - mLastY;
                if ((canPullUp || canPullDown)) {
                    ViewParent parent = getParent();
                    if (parent != null) parent.requestDisallowInterceptTouchEvent(true);
                    move(diffY);
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (canPullDown || canPullUp) {
                    final int scrollY = mChild.getTop();
                    mLastFrameValue = scrollY;
                    mAnimator.setIntValues(scrollY, 0);
                    mAnimator.start();
                }
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int downActionIndex = ev.getActionIndex();
                mLastY = (int) ev.getY(downActionIndex);
                mActivePointerId = ev.getPointerId(downActionIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int upActionIndex = ev.getActionIndex();
                final int pointerId = ev.getPointerId(upActionIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = upActionIndex == 0 ? 1 : 0;
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                mLastY = (int) ev.getY(ev.findPointerIndex(mActivePointerId));
                break;
        }
        super.dispatchTouchEvent(ev);//分发父view的事件
        return true;
    }

    private void move(int diffY) {
        if (canPullDown && diffY > 0) {
            int childTop = mChild.getTop();
            if (childTop <= maxDistance) {
                //阻尼系数：计算规则y=kx+b
                float k = diffY * (1 - mBounceFraction);
                float x = 1 - (childTop * arg / maxDistance);
                diffY = (int) (k * x);
                mChild.offsetTopAndBottom(diffY);
            }
        }
        if (canPullUp && diffY < 0) {
            int childBottom = mChild.getBottom() - mChild.getHeight();
            int absChildBottom = Math.abs(childBottom);
            if (absChildBottom <= maxDistance) {
                //阻尼系数：计算规则y=kx+b
                float k = diffY * (1 - mBounceFraction);
                float x = 1 - (absChildBottom * arg / maxDistance);
                diffY = (int) (k * x);
                mChild.offsetTopAndBottom(diffY);
            }
        }
    }

    /**
     * 是否可以下拉
     */
    private boolean isCanPullDown() {
        if (mBounceType == ENABLED_NONE || mBounceType == ENABLED_BOTTOM) return false;
        return mChild != null && getScrollY() == 0;
    }

    /**
     * 是否可以上拉
     */
    private boolean isCanPullUp() {
        if (mBounceType == ENABLED_NONE || mBounceType == ENABLED_TOP) return false;
        if (mChild == null) return false;
        boolean isCanPullUp = true;
        if (mChild.getHeight() - getHeight() > 0) {
            isCanPullUp = mChild.getHeight() - getHeight() == getScrollY();
        }
        return isCanPullUp;
    }

    public float getBounceFraction() {
        return mBounceFraction;
    }

    public void setBounceFraction(@FloatRange(from = 0, to = 1) float bounceFraction) {
        mBounceFraction = bounceFraction;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(@IntRange(from = 0) int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        if (interpolator == null || interpolator == mAnimator.getInterpolator()) return;
        mAnimator.setInterpolator(interpolator);
        mInterpolator = interpolator;
    }

    public int getBounceType() {
        return mBounceType;
    }

    public void setBounceType(@BounceType int bounceType) {
        mBounceType = bounceType;
    }

    /**
     * 必要时调用该方法销毁mAnimator，防止内存泄露
     */
    public void destroy() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }


    @IntDef({ENABLED_ALL, ENABLED_TOP, ENABLED_BOTTOM, ENABLED_NONE})
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    public @interface BounceType {
    }
}
