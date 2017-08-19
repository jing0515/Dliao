package com.bawei.lvwenjing.dliao.activitys;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.adapter.CChativityAdapter;
import com.bawei.lvwenjing.dliao.base.IActivity;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.speex.SpeexPlayer;
import com.bawei.lvwenjing.dliao.speex.SpeexRecorder;
import com.bawei.lvwenjing.dliao.utils.Constants;
import com.bawei.lvwenjing.dliao.utils.ImageResizeUtils;
import com.bawei.lvwenjing.dliao.utils.PreferencesUtils;
import com.bawei.lvwenjing.dliao.utils.SDCardUtils;
import com.bawei.lvwenjing.dliao.widget.EditTextPreIme;
import com.bawei.lvwenjing.dliao.widget.KeyBoardHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.bawei.lvwenjing.dliao.utils.ImageResizeUtils.copyStream;

public class ChatActivity extends IActivity implements View.OnClickListener, KeyBoardHelper.OnKeyBoardStatusChangeListener, EditTextPreIme.EditTextListener {
    private Button charactivitybiaoqing;
    private Button charactivitybiaojia;
    private Button charactivitybiaoyuyin;
    private EditTextPreIme charactivityet;
    private LinearLayout buttomlayoutview;
    EaseEmojiconMenuBase emojiconMenu;
    private Integer kh;
    private RelativeLayout relativeLayout;
    private ImageView micImage;
    private SpeexRecorder recorderInstance;
    private String fileName;
    private String filePath;
    private List<EMMessage> list;
    private EMMessageListener msgListener;
    private int userId;
    private String nickname;
    private String imagePath;
    private ListView chatactivityrv;
    private CChativityAdapter adapter1;
    private Button charactivitybt;
    private EMVoiceMessageBody voiceBody;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter1.notifyDataSetChanged();
                    chatactivityrv.setSelection(adapter1.getCount());
                    break;
                case 2:

