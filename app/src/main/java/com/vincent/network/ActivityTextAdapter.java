package com.vincent.network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vincent.network.bean.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTextAdapter extends BaseAdapter {

    private List<Person> objects = new ArrayList<Person>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ActivityTextAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Person getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_text, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((Person)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Person object, ViewHolder holder) {
        //TODO implement
    }

    static class ViewHolder {
    @BindView(R.id.text1) TextView text1;
    @BindView(R.id.text2) TextView text2;
    @BindView(R.id.text3) TextView text3;
    @BindView(R.id.text4) TextView text4;
    @BindView(R.id.text5) TextView text5;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
