package com.vincent.framework.base;

import android.content.Context;

/**
 * Created by Vincent on 23/1/2018.
 */

public interface ILoadingBaseView extends IBaseView {
    void showLoading();
    void showContent();
    void showNotData();
    void showError(String msg);
    Context context();
}
