package com.bawei.lvwenjing.dliao.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bawei.lvwenjing.dliao.R;

public class Regist_PasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist__password);
        RelativeLayout password = (RelativeLayout) findViewById(R.id.password_xiugai);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
