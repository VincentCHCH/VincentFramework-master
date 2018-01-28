package com.vincent.framework.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.vincent.framework.R;
import com.vincent.framework.base.BaseActivity;
import com.vincent.framework.bean.WeatherData;
import com.vincent.framework.presrent.WeatherPresenter;
import com.vincent.framework.utils.LoadingUIHelper;
import com.vincent.framework.view.WeatherView;

import butterknife.BindView;
import butterknife.OnClick;

public class WeatherActivity extends BaseActivity<WeatherPresenter> implements WeatherView {
    private final String TAG = "WeatherActivity";

    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView9)
    TextView textView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.button2)
    public void clickButton2() {
        getPresenter().getWeatherData("2", "苏州");
    }

    @Override
    public void showProgress() {
        LoadingUIHelper.showDialogForLoading(this, "获取数据", false);
    }

    @Override
    public void hideProgress() {
        LoadingUIHelper.hideDialogForLoading();
        Toast.makeText(this, "你的免费数据已经用完", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMsg(String e) {

    }

    @Override
    public void loadWeather(WeatherData weatherData) {
        Log.i(TAG, weatherData.toString());
//        textView1.setText("城市：" + weatherData.getResult().getToday().getCity());
//        textView2.setText("日期：" + weatherData.getResult().getToday().getWeek());
//        textView3.setText("今日温度：" + weatherData.getResult().getToday().getTemperature());
//        textView4.setText("天气标识：" + WeatherIDUtils.transfer(weatherData.getResult().getToday().getWeather_id().getFa()));
//        textView5.setText("穿衣指数：" + weatherData.getResult().getToday().getDressing_advice());
//        textView6.setText("干燥指数：" + weatherData.getResult().getToday().getDressing_index());
//        textView7.setText("紫外线强度：" + weatherData.getResult().getToday().getUv_index());
//        textView8.setText("穿衣建议：" + weatherData.getResult().getToday().getDressing_advice());
//        textView9.setText("旅游指数：" + weatherData.getResult().getToday().getTravel_index());
    }

    @Override
    protected int getLayout() {
        return R.layout.content_weather;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected WeatherPresenter createPresenter() {
        return new WeatherPresenter(this);
    }

}
