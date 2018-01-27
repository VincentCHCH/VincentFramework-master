package com.vincent.framework.presrent;

import android.util.Log;

import com.vincent.framework.base.BasePresenter;
import com.vincent.framework.bean.WeatherData;
import com.vincent.framework.model.WeatherModel;
import com.vincent.framework.model.WeatherModelImp;
import com.vincent.framework.view.WeatherView;

public class WeatherPresenter extends BasePresenter <WeatherView, WeatherModel> implements WeatherModelImp.WeatherOnListener {

    public WeatherPresenter(WeatherView view) {
        mModel = new WeatherModelImp(this);
        mView = view;
    }

    /**
     * 获取天气信息
     *
     * @param format
     * @param city
     */
    public void getWeatherData(String format, String city) {
        addSubscription(mModel.getWeatherData(format, city));
        mView.showProgress();
    }

    @Override
    public void onSuccess(WeatherData s) {
        mView.hideProgress();
        mView.loadWeather(s);
        Log.d("-------", "onSuccess() called with: " + "s = [" + s.toString() + "]");
    }

    @Override
    public void onFailure(Throwable e) {
        mView.hideProgress();
        Log.d("-------", "onFailure" + e.getMessage());
    }

}
