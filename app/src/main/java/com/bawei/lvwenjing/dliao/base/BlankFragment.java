package com.bawei.lvwenjing.dliao.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.lvwenjing.dliao.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BlankFragment<V, T extends BasePresenter<V>> extends IFragment {
    public T persenter;

    public abstract T initpersenter();

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        persenter = initpersenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        persenter.acttach((V) this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        persenter.detach();
    }
}
