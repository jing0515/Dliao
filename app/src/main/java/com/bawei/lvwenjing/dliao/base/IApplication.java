package com.bawei.lvwenjing.dliao.base;

import com.mob.MobApplication;

/**
 * Created by lenovo-pc on 2017/7/4.
 */

public class IApplication extends MobApplication {
    public static IApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static IApplication getApplication() {
        if (application == null) {
            application = getApplication();
        }
        return application;
    }


}
