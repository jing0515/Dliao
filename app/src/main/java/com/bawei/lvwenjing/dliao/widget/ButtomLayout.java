package com.bawei.lvwenjing.dliao.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawei.lvwenjing.dliao.R;

import io.reactivex.annotations.Nullable;

public class ButtomLayout extends LinearLayout {


    public ButtomLayout(Context context) {
        this(context,null);
    }

    public ButtomLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ButtomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view =  LayoutInflater.from(context).inflate(R.layout.tab_layout,this);
        RadioGroup radioGroup = (RadioGroup)  view.findViewById(R.id.tab_radiogroup);
        RadioButton radioButtonFirst = (RadioButton) view.findViewById(R.id.radiobutton_home);
        RadioButton radioButtonSecond = (RadioButton) view.findViewById(R.id.radiobutton_discover);
        RadioButton radioButtonThird = (RadioButton) view.findViewById(R.id.radiobutton_feed);
        RadioButton radioButtonFourth = (RadioButton) view.findViewById(R.id.radiobutton_me);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                switch (checkedId) {

                    case R.id.radiobutton_home:

                        setListener(0);
                        break;

                    case R.id.radiobutton_discover:
                        setListener(1);

                        break;

                    case R.id.radiobutton_feed:
                        setListener(2);

                        break;

                    case R.id.radiobutton_me:
                        setListener(3);

                        break;
                }

            }
        });


    }





    public void setListener(int index){

        if(listener != null){
            listener.onSelect(index);
        }
    }



    public interface OnSelectListener {
        public void onSelect(int index);
    }

    public OnSelectListener listener ;

    public void setOnSelectListener(OnSelectListener listener){
        this.listener = listener ;
    }











}
