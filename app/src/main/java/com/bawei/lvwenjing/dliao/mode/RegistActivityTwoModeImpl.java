package com.bawei.lvwenjing.dliao.mode;

import cn.smssdk.SMSSDK;

/**
 * Created by lenovo-pc on 2017/7/5.
 */

public class RegistActivityTwoModeImpl implements RegistActivityTwoMode {


    public void getVerificationCode(String phone) {

        SMSSDK.getVerificationCode("86", phone);

    }


}
