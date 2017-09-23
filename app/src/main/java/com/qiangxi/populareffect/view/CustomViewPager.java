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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by qiangxi(任强强) on 2017/9/22.
 * <p>
 * 自定义viewPager，实现效果：<br/>
 * - 当页面已经处于第一页再继续向右滑动时<br/>
 * - 当页面已经处于最后一页再继续向左滑动时<br/>
 * -> 触发相应回调<br/>
 * 该效果有两种常见的使用场景：<br/>
 * 1. 用于APP启动页，当处于最后一页时，再继续向左滑动，跳转到主界面（大部分app的启动页都基本有这个效果）<br/>
 * 2. 用于轮播图，当轮播图处于最后一页或第一页时，再继续向左或向右滑动，则跳转到其他的tab或界面中。（如建行首页的轮播图，掘金的顶部tab等）<br/>
 * </p>
 */
public class CustomViewPager extends ViewPager {
    private int mLastX;
    private OnEdgeListener mEdgeListener;
    private boolean isFirstPageEdge;
    private boolean isLastPageEdge;
    private int mTouchSlop;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        PagerAdapter adapter = getAdapter();
        if (adapter == null) return super.onTouchEvent(ev);
        int currentItem = getCurrentItem();
        int count = adapter.getCount();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = ev.getX() - mLastX;
                if (Math.abs(diffX) < mTouchSlop) break;
                dispatchEdge(currentItem, count, diffX);
                break;
            case MotionEvent.ACTION_UP:
                dispatchListener(currentItem);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void dispatchListener(int currentItem) {
        if (mEdgeListener != null) {
            if (isFirstPageEdge) {
                mEdgeListener.onFirstPageEdge(currentItem);
            }
            if (isLastPageEdge) {
                mEdgeListener.onLastPageEdge(currentItem);
            }
        }
        isFirstPageEdge = false;
        isLastPageEdge = false;
    }

    private void dispatchEdge(int currentItem, int count, float diffX) {
        //第一页
        if (currentItem == 0) {
            isFirstPageEdge = diffX > 0;
        } else {
            isFirstPageEdge = false;
        }
        //最后一页
        if (currentItem == count - 1) {
            isLastPageEdge = diffX <= 0;
        } else {
            isLastPageEdge = false;
        }
    }

    public void setOnEdgeListener(OnEdgeListener listener) {
        mEdgeListener = listener;
    }

    public interface OnEdgeListener {
        /**
         * 当页面已经处于第一页再继续向右滑动,当手指抬起时，触发回调
         *
         * @param position 第一页位置
         */
        void onFirstPageEdge(int position);

        /**
         * 当页面已经处于最后一页再继续向左滑动时,当手指抬起时，触发回调
         *
         * @param position 最后一页位置
         */
        void onLastPageEdge(int position);
    }
}
