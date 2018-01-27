package com.vincent.network.presrent;


import com.vincent.network.bean.WinXinData;
import com.vincent.network.model.WinXinDataModel;
import com.vincent.network.model.WinXinDataModelImpl;
import com.vincent.network.view.WeixinNewsView;

import java.util.List;


/**
*@类名 : WinXinDataPressnterImpl
*@描述 : 
*@时间 : 2016/4/20  14:10
*@作者: 李东
*@邮箱  : lidong@chni.com.cn
*@company: chni
*/
public class WinXinDataPressnterImpl implements WinXinDataPresenter,WinXinDataModelImpl.WeiXinDataOnListener {
    
    
    private WinXinDataModel mWinXinDataModel;
    private WeixinNewsView mWeixinNewsView;

    public WinXinDataPressnterImpl(WeixinNewsView weixinNewsView) {
        mWinXinDataModel = new WinXinDataModelImpl(this);
        mWeixinNewsView = weixinNewsView;
    }

    @Override
    public void getWeiXinData(int pno, String ps, String key) throws Exception {
        mWinXinDataModel.getWeiXinData(pno,ps,key);
    }


    @Override
    public void onSuccess(List<WinXinData.ResultBean.ListBean> s) {
        try {
            mWeixinNewsView.loadWeiXinData(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }
}
