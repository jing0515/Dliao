package com.bawei.lvwenjing.dliao.mode;

import com.bawei.lvwenjing.dliao.bean.RegisterBean;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public interface RegistActivityThreeMode {
    //进行网络请求支付时后使用
    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, RegisterInforFragmentDataListener listener);

    //建立接口 返回请求成功时候返回的数据 与失败返回的数据
    public interface RegisterInforFragmentDataListener {
        public void onSuccess(RegisterBean registerBean);

        public void onFailed(int code);

    }
}
