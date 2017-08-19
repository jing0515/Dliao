package com.bawei.lvwenjing.dliao.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.Log;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.db.DaoMaster;
import com.bawei.lvwenjing.dliao.db.DaoSession;
import com.bawei.lvwenjing.dliao.utils.PreferencesUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.mob.MobApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by lenovo-pc on 2017/7/4.
 */

public class IApplication extends MobApplication {
    public static IApplication application;
    private final static String dbName = "texts.db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化七牛直播
        StreamingEnv.init(getApplicationContext());

        //调用.so文件中的 方法
        System.loadLibrary("core");
        System.loadLibrary("speex");
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化环信
        EMClient.getInstance().init(this, options);

        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EaseUI.getInstance().init(this, options);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }


        EMClient.getInstance().setDebugMode(true);


        //进行小数点的截取
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");

        application = this;
        //建立数据库
        openHelper = new DaoMaster.DevOpenHelper(this, dbName);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        Fresco.initialize(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);


        //用这个每次都会起到。浪费内存
        CrashReport.initCrashReport(getApplicationContext(), "c3a52432b5", false);

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "a2cc40497b", true, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        //   CrashReport.initCrashReport(context, strategy);
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }

    public static IApplication getApplication() {
        if (application == null) {
            application = getApplication();
        }
        return application;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public void emLogin() {
        String username = PreferencesUtils.getValueByKey(this, "uid", 0) + "";
        String password = PreferencesUtils.getValueByKey(this, "yxpassword", "0");

        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("password = onSuccess");
            }

            @Override
            public void onError(int i, String s) {
                System.out.println("password onError = " + i + " s " + s);

            }

            @Override
            public void onProgress(int i, String s) {
                System.out.println("password onProgress = " + i);

            }
        });

    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public static void ring() {
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        soundPool.load(application, R.raw.avchat_ring, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1, 1, 0, 0, 1);
            }
        });

    }


    public static void callTo() {
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        soundPool.load(application, R.raw.avchat_connecting, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1, 1, 0, 0, 1);
            }
        });


    }
}
