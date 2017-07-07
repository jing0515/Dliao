package com.bawei.lvwenjing.dliao.presenter;

import android.text.TextUtils;

import com.bawei.lvwenjing.dliao.utils.PhoneCheckUtils;
import com.bawei.lvwenjing.dliao.base.BasePresenter;
import com.bawei.lvwenjing.dliao.mode.RegistActivityTwoModeImpl;
import com.bawei.lvwenjing.dliao.view.RegistActivityTwoView;

/**
 * Created by lenovo-pc on 2017/7/5.
 */

public class RegistActivityTwopresenter extends BasePresenter<RegistActivityTwoView> {

    private RegistActivityTwoModeImpl registerSmsModel;

    public RegistActivityTwopresenter() {
        registerSmsModel = new RegistActivityTwoModeImpl();
    }


    public void getVerificationCode(String phone) {


        if (TextUtils.isEmpty(phone)) {

            view.phoneError(1);
            return;
        }


        if (!PhoneCheckUtils.isChinaPhoneLegal(phone)) {
            view.phoneError(2);
            return;
        }
        registerSmsModel.getVerificationCode(phone);
        view.showTimer();
    }


    public void nextStep(String phone, String sms) {

        if (TextUtils.isEmpty(phone)) {

            view.phoneError(1);
            return;
        }
        if (TextUtils.isEmpty(sms)) {

            view.phoneError(3);
            return;
        }
        if (!PhoneCheckUtils.istrue(sms)) {
            view.phoneError(4);
            return;
        }


        if (!PhoneCheckUtils.isChinaPhoneLegal(phone)) {
            view.phoneError(2);
            return;
        }

        view.phoneError(5);
        //判断验证码是否合法  验证码是4为数字 \\d{4} sms 非空


    }

}
