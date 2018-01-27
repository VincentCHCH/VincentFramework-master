package com.vincent.network.base.page;

import android.content.Context;

import com.vincent.network.base.IBaseView;


public interface IBaseDataVIew extends IBaseView {
    void showLoading();
    void showContent();
    void showNotData();
    void showError(String msg);
    Context context();
}
