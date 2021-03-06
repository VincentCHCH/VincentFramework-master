package com.vincent.framework.login.mvp.model.impl;

import android.app.Dialog;

import com.vincent.framework.login.domain.UserInfo;
import com.vincent.framework.login.mvp.model.ILoginModel;
import com.vincent.framework.login.request.ApiService;
import com.vincent.framework.login.request.DialogSubscriber;
import com.vincent.framework.login.request.OkHttpUtils;
import com.vincent.framework.login.request.ResponseResult;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vincent on 25/1/2018.
 */
public class LoginModel implements ILoginModel {


    @Override
    public void login(Dialog dialog, String username, String password, final OnLoginListener loginListener) {

        /**
         * 被订阅者
         */
        Observable<ResponseResult<UserInfo>> observable = OkHttpUtils.getRetrofit().create(ApiService.class).login(username, password);

        /**
         * 订阅者
         */
        Subscriber<ResponseResult<UserInfo>> subscriber = new DialogSubscriber<ResponseResult<UserInfo>>(dialog) {
            @Override
            public void onSuccess(ResponseResult<UserInfo> userInfoUserResponseResult) {
                switch (userInfoUserResponseResult.getResult()) {
                    case 1:
                        loginListener.onSuccess(userInfoUserResponseResult.getData());
                        break;
                    case 0:
                        loginListener.onFailure(userInfoUserResponseResult.getMsg());
                        break;
                    case -1:
                        loginListener.onFailure(userInfoUserResponseResult.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(String msg) {
                loginListener.onFailure(msg);
            }
        };

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 回调接口用来把数据返回给p
     */
    public interface OnLoginListener {

        /**
         * 请求成功的回调方法
         * @param userInfo  用户信息
         */
        void onSuccess(UserInfo userInfo);

        /**
         * 请求失败的回调方法
         * @param msg   失败的信息
         */
        void onFailure(String msg);
    }
}
