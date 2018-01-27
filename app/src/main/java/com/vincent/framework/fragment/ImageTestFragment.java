package com.vincent.framework.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vincent.framework.R;
import com.vincent.framework.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Vincent on 25/1/2018.
 */

public class ImageTestFragment extends BaseFragment {
    private final String url = "http://www.internetke.com/public/images/jsImg/inkbigImg_1352250790.jpg";
    @BindView(R.id.test_iv1)
    ImageView mTestIv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_test;
    }

    @Override
    protected void initEventAndData() {
        /*
        蓝色的三角形，表示该图片是从磁盘加载的，另外如果为红色则表示从网络加载，如果为绿色表示从内存加载。
         */
        Picasso.with(getContext()).setIndicatorsEnabled(true);
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.tangyan)
                .error(R.mipmap.ic_launcher)
                .into(mTestIv1);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.test_iv1)
    public void onViewClicked() {
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
