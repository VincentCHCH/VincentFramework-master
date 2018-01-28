package com.vincent.framework.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lidong on 16/9/10.
 */
public class BasePresenter<V extends IBaseView, M extends IBaseModel> implements IPresenter<IBaseView> {

    protected V mView;
    protected M mModel;
    private CompositeSubscription mCompositeSubscription;

    //RXjava取消注册，以避免内存泄露
    protected void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    //RXjava注册
    protected void addSubscription(Subscription subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }

    @Override
    public void detachView() {
        this.mView = null;
        onUnsubscribe();
    }

    @Override
    public IBaseView getAttacchView() {
        return null;
    }

    public void onSuccess(Object s) {

    }

    public void onFailure(Throwable e) {

    }
}
