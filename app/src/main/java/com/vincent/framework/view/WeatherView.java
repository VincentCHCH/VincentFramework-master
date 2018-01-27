package com.vincent.framework.view;


import com.vincent.framework.base.IBaseView;
import com.vincent.framework.bean.WeatherData;

/**
 * Created by lidong on 2016/3/2.
 */
public interface WeatherView extends IBaseView {

    void loadWeather(WeatherData weatherData);
}
