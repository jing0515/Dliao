package com.bawei.lvwenjing.dliao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.bawei.lvwenjing.dliao.speex.SpeexPlayer;
import com.bawei.lvwenjing.dliao.speex.SpeexRecorder;
import com.bawei.lvwenjing.dliao.utils.SDCardUtils;

import java.io.File;

public class MainActivity extends Activity {
    Handler handler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
            }
        }
    } ;
    private String fileName;

    private Button bt1;
    private SpeexRecorder recorderInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
        Button bt3 = (Button) findViewById(R.id.bt3);

        bt1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
                System.out.println("filePath:" + filePath);
                File file = new File(filePath  + "/");
                System.out.println("file:" + file);
                if (!file.exists()) {
                    file.mkdirs();
                }
                fileName = file + File.separator + System.currentTimeMillis() + ".spx";
                System.out.println("保存文件名：＝＝ " + fileName);
                recorderInstance = new SpeexRecorder(fileName, handler);
                Thread th = new Thread(recorderInstance);
                th.start();
                recorderInstance.setRecording(true);            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorderInstance.setRecording(false);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeexPlayer player = new SpeexPlayer(fileName,handler);
                player.startPlay();
            }
        });
        startActivity(new Intent(MainActivity.this, SpalshActivity.class));
    }
}
