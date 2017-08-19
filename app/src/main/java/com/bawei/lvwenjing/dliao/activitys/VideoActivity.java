package com.bawei.lvwenjing.dliao.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.media.EMCallSurfaceView;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.superrtc.sdk.VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill;

public class VideoActivity extends Activity {

    @BindView(R.id.surface_big)
    EMCallSurfaceView surfaceBig;
    @BindView(R.id.surface_small)
    EMCallSurfaceView surfaceSmall;
    @BindView(R.id.videoactivity_tv)
    TextView videoactivityTv;
    @BindView(R.id.videoactivity_time)
    Chronometer videoactivitytime;
    @BindView(R.id.videoactivity_bt_jieting)
    Button videoactivityBtJieting;
    @BindView(R.id.videoactivity_bt_guaduan)
    Button videoactivityBtGuaduan;
    @BindView(R.id.videoactivity_bt_jujie)
    Button videoactivityBtJujie;
    @BindView(R.id.video_activity_iv)
    ImageView videoactivityiv;
    private String uid;
    private int type;
    private String name;
    int i = 0;
    private Timer timer;

    /**
     * @param type    1 拨打视频电话  2 接受视频电话
     * @param uid
     * @param context
     */
    public static void startTelActivity(int type, String uid, String name, Context context) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("uid", uid);
        intent.putExtra("name", name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        surfaceSmall.setTag(1);
        uid = getIntent().getExtras().getString("uid");
        type = getIntent().getExtras().getInt("type");
        name = getIntent().getStringExtra("name");

        //让自己图像 显示在上面
        surfaceSmall.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceSmall.setZOrderOnTop(true);
        videoactivityTv.setText(name);
        if (1 == type) {
            //拨打电话
            videoactivityBtJieting.setVisibility(View.GONE);
            videoactivityBtJujie.setVisibility(View.GONE);
            videoactivityBtGuaduan.setVisibility(View.VISIBLE);
            //拨打视频通话
            try {
                EMClient.getInstance().callManager().makeVideoCall(uid);
                IApplication.callTo();

            } catch (EMServiceNotReadyException e) {
                e.printStackTrace();
            }
        } else {
            //接听电话
            videoactivityBtJieting.setVisibility(View.VISIBLE);
            videoactivityBtJujie.setVisibility(View.VISIBLE);
            videoactivityBtGuaduan.setVisibility(View.GONE);
        }
        EMClient.getInstance().callManager().setSurfaceView(surfaceSmall, surfaceBig);
        surfaceSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) surfaceSmall.getTag();
                if (tag == 1) {
                    EMClient.getInstance().callManager().setSurfaceView(surfaceBig, surfaceSmall);
                    surfaceSmall.setTag(2);
                } else {
                    EMClient.getInstance().callManager().setSurfaceView(surfaceSmall, surfaceBig);

                    surfaceSmall.setTag(1);
                }
            }
        });

        surfaceSmall.setScaleMode(EMCallViewScaleModeAspectFill);
        surfaceBig.setScaleMode(EMCallViewScaleModeAspectFill);
        connectState();

    }


    public void connectState() {
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {

                switch (callState) {
                    case CONNECTING: // 正在连接对方

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoactivitytime.setText("正在连接");
                            }
                        });
                        Log.e("videoactivityTv ", "正在连接");

                        break;
                    case CONNECTED: // 双方已经建立连接

                        break;

                    case ACCEPTED: // 电话接通成功
                        videoactivitytime.setBase(SystemClock.elapsedRealtime());//计时器清零
                        videoactivitytime.start();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                        Log.e("videoactivityTv ", "电话接通成功");
                        break;
                    case DISCONNECTED: // 电话断了
                        videoactivitytime.stop();
                        videoactivitytime.setVisibility(View.GONE);
                        finish();
                        Toast.makeText(VideoActivity.this, "网络过差", Toast.LENGTH_SHORT).show();
                        Log.e("videoactivityTv ", "电话断了");

                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if (error == CallError.ERROR_NO_DATA) {
                            //无通话数据
                        } else {
                        }

                        Log.e("videoactivityTv ", "网络不稳定");


                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        Log.e("videoactivityTv ", "网络恢复正常");

                        break;
                    default:

                        Log.e("videoactivityTv ", "default");

                        break;
                }

            }
        });
    }

    @OnClick(R.id.video_activity_iv)
    public void set() {
        EMClient.getInstance().callManager().switchCamera();
    }

    @OnClick({R.id.videoactivity_bt_jieting, R.id.videoactivity_bt_guaduan, R.id.videoactivity_bt_jujie})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.videoactivity_bt_jieting:
                try {
                    EMClient.getInstance().callManager().answerCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("videoactivityTv ", "接听电话");


                break;
            case R.id.videoactivity_bt_guaduan:

                try {
                    //挂断
                    EMClient.getInstance().callManager().endCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }
                Log.e("videoactivityTv ", "挂断电话");

                finish();


                break;
            case R.id.videoactivity_bt_jujie:

                try {
                    EMClient.getInstance().callManager().rejectCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }
                Log.e("videoactivityTv ", "拒接电话");
                break;
        }
    }
}