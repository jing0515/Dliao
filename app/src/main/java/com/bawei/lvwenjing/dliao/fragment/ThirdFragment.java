package com.bawei.lvwenjing.dliao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.zhibo.PLVideoViewActivity;
import com.bawei.lvwenjing.dliao.zhibo.SWCameraStreamingActivity;
import com.bawei.lvwenjing.dliao.zhibo.adapter.GuanZhongAdapter;
import com.bawei.lvwenjing.dliao.zhibo.bean.GuanZhongBean;
import com.bawei.lvwenjing.dliao.zhibo.bean.ZhuboBean;
import com.google.gson.Gson;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {


    private ListView listView;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        Button bt1 = (Button) view.findViewById(R.id.fragment_thrid_zhibo);
        listView = (ListView) view.findViewById(R.id.fragment_third_lv);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.sign", 1 + "");
        map.put("live.type", 2 + "");

        RetrofitFactory.posts("http://qhb.2dyt.com/MyInterface/userAction_live.action", map, new BaseObServer() {
            private List<GuanZhongBean.ListBean> list;

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GuanZhongBean guanZhongBean = gson.fromJson(result, GuanZhongBean.class);
                if (guanZhongBean != null && guanZhongBean.getResult_code() == 200) {
                    list = guanZhongBean.getList();
                    GuanZhongAdapter guanZhongAdapter = new GuanZhongAdapter(IApplication.application, list);
                    listView.setAdapter(guanZhongAdapter);
                    guanZhongAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String playUrl = list.get(position).getPlayUrl();
                            String uid = list.get(position).getUid();
                /*            //如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                            EMClient.getInstance().groupManager().joinGroup(groupid);//需异步处理
                     //需要申请     和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                            EMClient.getInstance().groupManager().applyJoinToGroup(groupid, "求加入");*///需异步处理
                            Intent intent = new Intent(getContext(), PLVideoViewActivity.class);
                            intent.putExtra("videoPath", playUrl);
                            startActivity(intent);
                        }
                    });
                } else {


                }

            }

            @Override
            public void onFailed(int code) {

            }
        });



       /* Button bt2 = (Button) view.findViewById(R.id.fragment_thrid_guan);*/
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user.sign", 1 + "");
                map.put("live.type", 1 + "");
                RetrofitFactory.posts("http://qhb.2dyt.com/MyInterface/userAction_live.action", map, new BaseObServer() {
                    private List<GuanZhongBean.ListBean> list;

                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        ZhuboBean guanZhongBean = gson.fromJson(result, ZhuboBean.class);
                        if (guanZhongBean != null && guanZhongBean.getResult_code() == 200) {
                            String url = guanZhongBean.getUrl();


                            Intent intent = new Intent(getContext(), SWCameraStreamingActivity.class);
                            intent.putExtra("stream_json_str", url);
                            /**
                             * 创建群组
                             * @param groupName 群组名称
                             * @param desc 群组简介
                             * @param allMembers 群组初始成员，如果只有自己传空数组即可
                             * @param reason 邀请成员加入的reason
                             * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
                             *               option.inviteNeedConfirm表示邀请对方进群是否需要对方同意，默认是需要用户同意才能加群的。
                             *               option.extField创建群时可以为群组设定扩展字段，方便个性化订制。
                             * @return 创建好的group
                             * @throws HyphenateException
                             */

                            //            在IM服务器创建一个群组
//
//                    参数
//            groupName	群组的名字
//            desc	群组敘述
//            allMembers	群成员数组,不需要群主id
//            reason	邀请群成员加入时的邀请信息
//            option	群的设置
//            callback
                                EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
                                option.maxUsers = 200;
                                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;


                                    EMClient.getInstance().groupManager().asyncCreateGroup("fangjian", "mehsi", new String[]{}, "闲着没有来啊", option, new EMValueCallBack<EMGroup>() {
                                        @Override
                                        public void onSuccess(EMGroup emGroup) {
                                            String groupId = emGroup.getGroupId();
                                        }
                                        @Override
                                        public void onError(int i, String s) {
                                            System.out.println("创建群组的croupId 失败= " + s);
                                        }
                                    });


                            startActivity(intent);


                        } else {
                        }

                    }

                    @Override
                    public void onFailed(int code) {

                    }
                });

            }
        });


    }


}
