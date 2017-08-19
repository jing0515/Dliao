package com.bawei.lvwenjing.dliao.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.activitys.FridentActivity;
import com.bawei.lvwenjing.dliao.adapter.Fragment_frist_Adapter;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.DataBean;
import com.bawei.lvwenjing.dliao.bean.FFindBean;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.utils.SpacesItemDecoration;
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
public class FristFragment extends Fragment implements View.OnClickListener {


    List<DataBean> list = new ArrayList<DataBean>();

    private Fragment_frist_Adapter adapter;
    private RecyclerView rv;
    private SpringView springView;
    int page = 1;
    int type = 0;
    private Button bt;
    private IApplication application;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        application = (IApplication) getApplication();

        View view = inflater.inflate(R.layout.fragment_frist, container, false);
        springView = (SpringView) view.findViewById(R.id.spingview);
        bt = (Button) view.findViewById(R.id.fragment_frist_finshs);
        rv = (RecyclerView) view.findViewById(R.id.fragment_frist_recyview);
        long ctimer = System.currentTimeMillis();
        getInfor(ctimer);

        adapter = new Fragment_frist_Adapter(list, getApplication(), type);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new Fragment_frist_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                Intent intent = new Intent(IApplication.getApplication(), FridentActivity.class);
                int userId = list.get(position).getUserId();
                int relation = list.get(position).getRelation();
                intent.putExtra("userId", userId);
                intent.putExtra("relation", relation);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(int position, View view) {

            }
        });
        springView.setHeader(new DefaultHeader(getApplication()));
        springView.setFooter(new DefaultFooter(getApplication()));
        springView.setType(SpringView.Type.FOLLOW);
        rv.setLayoutManager(new LinearLayoutManager(getApplication()));
        bt.setOnClickListener(this);
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
                long lasttime = list.get(list.size() - 1).getLasttime();
                getInfor(lasttime);
                springView.onFinishFreshAndLoad();
            }
        });

        return view;
    }


    public void getInfor(long time) {
        //获取判断是否有网络的管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) IApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取可用网络
        NetworkInfo infor = connectivityManager.getActiveNetworkInfo();
        //获取wifi状态
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //获取网络状态
        NetworkInfo.State interstat = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (infor == null) {
            List<DataBean> lists = application.daoSession.getDataBeanDao().queryBuilder().list();
            list.addAll(lists);
            adapter.notifyDataSetChanged();
        } else {
            long ctimer = System.currentTimeMillis();
            Map<String, String> map = new HashMap<String, String>();
        map.put("user.currenttimer", time + "");
        RetrofitFactory.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action", map, new BaseObServer() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                FFindBean fFindBean = gson.fromJson(result, FFindBean.class);
                List<DataBean> data = fFindBean.getData();
                application.daoSession.getDataBeanDao().deleteAll();
                for (DataBean ingor : data) {
                    //存入信息
                    application.daoSession.getDataBeanDao().insert(ingor);
                }
                list.addAll(data);
                System.out.println("data.size()wangluoqin = " + data.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        if (type == 0) {

            //1：设置布局类型
            adapter.setType(1);
            //2：设置对应的布局管理器
            //设置layoutManager
            rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            //设置item之间的间隔
            SpacesItemDecoration decoration = new SpacesItemDecoration(10);
            rv.addItemDecoration(decoration);
            //3：刷新adapter
            adapter.notifyDataSetChanged();
            type = 1;
        } else {
            adapter.setType(0);
            rv.setLayoutManager(new LinearLayoutManager(getApplication()));
            adapter.notifyDataSetChanged();
            type = 0;
        }
    }
}
