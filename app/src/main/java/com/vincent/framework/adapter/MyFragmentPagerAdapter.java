package com.vincent.framework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vincent.framework.fragment.ImageTestFragment;
import com.vincent.framework.fragment.MineFragment;
import com.vincent.framework.utils.LogUtil;

/**
 * Created by Vincent on 24/1/2018.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final String TAG = this.getClass().getName();
    //顶部标签
    private String[] title = {"首页", "Android", "产品", "设计", "工具资源", "ios"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.log(LogUtil.LogLevel.INFO, TAG, "position: " + position);
        if (position == 1) {
            return new MineFragment();
        } else if (position == 2) {
            return new ImageTestFragment();
        }
        return new MineFragment();
    }

    @Override
    public int getCount() {
        return title.length;
    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


}
