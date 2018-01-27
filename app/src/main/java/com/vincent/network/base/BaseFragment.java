package com.vincent.network.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vincent.network.utils.LogUtil;

import butterknife.ButterKnife;

/**
 * Created by Vincent on 24/1/2018.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {
    private final String TAG = this.getClass().getName();
    protected IPresenter mPresenter;
    protected View mView;
    protected Activity mContext;

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof BaseActivity) {
            mContext = activity;
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.log(LogUtil.LogLevel.INFO, TAG, "onCreateView---");
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.log(LogUtil.LogLevel.INFO, TAG, "onViewCreated---");
        ButterKnife.bind(this, view);
        initEventAndData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }


    /**
     * 依赖注入的入口
     */
//    protected abstract void setupActivityComponent(AppComponent appComponent, FragmentModule fragmentModule);
    protected abstract int getLayoutId();

    protected abstract void initEventAndData();
}
