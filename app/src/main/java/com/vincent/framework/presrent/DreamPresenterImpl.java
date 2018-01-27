package com.vincent.framework.presrent;


import com.vincent.framework.bean.DreamData;
import com.vincent.framework.model.DreamModel;
import com.vincent.framework.model.DreamModelImpl;
import com.vincent.framework.view.DreamView;

import java.util.List;


/**
 * Created by Administrator on 2016/4/21.
 */
public class DreamPresenterImpl  implements DreamPresenter ,DreamModelImpl.DreamOnlistener{

    private DreamModel mDreamModel;
    private DreamView mDreamView;

    public DreamPresenterImpl(DreamView dreamView) {
        this.mDreamView = dreamView;
        this.mDreamModel = new DreamModelImpl(this);
    }

    @Override
    public void getDreamData(String key) {
        mDreamView.showProgress();
        mDreamModel.getDreamData(key);

    }

    @Override
    public void onSuccess(List<DreamData.ResultBean> s) {
        mDreamView.loadDreamBean(s);
        mDreamView.hideProgress();
    }

    @Override
    public void onFailure(Throwable e) {
        mDreamView.hideProgress();
    }
}
