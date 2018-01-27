package com.vincent.network.api;


import com.vincent.network.bean.DreamData;
import com.vincent.network.bean.WeatherData;
import com.vincent.network.bean.WinXinData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author : lidong
 * @classnmae : ApiManager
 * @describe :
 * @createtime : 2016/4/5 16:55
 * @company : chni
 * @email : lidong@chni.com.cn
 **/
public class ApiManager {
    /**
     * 基础地址
     */
    private static final String ENDPOINT = "http://v.juhe.cn";

    private static final Retrofit sRetrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .build();

    private static final ApiManagerService apiManager = sRetrofit.create(ApiManagerService.class);

    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(60 * 1000, TimeUnit.MILLISECONDS).readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    public Retrofit provideRetrofit() {
        return new Retrofit.Builder().client(provideOkHttpClient()) //
                .baseUrl(ENDPOINT) //
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //
                .build();
    }

    public ApiManagerService provideApiManagerService() {
        return provideRetrofit().create(ApiManagerService.class);
    }
    /**
     * 获取天气数据
     *
     * @param city
     * @return
     */
    public static Observable<WeatherData> getWeatherData(String format, String city) {
        Observable<WeatherData> ss = apiManager.getWeatherData(format, city, "ad1d20bebafe0668502c8eea5ddd0333");
        return ss;
    }

    /**
     * 获取周公解梦类型
     *
     * @param key
     * @return
     */
    public static Observable<DreamData> getDreamData(String key) {
        Observable<DreamData> ss = apiManager.getDreamData(key);
        return ss;
    }


    /**
     * 获取微信咨询
     *
     * @param pno
     * @param ps
     * @param key
     * @return
     */
    public static Observable<WinXinData> getWeiXinData(int pno, String ps, String key) throws Exception {
        Observable<WinXinData> ss = apiManager.getWinXinNewData(pno, ps, key);
        return ss;
    }

}
