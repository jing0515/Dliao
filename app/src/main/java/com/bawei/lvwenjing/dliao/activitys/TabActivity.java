package com.bawei.lvwenjing.dliao.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.fragment.FourthFragment;
import com.bawei.lvwenjing.dliao.fragment.FristFragment;
import com.bawei.lvwenjing.dliao.fragment.SecondFragment;
import com.bawei.lvwenjing.dliao.fragment.ThirdFragment;
import com.bawei.lvwenjing.dliao.utils.Shar;
import com.bawei.lvwenjing.dliao.widget.ButtomLayout;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends FragmentActivity implements ButtomLayout.OnSelectListener {
    private ButtomLayout buttomLayout;
    private FragmentManager fragmentManager;

    private List<Fragment> fragments = new ArrayList<Fragment>();
    private String imagePath;
    private String nickname;
    private int userId;
    private MyInforActivity myInforActivity;
    private FourthFragment fourthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        fragmentManager = getSupportFragmentManager();
        createFragment(savedInstanceState);
        buttomLayout = (ButtomLayout) findViewById(R.id.buttom_layout);
        buttomLayout.setOnSelectListener(this);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imagePath");

        switchFragment(0);
        //监听
        incoming();
    }

    CallReceiver callReceiver;

    public void incoming() {
        callReceiver = new CallReceiver();
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        registerReceiver(callReceiver, callFilter);
    }

    private class CallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            // call type
            String type = intent.getStringExtra("type");

              //响铃
            IApplication.ring();
            //跳转到通话页面
            VideoActivity.startTelActivity(2, from,"接听电话", TabActivity.this);


        }
    }

    public void createFragment(Bundle savedInstanceState) {


        FristFragment firstFragment = (FristFragment) fragmentManager.findFragmentByTag("FristFragment");
        SecondFragment secondFragment = (SecondFragment) fragmentManager.findFragmentByTag("SecondFragment");
        ThirdFragment thirdFragment = (ThirdFragment) fragmentManager.findFragmentByTag("ThirdFragment");
        if (IsFrist().equals("ha")) {
            myInforActivity = (MyInforActivity) fragmentManager.findFragmentByTag("MyInforActivity");

        } else {
            fourthFragment = (FourthFragment) fragmentManager.findFragmentByTag("FourthFragment");

        }
        if (firstFragment == null) {

            firstFragment = new FristFragment();
        }

        if (secondFragment == null) {
            secondFragment = new SecondFragment();
        }
        if (thirdFragment == null) {
            thirdFragment = new ThirdFragment();
        }


        if (fourthFragment == null || myInforActivity == null) {
            if (IsFrist().equals("ha")) {
                myInforActivity = new MyInforActivity();

            } else {
                fourthFragment = new FourthFragment();
            }


        }


        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        if (IsFrist().equals("ha")) {
            fragments.add(myInforActivity);
        } else {
            fragments.add(fourthFragment);

        }


    }


    public void switchFragment(int pos) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (!fragments.get(pos).isAdded()) {

            transaction.add(R.id.container, fragments.get(pos), fragments.get(pos).getClass().getSimpleName());
        }

        for (int i = 0; i < fragments.size(); i++) {

            if (i == pos) {
                transaction.show(fragments.get(pos));
            } else {
                transaction.hide(fragments.get(i));
            }

        }
        transaction.commit();


    }


    /**
     * 底部导航 点击 回调
     *
     * @param index
     */
    @Override
    public void onSelect(int index) {
        switchFragment(index);
    }

    public String IsFrist() {
        String fliet = (String) Shar.getFliet(IApplication.getApplication(), "name", "");
        return fliet;

    }

}