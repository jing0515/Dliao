package com.bawei.lvwenjing.dliao.zhibo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.utils.PreferencesUtils;
import com.bawei.lvwenjing.dliao.widget.EditTextPreIme;
import com.bawei.lvwenjing.dliao.widget.KeyBoardHelper;
import com.bawei.lvwenjing.dliao.zhibo.adapter.DanMuAdapter;
import com.bawei.lvwenjing.dliao.zhibo.bean.GuanZhongBean;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SWCameraStreamingActivity extends Activity implements StreamingStateChangedListener, KeyBoardHelper.OnKeyBoardStatusChangeListener {
    private JSONObject mJSONObject;
    private MediaStreamingManager mMediaStreamingManager;
    private StreamingProfile mProfile;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    break;
                case 2:

                    break;
            }
        }
    };
    private Integer kh;
    private EditTextPreIme editText;
    private ImageView imageView;
    private Button button;
    private LinearLayout cameraPreviewtoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swcamera_streaming);
        cameraPreviewtoo = (LinearLayout)    findViewById(R.id.cameraPreview_too);
        LinearLayout buttomlayoutview = (LinearLayout) findViewById(R.id.cameraPreview_relation);
        //使用工具类
        KeyBoardHelper helper = new KeyBoardHelper(this);
        helper.onCreate();
        helper.setOnKeyBoardStatusChangeListener(this);
        kh = PreferencesUtils.getValueByKey(this, "kh", 0);
        LinearLayout.LayoutParams prams = (LinearLayout.LayoutParams) buttomlayoutview.getLayoutParams();
        prams.height = kh;
        buttomlayoutview.setLayoutParams(prams);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("hahhhhhhhhhhhhh" + i);
        }
        ListView lv = (ListView) findViewById(R.id.cameraPreview_listview);



        DanMuAdapter adapter = new DanMuAdapter(IApplication.getApplication(), list);
        lv.setAdapter(adapter);
        editText = (EditTextPreIme) findViewById(R.id.cameraPreview_et);
        button = (Button) findViewById(R.id.cameraPreview_liaotian);
        button.setTag(1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) button.getTag();
                if (tag == 1) {
                    button.setVisibility(View.GONE);
                    cameraPreviewtoo.setVisibility(View.VISIBLE);
                    showKeyBoard(editText);
                }
            }
        });
        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        // Decide FULL screen or real size
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.cameraPreview_surfaceView);
        String streamJsonStrFromServer = getIntent().getStringExtra("stream_json_str");
        try {
//            mJSONObject = new JSONObject(streamJsonStrFromServer);
//            StreamingProfile.Stream stream = new StreamingProfile.Stream(mJSONObject);
            mProfile = new StreamingProfile();
            //推流的地址
            mProfile.setPublishUrl(streamJsonStrFromServer);
            mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH1)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY);
//                    .setStream(stream);  // You can invoke this before startStreaming, but not in initialization phase.
            CameraStreamingSetting setting = new CameraStreamingSetting();
            setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                    .setContinuousFocusModeEnabled(true)
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);

            //美颜
            setting.setBuiltInFaceBeautyEnabled(true);
            setting.setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(1.0f, 1.0f, 0.8f))
                    .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY);

            mMediaStreamingManager = new MediaStreamingManager(this, afl, glSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
            mMediaStreamingManager.prepare(setting, mProfile);
            mMediaStreamingManager.setStreamingStateListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaStreamingManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        mMediaStreamingManager.pause();
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object extra) {
        switch (streamingState) {
            case PREPARING:
                break;
            case READY:
                // start streaming when READY
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaStreamingManager != null) {
                            mMediaStreamingManager.startStreaming();
                        }
                    }
                }).start();
                break;
            case CONNECTING:
                break;
            case STREAMING:
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                // The streaming had been finished.
                break;
            case IOERROR:
                // Network connect error.
                break;
            case OPEN_CAMERA_FAIL:
                // Failed to open camera.
                break;
            case DISCONNECTED:
                // The socket is broken while streaming
                break;
        }
    }

    //隐藏键盘
    public void hidenKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    // 显示键盘
    public void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        button.setVisibility(View.VISIBLE);
        cameraPreviewtoo.setVisibility(View.GONE);
        button.setTag(1);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.sign", 1 + "");
        map.put("live.type", 3 + "");
        RetrofitFactory.posts("http://qhb.2dyt.com/MyInterface/userAction_live.action", map, new BaseObServer() {

            private List<GuanZhongBean.ListBean> list;

            @Override
            public void onSuccess(String result) {
                if (result.contains("200")) {
                    Toast.makeText(SWCameraStreamingActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailed(int code) {

            }
        });
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SWCameraStreaming Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        handler.sendEmptyMessageAtTime(1, 200);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {

    }
}