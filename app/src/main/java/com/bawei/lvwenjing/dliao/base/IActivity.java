package com.bawei.lvwenjing.dliao.base;

import android.app.Activity;
import android.os.Bundle;

import com.bawei.lvwenjing.dliao.R;

public class IActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i);
    }
}
