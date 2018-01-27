package com.vincent.network.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vincent.network.R;
import com.vincent.network.bean.Person;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhoujian on 2017/1/4.
 */

public class MainAdapter extends BaseAdapter {
    private List<Person> mList;
    private Context context;


    public MainAdapter(Context mContext, List<Person> list) {
        this.mList = list;
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        View view;
        final ViewHolder holder;
        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(context, R.layout.list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        if (mList != null && mList.size() > 0) {
              holder.mTvName.setText(mList.get(position).getName());
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
