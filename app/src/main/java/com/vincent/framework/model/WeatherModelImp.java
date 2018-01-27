package com.vincent.framework.model;


import android.text.TextUtils;

import com.vincent.framework.api.ApiManager;
import com.vincent.framework.bean.WeatherData;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lidong on 2016/3/2.
 */
public class WeatherModelImp implements WeatherModel {

    private WeatherOnListener mWeatherOnListener;


    public WeatherModelImp(WeatherOnListener mWeatherOnListener) {
        this.mWeatherOnListener = mWeatherOnListener;
    }

    @Override
    public Subscription getWeatherData(String format, String city) {
        Observable<WeatherData> request = ApiManager.getWeatherData(format, city).cache();

        Subscription sub = request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<WeatherData>() {
//                    @Override
//                    public void call(WeatherData weatherData) {
//                        mWeatherOnListener.onSuccess(weatherData);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        mWeatherOnListener.onFailure(throwable);
//                    }
//                }
                .subscribe(new Subscriber<WeatherData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mWeatherOnListener.onFailure(e);
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        mWeatherOnListener.onSuccess(weatherData);
                    }
                });
        return sub;


    }

    private void onError(Throwable e) {
//        dismissProgressDialog();
        String msg;
        if (e instanceof SocketTimeoutException) {
            msg = "请求超时。请稍后重试！";
        } else if (e instanceof ConnectException) {
            msg = "请求超时。请稍后重试！";
        } else {
            msg = "请求未能成功，请稍后重试！";
        }
        if (!TextUtils.isEmpty(msg)) {
//            onFailure(msg);
        }
    }

    public interface WeatherOnListener {
        void onSuccess(WeatherData s);

        void onFailure(Throwable e);
    }
}
