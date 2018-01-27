package com.vincent.network.base;

/**
 * Created by Vincent on 23/1/2018.
 */

public interface IPresenter<V extends IBaseView> {

    void detachView();

    V getAttacchView();
}
