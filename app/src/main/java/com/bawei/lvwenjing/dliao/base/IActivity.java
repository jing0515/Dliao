package com.bawei.lvwenjing.dliao.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bawei.lvwenjing.dliao.R;

public class IActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i);
        AppManager.getAppManager().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
