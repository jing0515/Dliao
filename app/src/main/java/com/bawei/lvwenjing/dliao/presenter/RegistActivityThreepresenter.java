package com.bawei.lvwenjing.dliao.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.base.BasePresenter;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.RegisterBean;
import com.bawei.lvwenjing.dliao.mode.RegistActivityThreeMode;
import com.bawei.lvwenjing.dliao.mode.RegistActivityThreeModeImpl;
import com.bawei.lvwenjing.dliao.view.RegistActivityThreeView;
import com.bawei.lvwenjing.dliao.widget.MyToast;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistActivityThreepresenter extends BasePresenter<RegistActivityThreeView> {
    //建立属性 来进行控制
    private RegistActivityThreeMode registerInforFragmentModel;

    public RegistActivityThreepresenter() {
        registerInforFragmentModel = new RegistActivityThreeModeImpl();
    }

    public void vaildInfor(String phone, String nickname, String sex, String age, String area, String introduce, String password, String lat, String lgi) {

        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(area) && !TextUtils.isEmpty(introduce) && !TextUtils.isEmpty(password)) {
            registerInforFragmentModel.getData(phone, nickname, sex, age, area, introduce, password, lat, lgi,
                    new RegistActivityThreeMode.RegisterInforFragmentDataListener() {
                        @Override
                        public void onSuccess(RegisterBean registerBean) {
                            view.registerSuccess(registerBean);
                        }

                        @Override
                        public void onFailed(int code) {
                            view.registerFailed(code);
                        }
                    });
        } else {
            MyToast.makeText(IApplication.getApplication(), "参数为空请重新输入", Toast.LENGTH_SHORT);
        }

    }


}
