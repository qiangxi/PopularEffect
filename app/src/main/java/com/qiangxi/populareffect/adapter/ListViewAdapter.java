package com.qiangxi.populareffect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qiangxi.populareffect.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qiang_xi on 2016/10/31 23:21.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ListViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_recycler_view_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemMainDesc.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_main_desc)
        TextView itemMainDesc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
