package com.bawei.lvwenjing.dliao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.BaseMvpActivity;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.LoginBean;
import com.bawei.lvwenjing.dliao.cipher.Md5Utils;
import com.bawei.lvwenjing.dliao.cipher.aes.JNCryptorUtils;
import com.bawei.lvwenjing.dliao.cipher.rsa.RsaUtils;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.presenter.LoginPresenter;
import com.bawei.lvwenjing.dliao.view.LogView;
import com.bawei.lvwenjing.dliao.widget.MyToast;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LogView, LoginPresenter> {
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
        System.loadLibrary("core");
        //随机数 rsa 公钥进行加密
        //  aes 加密  拿着加密后的
        String random = RsaUtils.getInstance().createRandom();
        String rsaKey = RsaUtils.getInstance().createRsaSecret(getApplicationContext(), random);
        String mobile = JNCryptorUtils.getInstance().encryptData("18511085102", getApplicationContext(), random);
    }

    @OnClick(R.id.login_finsh)
    public void setLogin_finsh() {
        finish();

    }

    @OnClick(R.id.login_login)
    public void loginButton() {
        /**
         *
         String phone =   loginEdittextPhone.getText().toString().trim() ;
         String password = loginEdittextPassword.getText().toString().trim();






         String rsaRandomKey =   RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(),randomKey);


         String cipherPhone =   JNCryptorUtils.getInstance().encryptData(phone,IApplication.getApplication(),randomKey);


         Map map = new HashMap<String,String>();
         map.put("user.phone",cipherPhone);
         map.put("user.password", Md5Utils.getMD5(password));
         map.put("user.secretkey",rsaRandomKey);

         RetrofitManager.post(Constants.LOGIN_ACTION, map, new BaseObserver() {
        @Override public void onSuccess(String result) {
        System.out.println("result = " + result);
        }

        @Override public void onFailed(int code) {

        }
        });

         */
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

        RetrofitFactory.post("http://qhb.2dyt.com/MyInterface/userAction_login.action", map, new BaseObServer() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                if (loginBean.getResult_code() == 200) {
                    startActivity(new Intent(LoginActivity.this, TabActivity.class));
                } else {
                    MyToast.makeText(IApplication.getApplication(), "" + "用户名或密码错误", Toast.LENGTH_SHORT);

                }


            }

            @Override
            public void onFailed(int code) {
                MyToast.makeText(IApplication.getApplication(), "" + "失败", Toast.LENGTH_SHORT);

            }
        });


        //md5 消息摘要算法  检验文件完整性 不可逆性质  md5分16位 32位 十六位是从三十二位中截取的
        //验签  验证 穿过去的参数 有没有被拦截 修改
        // 登陆时 客户端  将传过去的参数以adcb 排序 并且 将参数里中文移除
        //jni appkey可能服务端分配好 将appkey与存入的map参数进行拼接 拼接后 将其进行md5加密生成验签
        //服务器收到参数 进行拼接字符串再加上 appkey 进行MD5编码 生成验签 生成的验签 两个进行比较

    }
}
