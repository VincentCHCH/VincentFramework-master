package com.vincent.framework.login.mvp.view.impl;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.vincent.framework.R;
import com.vincent.framework.base.BaseActivity;
import com.vincent.framework.base.BasePresenter;
import com.vincent.framework.login.mvp.presenter.LoginPresenter;
import com.vincent.framework.login.mvp.view.ILoginView;
import com.vincent.framework.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Vincent on 25/1/2018.
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.id_et_username)
    EditText etUserName;
    @BindView(R.id.id_et_password)
    EditText etPassword;
    @BindView(R.id.textview)
    TextView textView;
    @BindView(R.id.id_btn_login)
    Button btnLogin;
    private Dialog dialog;

    /**
     * p层对象 来调取p层的方法
     */
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        /**
         * 实例化p  构造方法需要一个实现了ILoginView的类 当前activity实现了ILoginView 所以直接传this即可
         */
        loginPresenter = new LoginPresenter(this);

        dialog = DialogUtils.createLoadingDialog(this, "登陆中...");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public Dialog getDialog() {
        return dialog;
    }

    @Override
    public String getUserName() {
        return etUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setText(String result) {
        textView.setText(result);
    }

    @OnClick(R.id.id_btn_login)
    public void onClick() {
        /**
         * 调用登录方法进行登陆
         */
        loginPresenter.login();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMsg(String e) {

    }
}
