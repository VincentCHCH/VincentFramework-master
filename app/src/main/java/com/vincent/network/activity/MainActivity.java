package com.vincent.network.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.vincent.network.R;
import com.vincent.network.adapter.MyFragmentPagerAdapter;
import com.vincent.network.base.BaseActivity;
import com.vincent.network.utils.DensityUtil;
import com.vincent.network.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    ViewPager mViewPager;
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout ctlTitle;
    private List<String> list;
    //顶部标签
    private String[] title = {"首页", "Android", "产品", "设计", "工具资源", "ios"};
    //底部导航图片资源
    private int[] image = {R.drawable.home, R.drawable.explore, R.drawable.notifications, R.drawable.profile};
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarUpperAPI21();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initEventAndData() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.bar);
//        toolbar.setTitle("唐嫣");
        //为页面设置标题栏
        setSupportActionBar(toolbar);
        ctlTitle.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
//        ctlTitle.setExpandedTitleGravity(Gravity.CENTER);//设置展开后标题的位置
        ctlTitle.setTitle("Vincent");
        ctlTitle.setExpandedTitleColor(Color.WHITE);//设置展开后标题的颜色
        ctlTitle.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色
//        ctlTitle.setExpandedTitleTextAppearance(DensityUtil.sp2px(6));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //设置tabLayout模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TabLayout tabLayoutTwo = (TabLayout) findViewById(R.id.tab_layout_two);
        tabLayoutTwo.setTabMode(TabLayout.MODE_FIXED);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "heelowlrddsfs--", Toast.LENGTH_LONG).show();
            }
        });
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);


        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        tabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
//        one = tabLayout.getTabAt(0);
//        two = tabLayout.getTabAt(1);
//        three = tabLayout.getTabAt(2);

        //设置tabLayout标题
//        for (String aTitle : title) {
//            tabLayout.addTab(tabLayout.newTab().setText(aTitle));
//        }
        //自定义的tab布局
        for (int anImage : image) {
            View view = getLayoutInflater().inflate(R.layout.tab, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            imageView.setImageResource(anImage);
            //添加标签
            tabLayoutTwo.addTab(tabLayoutTwo.newTab().setCustomView(view));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }


    private void setStatusBarUpperAPI21(){
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        //由于setStatusBarColor()这个API最低版本支持21, 本人的是15,所以如果要设置颜色,自行到style中通过配置文件设置
//        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
//            StatusBarUtil.hideSystemUI(this);
        }
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
