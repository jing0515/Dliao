package com.bawei.lvwenjing.dliao.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.fragment.FourthFragment;
import com.bawei.lvwenjing.dliao.fragment.FristFragment;
import com.bawei.lvwenjing.dliao.fragment.SecondFragment;
import com.bawei.lvwenjing.dliao.fragment.ThirdFragment;
import com.bawei.lvwenjing.dliao.widget.ButtomLayout;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends FragmentActivity implements ButtomLayout.OnSelectListener {
    private ButtomLayout buttomLayout;
    private FragmentManager fragmentManager;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        fragmentManager = getSupportFragmentManager();
        createFragment(savedInstanceState);

        buttomLayout = (ButtomLayout) findViewById(R.id.buttom_layout);
        buttomLayout.setOnSelectListener(this);


        switchFragment(0);

    }

    public void createFragment(Bundle savedInstanceState) {

        FristFragment firstFragment = (FristFragment) fragmentManager.findFragmentByTag("FristFragment");
        SecondFragment secondFragment = (SecondFragment) fragmentManager.findFragmentByTag("SecondFragment");
        ThirdFragment thirdFragment = (ThirdFragment) fragmentManager.findFragmentByTag("ThirdFragment");
        FourthFragment fourthFragment = (FourthFragment) fragmentManager.findFragmentByTag("FourthFragment");

        if (firstFragment == null) {
            firstFragment = new FristFragment();
        }

        if (secondFragment == null) {
            secondFragment = new SecondFragment();
        }
        if (thirdFragment == null) {
            thirdFragment = new ThirdFragment();
        }
        if (fourthFragment == null) {
            fourthFragment = new FourthFragment();
        }


        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(fourthFragment);


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
}