package com.bawei.lvwenjing.dliao.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.BaseMvpActivity;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.RegisterBean;
import com.bawei.lvwenjing.dliao.cipher.Md5Utils;
import com.bawei.lvwenjing.dliao.presenter.RegistActivityThreepresenter;
import com.bawei.lvwenjing.dliao.view.RegistActivityThreeView;
import com.bawei.lvwenjing.dliao.widget.MyToast;
import com.jakewharton.rxbinding2.view.RxView;
import com.lljjcoder.citypickerview.widget.CityPickerView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.bawei.lvwenjing.dliao.R.id.registthree_age2;
import static com.bawei.lvwenjing.dliao.R.id.registthree_butNext;
import static com.bawei.lvwenjing.dliao.R.id.registthree_ctiy2;
import static com.bawei.lvwenjing.dliao.R.id.registthree_sex2;


public class RegistActivityThree extends BaseMvpActivity<RegistActivityThreeView, RegistActivityThreepresenter> implements RegistActivityThreeView {

    @BindView(R.id.registthree_finsh)
    ImageView registthreeFinsh;
    @BindView(R.id.registthree_zhuce)
    TextView registthreeZhuce;
    @BindView(R.id.registthree_name)
    TextView registthreeName;
    @BindView(R.id.registthree_name_et)
    EditText registthreeNameEt;
    @BindView(R.id.registthree_sex)
    TextView registthreeSex;
    @BindView(registthree_sex2)
    TextView registthreeSex2;
    @BindView(R.id.registthree_age)
    TextView registthreeAge;
    @BindView(registthree_age2)
    TextView registthreeAge2;
    @BindView(R.id.registthree_ctiy)
    TextView registthreeCtiy;
    @BindView(registthree_ctiy2)
    TextView registthreeCtiy2;
    @BindView(R.id.registthree_ingor)
    TextView registthree;
    @BindView(R.id.registthree_infor2)
    EditText registthree2;
    @BindView(R.id.registthree_password)
    TextView registthreePassword;
    @BindView(R.id.registthree_password2)
    EditText registthreePassword2;
    @BindView(R.id.registthree_butNext)
    Button registthreebutNext;
    private String photo;


    @Override
    public RegistActivityThreepresenter initpresenter() {
        return new RegistActivityThreepresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_three);
        ButterKnife.bind(this);
        System.loadLibrary("core");
        Intent intent = getIntent();
        photo = intent.getStringExtra("photo");
    }

    AlertDialog.Builder builder;

    @OnClick(registthree_age2)
    public void setregistthreeAge2() {

        if (builder == null) {
            final String[] ages = getResources().getStringArray(R.array.age);
            builder = new AlertDialog.Builder(RegistActivityThree.this);
            builder.setTitle("请选择年龄");
            builder.setItems(ages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    registthreeAge2.setText(ages[which]);
                }
            });
        }

        builder.show();
    }

    private String[] sexArry = new String[]{"女", "男"};

    @OnClick(registthree_sex2)
    public void setregistthree_sex2() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(RegistActivityThree.this);
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                registthreeSex2.setText(sexArry[which]);
            }
        });
        builder.show();
    }

    @OnClick(registthree_ctiy2)
    public void setregistthree_ctiy2() {
        CityPickerView cityPickerView = new CityPickerView(RegistActivityThree.this);
        cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //   Toast.makeText(RegistActivityThree.this,province+"-"+city+"-"+district,Toast.LENGTH_LONG).show();
                registthreeCtiy2.setText(province + "-" + city + "-" + district);
            }
        });
        cityPickerView.show();

    }

    @OnClick(registthree_butNext)
    public void setregistthree_butNext() {
        toData();
    }


    private void toData() {
        RxView.clicks(registthreebutNext).throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        persenter.vaildInfor(photo, registthreeNameEt.getText().toString().trim(), registthreeSex2.getText().toString().trim()
                                , registthreeAge2.getText().toString().trim(), registthreeCtiy2.getText().toString().trim()
                                , registthree2.getText().toString().trim(), Md5Utils.getMD5(registthreePassword2.getText().toString().trim()));
                    }
                });

    }

    @Override
    public void registerSuccess(RegisterBean registerBean) {
        if(registerBean.getResult_code() == 200){
            startActivity(new Intent(RegistActivityThree.this, PhotoAcitvity.class));
        }else {
            MyToast.makeText(IApplication.getApplication(),registerBean.getResult_message(), Toast.LENGTH_SHORT);
        }
      }

    @Override
    public void registerFailed(int code) {
        // 给一个用户友好的提示
        MyToast.makeText(IApplication.getApplication(), code + "" + "注册失败", Toast.LENGTH_SHORT);

    }
}
