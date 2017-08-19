package com.bawei.lvwenjing.dliao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.DataBean;
import com.bawei.lvwenjing.dliao.utils.PreferencesUtils;
import com.bawei.lvwenjing.dliao.widget.Distance;
import com.bumptech.glide.Glide;
import java.util.List;


public class Fragment_frist_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    List<DataBean> list;
    Context context;
    private int type = 0;

    public Fragment_frist_Adapter(List<DataBean> list, Context context, int type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    //点击切换布局的时候通过这个方法设置type
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragmentfrist_itme, null);
            viewHolder = new Fristhader(view);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_frist_pubu, null);
            viewHolder = new SecondHadre(view);

        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == 0) {
            Fristhader vholder = (Fristhader) holder;
            Glide.with(context).load(list.get(position).getImagePath()).into(vholder.iv);
            vholder.tilte.setText("昵称：" + list.get(position).getNickname());
            vholder.sex_age.setText("年龄：" + list.get(position).getAge() + " ,性别： " + list.get(position).getGender());
            vholder.infor.setText("签名：" + list.get(position).getIntroduce());
            vholder.lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(position, v);
                }
            });
            //系统的点击事件
            vholder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //当点击时：调用接口的方法
                    listener.onItemClickListener(position, v);
                }
            });
        } else {
            SecondHadre vholder = (SecondHadre) holder;
            Glide.with(context).load(list.get(position).getImagePath()).into(vholder.fragment_frist_imageview);
            //系统的点击事件
            vholder.fragment_frist_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //当点击时：调用接口的方法
                    listener.onItemClickListener(position, v);
                }
            });
            String lat = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lat", "0");
            String lng = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lng", "0");
            double olat = list.get(position).getLat();
            double olng = list.get(position).getLng();
            if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0) {
                double dlat = Double.valueOf(lat);
                double dlng = Double.valueOf(lng);
                int v = (int) ((olat - dlat) * (olat - dlat) + (olng - dlat) * (olng - dlat));
                double v1 = Distance.getInstance().LantitudeLongitudeDist(olng, olat, dlng, dlat);
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");
                String format = df.format(v1);
                Math.sqrt(v);
                vholder.masonry_item_title.setText("距离" + format + "公里");
            } else {
                vholder.masonry_item_title.setText("距离" + "1000" + "公里");
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Fristhader extends RecyclerView.ViewHolder {

        private final TextView infor;
        private final TextView sex_age;
        private final TextView tilte;
        private final ImageView iv;
        private final LinearLayout lin;

        public Fristhader(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.fragment_frist_title_iv);
            tilte = (TextView) itemView.findViewById(R.id.fragment_frist_title_name);
            sex_age = (TextView) itemView.findViewById(R.id.fragment_frist_title_sex_age);
            infor = (TextView) itemView.findViewById(R.id.fragment_frist_title_sex_infor);
            lin = (LinearLayout) itemView.findViewById(R.id.frist_adapter_line);
        }


    }

    class SecondHadre extends RecyclerView.ViewHolder {

        private final ImageView fragment_frist_imageview;
        private final TextView masonry_item_title;

        public SecondHadre(View itemView) {

            super(itemView);
            fragment_frist_imageview = (ImageView) itemView.findViewById(R.id.masonry_item_img);
            masonry_item_title = (TextView) itemView.findViewById(R.id.masonry_item_title);
        }
    }

    //自建里点击事件
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);

        void onItemLongClickListener(int position, View view);
    }


}
