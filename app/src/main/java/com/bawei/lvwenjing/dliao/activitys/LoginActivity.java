package com.bawei.lvwenjing.dliao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.AppManager;
import com.bawei.lvwenjing.dliao.base.BaseMvpActivity;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.LoginBean;
import com.bawei.lvwenjing.dliao.cipher.Md5Utils;
import com.bawei.lvwenjing.dliao.cipher.aes.JNCryptorUtils;
import com.bawei.lvwenjing.dliao.cipher.rsa.RsaUtils;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.presenter.LoginPresenter;
import com.bawei.lvwenjing.dliao.utils.PreferencesUtils;
import com.bawei.lvwenjing.dliao.utils.Shar;
import com.bawei.lvwenjing.dliao.view.LogView;
import com.bawei.lvwenjing.dliao.widget.KeyBoardHelper;
import com.bawei.lvwenjing.dliao.widget.MyToast;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LogView, LoginPresenter> implements KeyBoardHelper.OnKeyBoardStatusChangeListener {
    @BindView(R.id.login_finsh)
    ImageView login_finsh;
    @BindView(R.id.login_tiao_regist)
    TextView pubTitleRightbtn;
    @BindView(R.id.login_phone)
    EditText login_phone;
    @BindView(R.id.login_password)
    EditText login_password;
    @BindView(R.id.login_login)
    Button loginBtnLogin;


    @Override
    public LoginPresenter initpresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //随机数 rsa 公钥进行加密
        //  aes 加密  拿着加密后的
        String random = RsaUtils.getInstance().createRandom();
        String rsaKey = RsaUtils.getInstance().createRsaSecret(getApplicationContext(), random);
        String mobile = JNCryptorUtils.getInstance().encryptData("18511085102", getApplicationContext(), random);
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);
    }

    @OnClick(R.id.login_finsh)
    public void setLogin_finsh() {
        finish();

    }

    @OnClick(R.id.login_tiao_regist)
    public void logintiaoregist() {
        startActivity(new Intent(IApplication.getApplication(), RegistActivity.class));
        AppManager.getAppManager().finishActivity(LoginActivity.this);

    }

    @OnClick(R.id.login_login)
    public void loginButton() {
        //获取密码获取 电话号码
        String photo = login_phone.getText().toString().trim();
        String password = login_password.getText().toString().trim();
        //获取十六位的随机数
        String randomKey = RsaUtils.getStringRandom(16);
        //对电话号进行rsa与aes方式进行加密
        String cipherPhone = JNCryptorUtils.getInstance().encryptData(photo, IApplication.getApplication(), randomKey);
        String rsaRandomKey = RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(), randomKey);


        Map map = new HashMap<String, String>();
        map.put("user.phone", cipherPhone);
        map.put("user.password", Md5Utils.getMD5(password));
        map.put("user.secretkey", rsaRandomKey);

        if ((PreferencesUtils.getValueByKey(IApplication.getApplication(), "lat", "0")) == null) {
            map.put("user.lat", 110.0);
            map.put("user.lng", 40.0);
        }
        map.put("user.lat", PreferencesUtils.getValueByKey(IApplication.getApplication(), "lat", "0"));
        map.put("user.lng", PreferencesUtils.getValueByKey(IApplication.getApplication(), "lng", "0"));
        RetrofitFactory.post("http://qhb.2dyt.com/MyInterface/userAction_login.action", map, new BaseObServer() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                System.out.println("sss" + result);
                if (loginBean.getResult_code() == 200) {
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "imager", loginBean.getData().getImagePath());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "phone", loginBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "password", loginBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "yxpassword", loginBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "uid", loginBean.getData().getUserId());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "nickname", loginBean.getData().getNickname());
                    IApplication.getApplication().emLogin();
                    Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();

                    Shar.setFile(IApplication.getApplication(), "name", "ha");
                    Intent intent = new Intent(IApplication.getApplication(), TabActivity.class);
                    startActivity(intent);
                    AppManager.getAppManager().finishActivity(LoginActivity.this);


                } else {
                    MyToast.makeText(IApplication.getApplication(), "" + "用户名或密码错误", Toast.LENGTH_SHORT);

                }


            }

            @Override
            public void onFailed(int code) {
                MyToast.makeText(IApplication.getApplication(), "" + "失败", Toast.LENGTH_SHORT);

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        startActivity(new Intent(IApplication.getApplication(), TabActivity.class));
        AppManager.getAppManager().finishActivity(LoginActivity.this);

        return true;
    }


    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        PreferencesUtils.addConfigInfo(this, "kh", keyBoardheight);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {

    }
}
