package com.bawei.lvwenjing.dliao.base;

import android.os.Bundle;

import com.bawei.lvwenjing.dliao.R;

public abstract class BaseMvpActivity<V, T extends BasePresenter<V>> extends IActivity {
    public T persenter;

    public abstract T initpresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_mvp);
        persenter = initpresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        persenter.acttach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detach();
    }
}
