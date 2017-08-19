package com.bawei.lvwenjing.dliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bawei.lvwenjing.dliao.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by lenovo-pc on 2017/7/10.
 */

public class Fragment_fourth_Adapter extends BaseAdapter {
    List<String> list;
    Context context;
    private InforClass inforClass;

    public Fragment_fourth_Adapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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
        if (convertView == null) {
            inforClass = new InforClass();
            convertView = View.inflate(context, R.layout.fragment_fourth_gvitme, null);
            inforClass.iv = (ImageView) convertView.findViewById(R.id.fragment_fourth_show_photo);
            convertView.setTag(inforClass);
        } else {
            inforClass = (InforClass) convertView.getTag();
        }
        Glide.with(context).load(list.get(position)).into(inforClass.iv);

        return convertView;
    }

    class InforClass {
        ImageView iv;
    }
}
