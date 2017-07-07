package com.bawei.lvwenjing.dliao.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.widget.MyToast;
import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.BaseMvpActivity;
import com.bawei.lvwenjing.dliao.presenter.RegistActivityTwopresenter;
import com.bawei.lvwenjing.dliao.view.RegistActivityTwoView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

// extends BaseMvpFragment<RegisterSmsView,RegisterSmsPresenter> implements RegisterSmsView
public class RegistActivityTwo extends BaseMvpActivity<RegistActivityTwoView, RegistActivityTwopresenter> implements RegistActivityTwoView {

    @BindView(R.id.registtwo_finsh)
    ImageView registtwoFinsh;
    @BindView(R.id.two_regist_phone)
    EditText twoRegistPhone;
    @BindView(R.id.two_regist_password)
    EditText twoRegistPassword;
    @BindView(R.id.two_yanzhengma)
    Button twoYanzhengma;
    @BindView(R.id.two_regist_regist)
    Button twoRegistRegist;
    @BindView(R.id.activity_regist_two)
    LinearLayout activityRegistTwo;
    private EventHandler eventHandler;


    @Override
    public RegistActivityTwopresenter initpresenter() {
        return new RegistActivityTwopresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_two);

        //  SMSSDK.initSDK(RegistActivityTwo.this, "1f32935270169", "29bd32d477e5a679e749a4ae7af771cb");

        SMSSDK.initSDK(RegistActivityTwo.this, "1f3316358a377", "268073f78d73766d652cf4a29ef36cbd");

        ButterKnife.bind(this);
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                System.out.println("result = " + result);
                System.out.println("data = " + data);

            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

    }

    @OnClick(R.id.registtwo_finsh)
    public void setLogin_finsh() {
        finish();

    }
    //

    @OnClick(R.id.two_yanzhengma)
    public void settwo_yanzhengma() {
        persenter.getVerificationCode(twoRegistPhone.getText().toString().trim());


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.two_regist_regist)
    public void twoRegistRegist() {

        persenter.nextStep(twoRegistPhone.getText().toString().trim(), twoRegistPassword.getText().toString().trim());
    }

    @Override
    public void phoneError(int type) {

        switch (type) {
            case 1:
                MyToast.makeText(RegistActivityTwo.this, "手机号码不能为空", Toast.LENGTH_SHORT);
                break;
            case 2:
                MyToast.makeText(RegistActivityTwo.this, "手机格式不正确", Toast.LENGTH_SHORT);
                break;
            case 3:
                MyToast.makeText(RegistActivityTwo.this, "验证码不能为空", Toast.LENGTH_SHORT);
                break;
            case 4:
                MyToast.makeText(RegistActivityTwo.this, "验证码不正确", Toast.LENGTH_SHORT);
                break;
            case 5:
                Intent intent = new Intent(RegistActivityTwo.this, RegistActivityThree.class);
                intent.putExtra("photo", twoRegistPhone.getText().toString().trim());
                startActivity(intent);
                break;
        }

    }

    private Disposable disposable;

    /**
     * 显示倒计时
     */
    @Override
    public void showTimer() {

        twoYanzhengma.setClickable(false);

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 29 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                        disposable = d;
//                        d.dispose();

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {

                        System.out.println("aLong = " + aLong);
                        twoYanzhengma.setTextSize(15);
                        twoYanzhengma.setText(aLong + "秒后重新发送");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        twoYanzhengma.setClickable(true);
                        twoYanzhengma.setText("获取验证码");

                    }
                });


    }


    @Override
    public void toNextPage() {


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}