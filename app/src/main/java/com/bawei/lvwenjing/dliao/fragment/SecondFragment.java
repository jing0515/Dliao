package com.bawei.lvwenjing.dliao.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.activitys.ChatActivity;
import com.bawei.lvwenjing.dliao.adapter.Fragment_second_Adapter;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.DataBean1;
import com.bawei.lvwenjing.dliao.bean.FActionFriendBean;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.utils.Shar;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bawei.lvwenjing.dliao.base.IApplication.getApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    List<DataBean1> list = new ArrayList<DataBean1>();
    private IApplication application;
    private RecyclerView rv;
    private SpringView springView;
    private Fragment_second_Adapter adapter;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two, container, false);

        ImageView iv = (ImageView) view.findViewById(R.id.fragment_two_login_iv);
        TextView fragment_two_login_tv = (TextView) view.findViewById(R.id.fragment_two_login_tv);
        if (IsFrist().equals("ha")) {
            iv.setVisibility(View.GONE);
            fragment_two_login_tv.setVisibility(View.GONE);
            long ctimer = System.currentTimeMillis();
            getInfor(ctimer);
            application = (IApplication) getApplication();
            springView = (SpringView) view.findViewById(R.id.spingviews);
            rv = (RecyclerView) view.findViewById(R.id.fragment_frist_recyviews);
            springView.setHeader(new DefaultHeader(getApplication()));
            springView.setFooter(new DefaultFooter(getApplication()));
            springView.setType(SpringView.Type.FOLLOW);
            adapter = new Fragment_second_Adapter(list, IApplication.getApplication());
            rv.setAdapter(adapter);
            adapter.setOnItemClickListener(new Fragment_second_Adapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position, View view) {
                    int userId = list.get(position).getUserId();
                    String imagePath = list.get(position).getImagePath();
                    String nickname = list.get(position).getNickname();
                    Intent intent = new Intent(IApplication.getApplication(), ChatActivity.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("imagePath",imagePath);
                    intent.putExtra("nickname",nickname);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClickListener(int position, View view) {

                }
            });

            rv.setLayoutManager(new LinearLayoutManager(getApplication()));
            springView.setListener(new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                    list.clear();
                    long ctimer = System.currentTimeMillis();
                    getInfor(ctimer);
                    springView.onFinishFreshAndLoad();
                }

                @Override
                public void onLoadmore() {
                    if (list.size()==0){
                        return;
                    }
                    long relationtime = list.get(list.size() - 1).getRelationtime();
                    getInfor(relationtime);
                    springView.onFinishFreshAndLoad();
                }
            });
        } else {


        }


        return view;
    }

    public void getInfor(long re) {

        //获取判断是否有网络的管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) IApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取可用网络
        NetworkInfo infor = connectivityManager.getActiveNetworkInfo();
        //获取wifi状态
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //获取网络状态
        NetworkInfo.State interstat = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (infor == null) {
            //  List<DataBean1> lista = application.daoSession.getDataBean1Dao().queryBuilder().list();
            // list.addAll(lista);adapter.notifyDataSetChanged();
        } else {
            long ctimer = System.currentTimeMillis();
            Map<String, String> map = new HashMap<String, String>();
            map.put("user.currenttimer", re + "");
            RetrofitFactory.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUserAndFriend.action", map, new BaseObServer() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    FActionFriendBean fActionFriendBean = gson.fromJson(result, FActionFriendBean.class);
                    if (fActionFriendBean == null) {
                        return;
                    }

                    List<DataBean1> data = fActionFriendBean.getData();
                    if (data == null) {
                        return;
                    } else {
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailed(int code) {

                }
            });
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        list.clear();
    }

    public String IsFrist() {
        String fliet = (String) Shar.getFliet(IApplication.getApplication(), "name", "");
        return fliet;

    }
}
