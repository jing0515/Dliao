package com.bawei.lvwenjing.dliao.mode;

import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.RegisterBean;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.utils.PreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistActivityThreeModeImpl implements RegistActivityThreeMode {


    @Override
    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, final RegisterInforFragmentDataListener listener) {
        //进行处理参数 排序 出去中文符号
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.phone", phone);
        map.put("user.nickname", nickname);
        map.put("user.password", password);
        map.put("user.gender", sex);
        map.put("user.area", area);
        map.put("user.age", age);
        map.put("user.introduce", introduce);


        RetrofitFactory.post("http://qhb.2dyt.com/MyInterface/userAction_add.action", map, new BaseObServer() {
            @Override
            public void onSuccess(String result) {
                System.out.println("success=========================" + result);
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);

                if (registerBean.getResult_code() == 200) {
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "phone", registerBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "password", registerBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "yxpassword", registerBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "uid", registerBean.getData().getUserId());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "nickname", registerBean.getData().getNickname());

                }
                listener.onSuccess(registerBean);
            }

            @Override
            public void onFailed(int code) {
                System.out.println("error=========================");
                listener.onFailed(code);
            }

        });


    }


}