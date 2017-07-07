package com.bawei.lvwenjing.dliao.base;

/**
 * Created by lenovo-pc on 2017/7/4.
 */

public class BasePresenter<T> {
    public T view;

    public void acttach(T view) {
        //绑定
        this.view = view;
    }

    public void detach() {
        //销毁
        this.view = null;
    }


}
