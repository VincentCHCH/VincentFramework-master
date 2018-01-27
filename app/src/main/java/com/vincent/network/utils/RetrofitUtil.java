package com.vincent.network.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vincent on 26/1/2018.
 */

public class RetrofitUtil {

    public static final String BASE_URL = "http://sarrs.go.letv.com/sarrs/";
    private static volatile RetrofitUtil mRetrofitUtils;
    private Retrofit mRetrofit;

    private RetrofitUtil() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitUtil getRetrofitInstance() {
        if (mRetrofitUtils == null) {
            synchronized (RetrofitUtil.class) {
                mRetrofitUtils = new RetrofitUtil();
            }
        }
        return mRetrofitUtils;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }


}
