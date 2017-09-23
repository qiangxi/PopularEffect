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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.qiangxi.populareffect.utli.ScreenUtils;

/**
 * Created by qiang_xi on 2016/10/31 20:21.
 * 让指定view的背景颜色跟随滑动渐变的ListView
 */

public class FadeListView extends ListView {
    private View mView;
    private int mColorResId = -1;
    private float mOffsetY;
    private static final int DEFAULT_OFFSET = 150;//默认Y轴方向滑动的限定值,dp
    private View firstHeadView;

    public FadeListView(Context context) {
        super(context);
    }

    public FadeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != mView && mColorResId != -1) {
            int offsetY = Math.abs(firstHeadView.getTop());
            if (offsetY <= mOffsetY) {
                mView.setBackgroundColor(mColorResId);
                mView.setAlpha(offsetY / mOffsetY);
            }
        }
    }

    /**
     * 绑定目标view
     *
     * @param fadeView      要实现颜色渐变效果的view
     * @param firstHeadView listView的第一个headView
     * @param colorResId    要进行渐变的颜色
     */
    public void bindView(View fadeView, View firstHeadView, int colorResId) {
        bindView(fadeView, firstHeadView, colorResId, ScreenUtils.dpToPx(getContext(), DEFAULT_OFFSET));
    }

    /**
     * 绑定目标view
     *
     * @param fadeView      要实现颜色渐变效果的view
     * @param firstHeadView listView的第一个headView
     * @param colorResId    要进行渐变的颜色
     * @param offsetY       Y轴方向滑动的限定值,颜色渐变在0到offsetY之间渐变,单位:px
     */
    public void bindView(View fadeView, View firstHeadView, int colorResId, float offsetY) {
        this.mView = fadeView;
        this.firstHeadView = firstHeadView;
        this.mColorResId = colorResId;
        this.mOffsetY = offsetY;
    }
}
