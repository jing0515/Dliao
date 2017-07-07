package com.bawei.lvwenjing.dliao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.bawei.lvwenjing.dliao.activitys.LoginActivity;
import com.bawei.lvwenjing.dliao.activitys.RegistActivity;
import com.bawei.lvwenjing.dliao.base.IActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpalshActivity extends IActivity {
    @BindView(R.id.spalsh_regist)
    Button regist;
    @BindView(R.id.spalsh_login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.spalsh_login)
    public void setLogin() {
        startActivity(new Intent(SpalshActivity.this, LoginActivity.class));

    }

    @OnClick(R.id.spalsh_regist)
    public void regist() {
        startActivity(new Intent(SpalshActivity.this, RegistActivity.class));


    }
}
