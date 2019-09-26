package cn.lattice.cabinet.ui;

import android.content.Context;

import com.uuzuche.lib_zxing.ZApplication;


/**
 * @author Dankal Android Developer
 * @since 2018/5/9
 */

public class RocApplication extends ZApplication {
    //是否是开发环境
    public static final boolean isDev = true;

    private static RocApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }



    public static Context getContext() {
        return context;
    }


    private static boolean login;

}
