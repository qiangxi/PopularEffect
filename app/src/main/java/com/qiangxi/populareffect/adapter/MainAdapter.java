package com.qiangxi.populareffect.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.bean.MainItemInfo;

import java.util.List;

/**
 * 作者 任强强 on 2016/10/31 12:24.
 */

public class MainAdapter extends RecyclerArrayAdapter<MainItemInfo> {

    public MainAdapter(Context context) {
        super(context);
    }

    public MainAdapter(Context context, List<MainItemInfo> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(parent, R.layout.item_main_recycler_view_layout);
    }

    public class MainViewHolder extends BaseViewHolder<MainItemInfo> {
        private TextView mTextView;

        public MainViewHolder(View itemView) {
            super(itemView);
        }

        public MainViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            mTextView = $(R.id.item_main_desc);
        }

        @Override
        public void setData(MainItemInfo info) {
            super.setData(info);
            mTextView.setText(info.getItemDesc());
        }
    }
}
