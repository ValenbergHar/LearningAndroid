package com.example.customlistadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonAdapter extends BaseAdapter {
    Activity mActivity;
    MyFriends myFriends;

    public PersonAdapter(Activity mActivity, MyFriends myFriends) {
        this.mActivity = mActivity;
        this.myFriends = myFriends;
    }

    @Override
    public int getCount() {
        return myFriends.getMyFriendList().size();
    }

    @Override
    public Object getItem(int position) {
        return myFriends.getMyFriendList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View onePersonLine;
        LayoutInflater layoutInflater =(LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onePersonLine=layoutInflater.inflate(R.layout.person_one_line,parent,false);
        TextView tv_name = onePersonLine.findViewById(R.id.txtName);
        TextView tv_age = onePersonLine.findViewById(R.id.txtAge);
        ImageView iv_icon=onePersonLine.findViewById(R.id.imageView);

        return null;
    }
}