                    break;
            }
        }
    };
    private EMConversation conversation;
    private Button charactivitybiaojia2;
    private LinearLayout buttomlayoutviewsss;
    private ImageView iv;
    boolean istrue = false;
    boolean isfalse = false;
    private RelativeLayout relativeLayout1;
    private ImageView chativ3;
    private AnimationDrawable drawable;
    int frist = 1;
    private ImageView chat_itme1_xiangji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //建立数据库
        conversation = EMClient.getInstance().chatManager().getConversation(userId + "");
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 1);
        imagePath = intent.getStringExtra("imagePath");
        nickname = intent.getStringExtra("nickname");
        drawable = new AnimationDrawable();
        drawable.addFrame(getResources().getDrawable(R.drawable.amp1), 100);
        drawable.addFrame(getResources().getDrawable(R.drawable.amp2), 300);
        drawable.addFrame(getResources().getDrawable(R.drawable.amp3), 100);
        drawable.addFrame(getResources().getDrawable(R.drawable.amp4), 200);
        drawable.addFrame(getResources().getDrawable(R.drawable.amp5), 100);
        drawable.addFrame(getResources().getDrawable(R.drawable.amp6), 100);
        drawable.addFrame(getResources().getDrawable(R.drawable.amp7), 300);
        PreferencesUtils.addConfigInfo(IApplication.getApplication(), "nicknames", imagePath);
        PreferencesUtils.addConfigInfo(IApplication.getApplication(), "userId", userId);

        list = new ArrayList<EMMessage>();
        inits();
        //使用工具类
        KeyBoardHelper helper = new KeyBoardHelper(this);
        helper.onCreate();
        helper.setOnKeyBoardStatusChangeListener(this);
        kh = PreferencesUtils.getValueByKey(this, "kh", 0);
        RelativeLayout.LayoutParams prams = (RelativeLayout.LayoutParams) buttomlayoutview.getLayoutParams();
        prams.height = kh;
        buttomlayoutview.setLayoutParams(prams);
        charactivitybiaoqing.setTag(1);
        charactivitybiaoyuyin.setTag(1);
        charactivitybiaojia.setTag(1);
        //适配器
        setAdapter();

        charactivitybiaoqing.setOnClickListener(this);
        charactivitybiaojia.setOnClickListener(this);
        charactivitybiaoyuyin.setOnClickListener(this);
        charactivitybiaojia2.setOnClickListener(this);
        initEmoje(null);
        initListener();
        receive();
    }

    public void setAdapter() {
        adapter1 = new CChativityAdapter(ChatActivity.this, list);
        chatactivityrv.setAdapter(adapter1);
        adapter1.setListenerto(new CChativityAdapter.Listenerto() {
            @Override
            public void send(EMMessage s) {
                if (isfalse == false) {
                    EMVoiceMessageBody getbody = (EMVoiceMessageBody) s.getBody();
                    int length = getbody.getLength();
                    final String remoteUrl = getbody.getLocalUrl();
                    SpeexPlayer player = new SpeexPlayer(remoteUrl, handler);
                    player.startPlay();
                    isfalse = true;
                } else {
                    recorderInstance.setRecording(false);
                    isfalse = false;
                }
            }
        });
        adapter1.setListener(new CChativityAdapter.Listener() {
            @Override
            public void send(EMMessage s) {
                if (istrue == false) {
                    voiceBody = (EMVoiceMessageBody) s.getBody();
                    String fileName = voiceBody.getFileName();
                    SpeexPlayer player = new SpeexPlayer(fileName, handler);
                    player.startPlay();
                    istrue = true;
                } else {
                    recorderInstance.setRecording(false);
                    istrue = false;
                }
            }
        });
        adapter1.notifyDataSetChanged();
        chatactivityrv.setSelection(adapter1.getCount());
    }


    private void startvoice() {
        filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
        File file = new File(filePath + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        fileName = file + File.separator + System.currentTimeMillis() + ".spx";
        recorderInstance = new SpeexRecorder(fileName, handler);
        Thread th = new Thread(recorderInstance);
        th.start();
        recorderInstance.setRecording(true);
    }


    private void inits() {
        charactivitybiaoqing = (Button) findViewById(R.id.char_activity_biaoqing);
        charactivitybiaojia = (Button) findViewById(R.id.char_activity_jia);
        charactivitybiaojia2 = (Button) findViewById(R.id.char_activity_jia2);
        charactivitybiaoyuyin = (Button) findViewById(R.id.char_activity_yuyin);
        charactivityet = (EditTextPreIme) findViewById(R.id.char_activity_et);
        chativ3 = (ImageView) findViewById(R.id.chat_video_imageView3);
        chativ3.setBackgroundDrawable(drawable);
        iv = (ImageView) findViewById(R.id.voided_jieting_jieting);
        buttomlayoutview = (LinearLayout) findViewById(R.id.buttom_layout_view);
        buttomlayoutviewsss = (LinearLayout) findViewById(R.id.buttom_layout_viewssssfs);
        chatactivityrv = (ListView) findViewById(R.id.chat_activity_rv);
        charactivitybt = (Button) findViewById(R.id.char_activity_bt);
        chat_itme1_xiangji = (ImageView) findViewById(R.id.chat_itme1_xiangji);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.chat_video_tupian);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拨打视频电话
                VideoActivity.startTelActivity(1, userId + "", nickname, ChatActivity.this);
            }
        });
        micImage = (ImageView) findViewById(com.hyphenate.easeui.R.id.mic_image);
        //发送相册
        chat_itme1_xiangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPhoto();
            }
        });
        charactivityet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = charactivityet.getText().toString();
                if (TextUtils.isEmpty(s1)) {
                    charactivitybiaojia.setVisibility(View.VISIBLE);
                    charactivitybiaojia2.setVisibility(View.GONE);
                    return;
                }
                charactivitybiaojia2.setVisibility(View.VISIBLE);
                charactivitybiaojia.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        TextView charname = (TextView) findViewById(R.id.chat_name);
        charname.setText(nickname);
        charactivityet.setListener(this);
        charactivityet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (buttomlayoutview.getVisibility() == View.VISIBLE && buttomlayoutviewsss.getVisibility() == View.GONE) {
                    setKeyBoardModelPan();
                } else {
                    setKeyBoardModelResize();
                }

                if (buttomlayoutviewsss.getVisibility() == View.VISIBLE && buttomlayoutview.getVisibility() == View.GONE) {
                    setKeyBoardModelPan();
                } else {
                    setKeyBoardModelResize();
                }

                charactivityet.setListener(ChatActivity.this);
                return false;
            }
        });

    }

    public void toPhoto() {
        try {
            createLocalPhotoName();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String LocalPhotoName;

    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return LocalPhotoName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        switch (requestCode) {
            case 1:
                //相册
                try {
                    // 必须这样处理，不然在4.4.2手机上会出问题
                    Uri originalUri = data.getData();
                    File f = null;
                    if (originalUri != null) {
                        f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = this.getContentResolver().query(originalUri, proj, null, null, null);
                        if (null == actualimagecursor) {
                            if (originalUri.toString().startsWith("file:")) {
                                File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                if (!file.exists()) {
                                    //地址包含中文编码的地址做utf-8编码
                                    originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                }
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }
                        } else {
                            // 系统图库
                            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            String img_path = actualimagecursor.getString(actual_image_column_index);
                            if (img_path == null) {
                                InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            } else {
                                File file = new File(img_path);
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }

                        }
                        Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), Constants.RESIZE_PIC);
                        FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                        if (bitmap != null) {
                            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                                fos.close();
                                fos.flush();
                            }
                            if (!bitmap.isRecycled()) {
                                bitmap.isRecycled();
                            }
                            System.out.println("f getAbsolutePath= " + f.getAbsolutePath());
                            //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
                            EMMessage message = EMMessage.createImageSendMessage(f.getAbsolutePath(), false, userId + "");
                            EMClient.getInstance().chatManager().sendMessage(message);
                            EMClient.getInstance().chatManager().setVoiceMessageListened(message);
                            EMClient.getInstance().chatManager().updateMessage(message);
                            list.add(message);
                            handler.sendEmptyMessage(1);
                            //  handler.removeMessages(1);
                            message.setMessageStatusCallback(new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    System.out.println("1111111" + "上传成功");
                                }

                                @Override
                                public void onError(int i, String s) {
                                    System.out.println("1111111" + "上传失败");
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.char_activity_yuyin:
                hidenKeyBoard(charactivityet);
                if (buttomlayoutview.getVisibility() == View.VISIBLE) {
                    buttomlayoutview.setVisibility(View.GONE);
                }
                if (buttomlayoutviewsss.getVisibility() == View.VISIBLE) {
                    buttomlayoutviewsss.setVisibility(View.GONE);
                }
                int tag1 = (int) charactivitybiaoyuyin.getTag();
                charactivitybt.setVisibility(View.GONE);
                charactivityet.setVisibility(View.VISIBLE);
                charactivityet.setListener(null);
                setKeyBoardModelResize();
                if (tag1 == 1) {

                    charactivitybiaoyuyin.setBackgroundResource(R.drawable.c);
                    charactivitybt.setVisibility(View.GONE);
                    charactivityet.setVisibility(View.VISIBLE);
                    showKeyBoard(charactivityet);
                    charactivitybiaoyuyin.setTag(2);
                } else {

                    hidenKeyBoard(charactivityet);
                    charactivityet.setVisibility(View.GONE);
                    charactivitybt.setVisibility(View.VISIBLE);
                    charactivitybiaoyuyin.setBackgroundResource(R.drawable.b);
                    charactivitybiaoyuyin.setTag(1);
                    charactivitybt.setOnTouchListener(new View.OnTouchListener() {

                        private int ss;

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {

                                case MotionEvent.ACTION_DOWN:
                                    drawable.start();
                                    relativeLayout1.setVisibility(View.VISIBLE);
                                    Calendar Cld = Calendar.getInstance();
                                    ss = Cld.get(Calendar.SECOND);
                                    startvoice();
                                    break;
                                case MotionEvent.ACTION_UP:

                                    Calendar Clds = Calendar.getInstance();
                                    int SSs = Clds.get(Calendar.SECOND);
                                    int MIs = Clds.get(Calendar.MILLISECOND);
                                    int b = SSs - ss;
                                    if (b < 1) {
                                        drawable.stop();
                                        Toast.makeText(ChatActivity.this, "录音时间过短", Toast.LENGTH_SHORT).show();
                                        relativeLayout1.setVisibility(View.GONE);
                                        recorderInstance.setRecording(false);
                                    } else {
                                        drawable.stop();
                                        relativeLayout1.setVisibility(View.GONE);
                                        setVioesMessage(b);
                                        recorderInstance.setRecording(false);
                                    }
                                    break;
                                default:
                                    return false;
                            }
                            return true;
                        }
                    });
                }
                break;
            case R.id.char_activity_jia2:
                setTextMessage();
                charactivityet.setText("");
                break;
            case R.id.char_activity_jia:
                hidenKeyBoard(charactivityet);
                int tags = (int) charactivitybiaojia.getTag();
                if (charactivitybt.getVisibility() == View.VISIBLE) {
                    charactivitybt.setVisibility(View.GONE);
                }
                if (charactivityet.getVisibility() == View.GONE) {
                    charactivityet.setVisibility(View.VISIBLE);
                    charactivityet.setListener(null);
                }
                if (tags == 1) {
                    setKeyBoardModelPan();
                    charactivitybiaojia.setBackgroundResource(R.drawable.j);
                    buttomlayoutviewsss.setVisibility(View.VISIBLE);
                    buttomlayoutview.setVisibility(View.GONE);
                    hidenKeyBoard(charactivityet);
                    charactivitybiaojia.setTag(2);

                } else {
                    setKeyBoardModelPan();
                    charactivitybiaojia.setBackgroundResource(R.drawable.b);
                    showKeyBoard(charactivityet);
                    charactivitybiaojia.setTag(1);
                }
                break;
            case R.id.char_activity_biaoqing:
               hidenKeyBoard(charactivityet);
                int tag = (int) charactivitybiaoqing.getTag();

                if (charactivitybt.getVisibility() == View.VISIBLE) {
                    charactivitybt.setVisibility(View.GONE);
                }
                if (charactivityet.getVisibility() == View.GONE) {
                    charactivityet.setVisibility(View.VISIBLE);
                    charactivityet.setListener(null);
                }
                setKeyBoardModelPan();
                if (tag == 1) {
                    charactivitybiaoqing.setBackgroundResource(R.drawable.a);
                    buttomlayoutview.setVisibility(View.VISIBLE);
                    buttomlayoutviewsss.setVisibility(View.GONE);
                    charactivitybiaoqing.setTag(2);
                    hidenKeyBoard(charactivityet);

                } else {
                    charactivitybiaoqing.setBackgroundResource(R.drawable.a2);
                    showKeyBoard(charactivityet);
                    charactivitybiaoqing.setTag(1);
                }

                break;
        }
    }


    //发送语音
    private void setVioesMessage(int length) {
        //filePath为语音文件路径，length为录音时间(秒)
        System.out.println(fileName);
        EMMessage message = EMMessage.createVoiceSendMessage(fileName, length, userId + "");
        //       EMConversation emConversation =  EMClient.getInstance().chatManager().getConversation("1");

        EMClient.getInstance().chatManager().sendMessage(message);
        EMClient.getInstance().chatManager().setVoiceMessageListened(message);
        EMClient.getInstance().chatManager().updateMessage(message);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userId + "");
        conversation.appendMessage(message);
        conversation.insertMessage(message);
        list.add(message);
        EMVoiceMessageBody voiceBodys = (EMVoiceMessageBody) message.getBody();
        voiceBodys.setFileName(fileName);
        handler.sendEmptyMessage(1);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("Vioes_onSuccess = onSuccess " + "  ");
            }

            @Override
            public void onError(int i, String s) {
                System.out.println("Vioes_onError = onSuccess ");

            }

            @Override
            public void onProgress(int i, String s) {
                System.out.println("Vioes_onProgress = onSuccess ");
            }
        });

    }

    //发送文本消息
    public void setTextMessage() {
        String s = charactivityet.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return;
        }
        final EMMessage emMessage = EMMessage.createTxtSendMessage(s + "", userId + "");
        EMClient.getInstance().chatManager().sendMessage(emMessage);
        list.add(emMessage);
        handler.sendEmptyMessage(1);
      //  EMClient.getInstance().chatManager().getConversation(userId + "");
        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess = onSuccess " + "  ");
            }

            @Override
            public void onError(int i, String s) {
                System.out.println("onError = onSuccess ");
            }

            @Override
            public void onProgress(int i, String s) {
                System.out.println("onProgress = onSuccess ");
            }
        });

    }

    public void receive() {
        msgListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage ms : messages) {
                    conversation.insertMessage(ms);
                    conversation.appendMessage(ms);
                    handler.sendEmptyMessage(1);
                }
                //收到消息
                System.out.println("onMessageReceived messages = " + messages.get(0).getBody().toString());


                list.addAll(messages);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                System.out.println("onCmdMessageReceived messages = " + messages);

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                System.out.println("onMessageReceived messages = " + list.size());

            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
                System.out.println("onMessageDelivered messages = " + message);
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                System.out.println("onMessageChanged messages = " + message);

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

    }


    //设置输入法模式 pan
    public void setKeyBoardModelPan() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    //设置输入法模式 resize
    public void setKeyBoardModelResize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    //隐藏键盘
    public void hidenKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(charactivityet.getWindowToken(), 0);
    }

    // 显示键盘
    public void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(charactivityet, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        handler.sendEmptyMessageAtTime(1, 200);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //服务端断开连接
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (buttomlayoutview.getVisibility() == View.VISIBLE) {
                buttomlayoutview.setVisibility(View.GONE);
                charactivitybiaoqing.setTag(1);
                charactivitybiaoyuyin.setTag(1);
                charactivitybiaojia.setTag(1);
                return false;
            }
            if (buttomlayoutviewsss.getVisibility() == View.VISIBLE) {
                buttomlayoutviewsss.setVisibility(View.GONE);
                charactivitybiaoqing.setTag(1);
                charactivitybiaoyuyin.setTag(1);
                charactivitybiaojia.setTag(1);
                return false;
            }
            return super.onKeyDown(keyCode, event);

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //表情
    private void initEmoje(List<EaseEmojiconGroupEntity> emojiconGroupList) {

        if (emojiconMenu == null) {
            emojiconMenu = (EaseEmojiconMenu) View.inflate(ChatActivity.this, com.hyphenate.easeui.R.layout.ease_layout_emojicon_menu, null);
            if (emojiconGroupList == null) {
                emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(com.hyphenate.easeui.R.drawable.ee_1, Arrays.asList(EaseDefaultEmojiconDatas.getData())));
            }
            ((EaseEmojiconMenu) emojiconMenu).init(emojiconGroupList);
        }
        buttomlayoutview.addView(emojiconMenu);
    }

    //表情
    private void initListener() {

        emojiconMenu.setEmojiconMenuListener(new EaseEmojiconMenuBase.EaseEmojiconMenuListener()

        {
            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {
                if (emojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION) {
                    if (emojicon.getEmojiText() != null) {
                        charactivityet.append(EaseSmileUtils.getSmiledText(ChatActivity.this, emojicon.getEmojiText()));
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                if (!TextUtils.isEmpty(charactivityet.getText())) {
                    KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                    charactivityet.dispatchKeyEvent(event);
                }
            }
        });
    }

    @Override
    public void onBack() {
        charactivityet.setListener(null);
        System.out.println("chatTitle = onBack");
        setKeyBoardModelResize();
        buttomlayoutview.setVisibility(View.GONE);
        buttomlayoutviewsss.setVisibility(View.GONE);
        charactivitybiaoqing.setTag(1);
        charactivitybiaoyuyin.setTag(1);
        charactivitybiaojia.setTag(1);
    }


}
