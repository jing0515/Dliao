package com.bawei.lvwenjing.dliao.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.AppManager;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.network.cookie.CookiesManager;
import com.bawei.lvwenjing.dliao.utils.Shar;

public class Zhanghaotuichu extends Activity {

    private LinearLayout zhanghao_yonghuinfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhanghaotuichu);
        Button bt = (Button) findViewById(R.id.zhanghu_tuchu);
        zhanghao_yonghuinfor = (LinearLayout) findViewById(R.id.zhanghao_yonghuinfor);
        zhanghao_yonghuinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IApplication.getApplication(), Regist_PasswordActivity.class));

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(Zhanghaotuichu.this)
                        .setTitle("切换账号")
                        .setMessage("当前账户退出后，将无法收到消息推送~~ 是否确定切换账号");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //退出时候使用
                        new CookiesManager(IApplication.application).removeAllCookie();

                        Shar.setFile(IApplication.getApplication(), "name", "a");
                        startActivity(new Intent(IApplication.getApplication(), LoginActivity.class));
                        AppManager.getAppManager().finishActivity(Zhanghaotuichu.this);

                    }
                });

                builder.show();
            }
        });
    }
}
