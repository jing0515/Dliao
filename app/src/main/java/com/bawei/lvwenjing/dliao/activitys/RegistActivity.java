package com.bawei.lvwenjing.dliao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.AppManager;
import com.bawei.lvwenjing.dliao.base.IActivity;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.customview.HorizontalselectedView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

import static com.bawei.lvwenjing.dliao.R.id.regist_tiao_login;


public class RegistActivity extends IActivity {

    private HorizontalselectedView hsMain;
    List<String> strings = new ArrayList<String>();
    private RadioGroup rg;
    private Button bt;
    private TextView tiaotv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);


        hsMain = (HorizontalselectedView) findViewById(R.id.hd_main);
        initdata();

        rg = (RadioGroup) findViewById(R.id.regist_rg);
        tiaotv = (TextView) findViewById(regist_tiao_login);
        tiaotv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IApplication.getApplication(), LoginActivity.class));
                AppManager.getAppManager().finishActivity(RegistActivity.this);
            }
        });
        bt = (Button) findViewById(R.id.regist_bt_requst);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 2; i++) {
                    if (rg.getChildAt(i).getId() == checkedId) {
                        bt.setClickable(true);
                        bt.setBackgroundResource(R.drawable.shape);
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(RegistActivity.this, RegistActivityTwo.class));
                                AppManager.getAppManager().finishActivity(RegistActivity.this);

                            }
                        });
                    }
                }

            }
        });


    }

    private void initdata() {

        for (int i = 18; i < 66; i++) {
            strings.add(i + "岁");
        }
        hsMain.setData(strings);
    }

    @OnClick(R.id.regist_finsh)
    public void setLogin_finsh() {
        finish();

    }

}

