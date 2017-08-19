package com.bawei.lvwenjing.dliao.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.adapter.Frident_Adapter;
import com.bawei.lvwenjing.dliao.adapter.PicShowDialog;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.PersonBean;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.widget.MyToast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FridentActivity extends Activity {

    private Button bt;
    List<PersonBean.DataBean.PhotolistBean> list = new ArrayList<PersonBean.DataBean.PhotolistBean>();
    private int relation;
    private int userId;
    private ImageView fridentiv;
    private TextView fridentName;
    private TextView fridentCtry;
    private TextView fridentAge;
    private RecyclerView re;
    int num = 0;
    private Integer buttoningor;
    private Button bts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frident);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 1);
        relation = intent.getIntExtra("relation", 1);
        getUrl();
        fridentiv = (ImageView) findViewById(R.id.frident_iv);
        fridentName = (TextView) findViewById(R.id.frident_name);
        fridentAge = (TextView) findViewById(R.id.frident_age);
        fridentCtry = (TextView) findViewById(R.id.frident_ctry);
        re = (RecyclerView) findViewById(R.id.frident_lin);
        bt = (Button) findViewById(R.id.fridentactivity_button);
        if (relation == 2) {
            bt.setText("发送消息");
        } else {
            bt.setText("加好友");
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (relation == 2) {
                    startActivity(new Intent(IApplication.getApplication(), ChatActivity.class));
                } else {
                    getFrident();
                }


            }
        });
    }

    public void getUrl() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.userId", userId + "");
        RetrofitFactory.post("http://qhb.2dyt.com/MyInterface/userAction_selectUserById.action", map, new BaseObServer() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PersonBean personBean = gson.fromJson(result, PersonBean.class);
                PersonBean.DataBean data = personBean.getData();
                final List<PersonBean.DataBean.PhotolistBean> photolist = data.getPhotolist();
                list.addAll(photolist);
                if (photolist.size() == 0) {
                    Glide.with(IApplication.getApplication()).load(data.getImagePath()).into(fridentiv);
                    fridentName.setText(data.getNickname());
                    fridentAge.setText(data.getGender());
                    fridentCtry.setText(data.getArea());
                    return;
                }
                Frident_Adapter adapter = new Frident_Adapter(photolist, IApplication.application);
                re.setLayoutManager(new GridLayoutManager(IApplication.getApplication(), photolist.size()));
                re.setAdapter(adapter);
                adapter.setOnItemClickListener(new Frident_Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, View view) {
                        PicShowDialog picShowDialog = new PicShowDialog(FridentActivity.this, photolist, position);
                        picShowDialog.show();
                    }

                    @Override
                    public void onItemLongClickListener(int position, View view) {

                    }
                });
                Glide.with(IApplication.getApplication()).load(data.getImagePath()).into(fridentiv);
                fridentName.setText(data.getNickname());
                fridentAge.setText(data.getGender());
                fridentCtry.setText(data.getArea());
            }

            @Override
            public void onFailed(int code) {

            }
        });

    }

    public void getFrident() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("relationship.friendId", userId + "");
        RetrofitFactory.post("http://qhb.2dyt.com/MyInterface/userAction_addFriends.action", map, new BaseObServer() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=====" + result);
                Gson gson = new Gson();
                if (result.contains("未登录")) {
                    // 给一个用户友好的提示
                    MyToast.makeText(IApplication.getApplication(), "" + "请先登陆", Toast.LENGTH_SHORT);
                    return;
                }
                if (result.contains("200")) {
                    // 给一个用户友好的提示
                    MyToast.makeText(IApplication.getApplication(), "" + "添加成功", Toast.LENGTH_SHORT);
                    bt.setText("发消息");
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

}
