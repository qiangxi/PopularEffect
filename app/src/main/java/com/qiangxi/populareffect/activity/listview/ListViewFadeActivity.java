package com.qiangxi.populareffect.activity.listview;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.adapter.ListViewAdapter;
import com.qiangxi.populareffect.base.BaseActivity;
import com.qiangxi.populareffect.view.FadeListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewFadeActivity extends BaseActivity {

    @Bind(R.id.fadeListView)
    FadeListView fadeListView;
    @Bind(R.id.listViewTitleLayout)
    RelativeLayout listViewTitleLayout;
    ListViewAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_fade);
        ButterKnife.bind(this);
        setTranslucentStatus(true);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            list.add("item:  " + i);
        }
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.banner_bg);
        imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 900));
        fadeListView.addHeaderView(imageView);
        adapter = new ListViewAdapter(this, list);
        fadeListView.setAdapter(adapter);
        fadeListView.bindView(listViewTitleLayout, imageView, Color.BLUE, 900);
    }
}
