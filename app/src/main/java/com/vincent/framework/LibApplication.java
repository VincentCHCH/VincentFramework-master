package com.vincent.framework;

import android.app.Application;
import android.content.Context;

/**
 * Created by h127856 on 16/10/8.
 */
public class LibApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    /** Instance of the current application. */
    private static volatile Application mInstance;

    /**
     * Constructor.
     */
    public LibApplication() {
        mInstance = this;
    }

    /**
     * Gets the application context.
     *
     * @return the application context
     */
    public static Context getContext() {
        return mInstance;
    }


    public static void setApplication(Application application){
        mInstance = application;
    }
}
