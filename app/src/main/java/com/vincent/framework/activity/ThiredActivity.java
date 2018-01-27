package com.vincent.framework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.vincent.framework.R;
import com.vincent.framework.adapter.MainAdapter;
import com.vincent.framework.bean.Person;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * Created by zhoujian on 2016/12/12.
 */

public class ThiredActivity extends Activity {


    private ArrayList<Person> list;
    @BindView(R.id.list_person)
    ListView mListPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thired);
        ButterKnife.bind(this);
        initDatas();
        setDatas();
    }

    private void setDatas() {
        MainAdapter adapter = new MainAdapter(ThiredActivity.this, list);
        mListPerson.setAdapter(adapter);
    }




    @OnItemClick(R.id.list_person)
    public void onItemClick(int position) {
        Toast.makeText(this, list.get(position).getName() + "ListView的item被点击啦！", Toast.LENGTH_SHORT).show();
    }

    @OnItemLongClick(R.id.list_person)
    public boolean onItemLongClick(View view, int position) {
        Toast.makeText(this, list.get(position).getName() + "被--长按--啦！", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void initDatas() {
        list = new ArrayList<Person>();
        Person person1 = new Person("贾宝玉");
        list.add(person1);
        Person person2 = new Person("林黛玉");
        list.add(person2);
        Person person3 = new Person("薛宝钗");
        list.add(person3);
        Person person4 = new Person("王熙凤");
        list.add(person4);
        Person person5 = new Person("史湘云");
        list.add(person5);
        Person person6 = new Person("贾探春");
        list.add(person6);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
