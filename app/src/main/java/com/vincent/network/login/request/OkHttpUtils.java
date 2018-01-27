package com.vincent.network.login.request;

import com.vincent.network.common.Constants;
import com.vincent.network.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vincent on 25/1/2018.
 */
public class OkHttpUtils {
    private final static String TAG = "OkHttpUtils";
    /**
     * okhttp
     */
    private static OkHttpClient okHttpClient;


    /**
     * Retrofit
     */
    private static Retrofit retrofit;

    /**
     * 获取Retrofit的实例
     *
     * @return retrofit
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.88:8888/gdmsaec-app/act/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .addNetworkInterceptor(new HttpLoggingInterceptor())
                    .build();
        }





        return okHttpClient;
    }

    public static ApiService createService(String baseUrl) {
        //ok的配置
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        //这里你可以拦截添加个公共参数什么的等等,干啥都行
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        });

        OkHttpClient client = builder.build();
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(baseUrl)
                        .client(client)
                        //Gson的转换工厂
                        .addConverterFactory(GsonConverterFactory.create())
                        //Observable的适配
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
        return retrofit.create(ApiService.class);
    }

   static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            //请求前--打印请求信息
            long t1 = System.nanoTime();
            LogUtil.log(LogUtil.LogLevel.INFO, TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            //网络请求
            //调用chain.proceed(request)是每个拦截器实现的关键部分。
            // 这个看似简单的方法是所有HTTP 工作发生的地方， 在这里产生一个响应应答请求。
            Response response = chain.proceed(request);

            //网络响应后--打印响应信息
            long t2 = System.nanoTime();
            LogUtil.log(LogUtil.LogLevel.INFO, TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

}
