package com.vincent.network.view;


import com.vincent.network.base.IBaseView;
import com.vincent.network.bean.WeatherData;

/**
 * Created by lidong on 2016/3/2.
 */
public interface WeatherView extends IBaseView {

    void loadWeather(WeatherData weatherData);
}
