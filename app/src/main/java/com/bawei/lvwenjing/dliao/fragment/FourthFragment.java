package com.bawei.lvwenjing.dliao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.activitys.LoginActivity;
import com.bawei.lvwenjing.dliao.base.AppManager;
import com.bawei.lvwenjing.dliao.base.IApplication;


/**
 *
 */
public class FourthFragment extends Fragment {


    private Button fourth_denglu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        LinearLayout fourth_shezhi = (LinearLayout) view.findViewById(R.id.fourth_shezhi);
        fourth_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        fourth_denglu = (Button) view.findViewById(R.id.fourth_denglu);
        fourth_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IApplication.getApplication(), LoginActivity.class));
                AppManager.getAppManager().finishActivity(getActivity());
            }
        });
        return view;
    }


}



