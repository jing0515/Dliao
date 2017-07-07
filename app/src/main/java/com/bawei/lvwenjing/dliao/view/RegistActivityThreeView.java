package com.bawei.lvwenjing.dliao.view;

import com.bawei.lvwenjing.dliao.bean.RegisterBean;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public interface RegistActivityThreeView {
    //成功返回
    public void registerSuccess(RegisterBean registerBean);

    //失败返回
    public void registerFailed(int code);

}
