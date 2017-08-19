package com.bawei.lvwenjing.dliao.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.LruCache;

import com.bawei.lvwenjing.dliao.R;

import java.util.HashMap;

public class Regist_Password_twoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist__password_two);
        HashMap<String, String> map = new HashMap<>();
        LruCache lruCache = new LruCache<String, Object>(100) {

        };

    }
}
