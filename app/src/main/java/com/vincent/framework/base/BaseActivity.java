package com.vincent.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vincent.framework.navigator.Navigator;
import com.vincent.framework.utils.AppManager;

import butterknife.ButterKnife;

/**
 * Created by Vincent on 24/1/2018.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    protected P mPresenter;
    protected Activity mContext;
    protected Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = createPresenter();
        initEventAndData();
        AppManager.getAppManager().addActivity(this);
    }

    protected void setCommonBackToolBack(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 依赖注入的入口
     */
//    protected abstract void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule);
    protected abstract int getLayout();

    protected abstract void initEventAndData();

    protected abstract P createPresenter();

    /**
     * 获取presenter
     */
    protected  P getPresenter(){
        return mPresenter;
    }
}
