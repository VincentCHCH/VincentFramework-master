package com.vincent.framework.http;

import android.util.Log;

import com.vincent.framework.LibApplication;
import com.vincent.framework.common.Constants;
import com.vincent.framework.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Vincent on 24/1/2018.
 */

public class OkHttpManager {
    private static OkHttpClient mOkHttpClient;

    public static OkHttpClient getInstance() {

        if (mOkHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (mOkHttpClient == null) {
                    //Retrofit2.0 怎么打印请求到的json字符串和查看log呢
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    Interceptor apikey = new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            return chain.proceed(chain.request())
                                    .newBuilder()
                                    .addHeader("apikey", Constants.Api_Key)
                                    .build();
                        }
                    };

                    File cacheFile = new File(LibApplication.getContext().getCacheDir(), "cache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
                    mOkHttpClient = new OkHttpClient.Builder()
                            .readTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .addInterceptor(apikey)
                            .addInterceptor(loggingInterceptor)
                            .addNetworkInterceptor(new HttpCacheInterceptor())
                            .cache(cache)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetworkAvailable(LibApplication.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetworkAvailable(LibApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

}
