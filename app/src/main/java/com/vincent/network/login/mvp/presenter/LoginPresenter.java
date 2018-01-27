package com.vincent.network.login.mvp.presenter;

import com.vincent.network.login.domain.UserInfo;
import com.vincent.network.login.mvp.model.ILoginModel;
import com.vincent.network.login.mvp.model.impl.LoginModel;
import com.vincent.network.login.mvp.view.ILoginView;

/**
 * Created by Vincent on 25/1/2018.
 */
public class LoginPresenter {

    /**
     * v层
     */
    private ILoginView loginView;

    /**
     * m层
     */
    private ILoginModel loginModel;

    /**
     * mvp模式  p层持有  v 和m 的接口引用 来进行数据的传递  起一个中间层的作用
     *
     * @param loginView
     */
    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModel();
    }

    /**
     * 用户登陆
     */
    public void login() {
        loginModel.login(loginView.getDialog(), loginView.getUserName(), loginView.getPassword(), new LoginModel.OnLoginListener() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                loginView.setText(userInfo.toString());
            }

            @Override
            public void onFailure(String msg) {
                loginView.showMsg(msg);
            }
        });
    }
}
