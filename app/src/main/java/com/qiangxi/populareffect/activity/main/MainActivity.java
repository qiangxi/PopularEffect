package com.qiangxi.populareffect.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.activity.animation.ExposeAnimationActivity;
import com.qiangxi.populareffect.activity.animation.LayoutAnimationActivity;
import com.qiangxi.populareffect.activity.listview.ListViewFadeActivity;
import com.qiangxi.populareffect.activity.recyclerview.RecyclerViewFadeActivity;
import com.qiangxi.populareffect.activity.scrollview.ScrollViewFadeActivity;
import com.qiangxi.populareffect.activity.slidingpanelayout.SlidingPaneLayoutActivity;
import com.qiangxi.populareffect.adapter.MainAdapter;
import com.qiangxi.populareffect.base.BaseActivity;
import com.qiangxi.populareffect.bean.MainItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RecyclerArrayAdapter.OnItemClickListener {

    @Bind(R.id.recycleView)
    EasyRecyclerView mRecycleView;
    private MainAdapter mAdapter;
    private List<MainItemInfo> mList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        //滑动时,view元素发生位移的效果待定
        mList.add(new MainItemInfo("ScrollView滑动,标题栏颜色渐变", ScrollViewFadeActivity.class));
        mList.add(new MainItemInfo("ScrollView滑动,Child(0)缩放", ScrollViewFadeActivity.class));
        mList.add(new MainItemInfo("ListView滑动,标题栏颜色渐变", ListViewFadeActivity.class));
        mList.add(new MainItemInfo("ListView滑动,头部子view缩放", ScrollViewFadeActivity.class));
        mList.add(new MainItemInfo("RecyclerView滑动,标题栏颜色渐变", RecyclerViewFadeActivity.class));
        mList.add(new MainItemInfo("RecyclerView滑动,头部子view缩放", ScrollViewFadeActivity.class));
        mList.add(new MainItemInfo("以所点击的view为中心向外逐渐扩散的方式打开新界面", ExposeAnimationActivity.class));
        mList.add(new MainItemInfo("V4.SlidingPaneLayoutActivity", SlidingPaneLayoutActivity.class));
        mList.add(new MainItemInfo("布局动画", LayoutAnimationActivity.class));
    }

    private void initView() {
        mAdapter = new MainAdapter(this, mList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration decoration = new DividerDecoration(Color.RED, 1);
        decoration.setDrawHeaderFooter(true);
        mRecycleView.addItemDecoration(decoration);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView textView = new TextView(MainActivity.this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setText(getString(R.string.attention));
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(16);
                return textView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, mList.get(position).getActicityClass());
        startActivity(intent);
    }
}
