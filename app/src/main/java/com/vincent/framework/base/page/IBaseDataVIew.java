package com.vincent.framework.base.page;

import android.content.Context;

import com.vincent.framework.base.IBaseView;


public interface IBaseDataVIew extends IBaseView {
    void showLoading();
    void showContent();
    void showNotData();
    void showError(String msg);
    Context context();
}
