package com.vincent.framework.view;


import com.vincent.framework.bean.DreamData;

import java.util.List;


/**
 * Created by Administrator on 2016/4/21.
 */
public interface DreamView {

    void showProgress();
    void hideProgress();
    void loadDreamBean(List<DreamData.ResultBean> s);

}
