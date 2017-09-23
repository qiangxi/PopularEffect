package com.qiangxi.populareffect.activity.viewpager;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.databinding.ActivityCustomViewPagerBinding;
import com.qiangxi.populareffect.utli.T;
import com.qiangxi.populareffect.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class CustomViewPagerActivity extends AppCompatActivity
        implements CustomViewPager.OnEdgeListener {
    private String[] arr = {"第一页", "第二页", "第三页", "最后一页"};
    private List<View> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustomViewPagerBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_custom_view_pager);
        binding.viewPager.setOnEdgeListener(this);
        binding.viewPager.setAdapter(new CustomPagerAdapter());
        mViews = generateViews();
    }

    private List<View> generateViews() {
        List<View> views = new ArrayList<>();
        for (String text : arr) {
            TextView view = new TextView(this);
            view.setGravity(Gravity.CENTER);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setText(text);
            view.setBackgroundColor(Color.WHITE);
            views.add(view);
        }
        return views;
    }

    @Override
    public void onFirstPageEdge(int position) {
        T.show(this, "onFirstPageEdge,position=" + position);
    }

    @Override
    public void onLastPageEdge(int position) {
        T.show(this, "onLastPageEdge,position=" + position);
    }

    private class CustomPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public int getCount() {
            return arr.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
