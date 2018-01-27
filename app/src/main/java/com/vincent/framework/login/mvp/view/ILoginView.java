package com.vincent.framework.login.mvp.view;

import android.app.Dialog;

/**
 * Created by Vincent on 25/1/2018.
 */
public interface ILoginView {

    /**
     * 获取view层的dialog
     * @return
     */
    Dialog getDialog();

    /**
     * 获取用户名 参数
     * @return username
     */
    String getUserName();

    /**
     * 获取密码
     * @return password
     */
    String getPassword();

    /**
     * 弹出消息
     * @param msg
     */
    void showMsg(String msg);

    /**
     * 将数据返回给view
     * @param result
     */
    void setText(String result);
}
