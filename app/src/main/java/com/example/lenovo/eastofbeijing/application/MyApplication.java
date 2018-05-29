package com.example.lenovo.eastofbeijing.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.dash.zxinglibrary.activity.ZXingLibrary;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class MyApplication extends Application {

    private static int myTid;
    private static Handler handler;
    private static Context context;

    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Fresco
        Fresco.initialize(this);
        //初始化Zxing库
        ZXingLibrary.initDisplayOpinion(this);
        myTid = Process.myTid();
        handler = new Handler();
        context = getApplicationContext();
        UMShareAPI.get(this);
        Config.DEBUG = true;
    }

    public static int getMainThreadId() {
        return myTid;
    }

    public static Handler getAppHanler() {
        return handler;
    }

    public static Context getAppContext() {
        return context;
    }
}