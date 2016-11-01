package com.qiangxi.populareffect.activity.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.adapter.MainAdapter;
import com.qiangxi.populareffect.base.BaseActivity;
import com.qiangxi.populareffect.bean.MainItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewFadeActivity extends BaseActivity {

    @Bind(R.id.fadeRecyclerView)
    EasyRecyclerView mFadeRecyclerView;
    @Bind(R.id.recyclerViewTitleLayout)
    RelativeLayout mRecyclerViewTitleLayout;

    private List<MainItemInfo> mList = new ArrayList<>();
    private MainAdapter mAdapter;
    private static final float DEFAULT_OFFSET = 900;//默认Y轴方向滑动的限定值,px

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_fade);
        ButterKnife.bind(this);
        setTranslucentStatus(true);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mList.add(new MainItemInfo("item:  " + i, null));
        }
        mAdapter = new MainAdapter(this, mList);
        //头部
        final ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.banner_bg);
        imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 900));
        //添加头部
        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return imageView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mFadeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFadeRecyclerView.addItemDecoration(new DividerDecoration(Color.RED, 1));
        mFadeRecyclerView.setAdapter(mAdapter);
        mFadeRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //实时获取头部view偏移量
                int offsetY = Math.abs(imageView.getTop());
                if (offsetY <= DEFAULT_OFFSET) {
                    mRecyclerViewTitleLayout.setBackgroundColor(Color.GREEN);
                    mRecyclerViewTitleLayout.setAlpha(offsetY / DEFAULT_OFFSET);
                }
            }
        });
    }
}
