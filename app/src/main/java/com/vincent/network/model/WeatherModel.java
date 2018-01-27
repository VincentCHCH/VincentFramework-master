package com.vincent.network.model;

import com.vincent.network.base.IBaseModel;

import rx.Subscription;

/**
 *
 * Created by lidong on 2016/3/2.
 */
public interface WeatherModel extends IBaseModel{
    /**
     * 获取天气信息
     * @param format
     * @param city
     */
    Subscription getWeatherData(String format, String city);


}
