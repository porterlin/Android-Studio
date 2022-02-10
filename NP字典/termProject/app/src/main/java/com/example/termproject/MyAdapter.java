package com.example.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    private String[] mString;
    private Context mContext;
    private String[] mCheckComplete;
    int mResource;

    public MyAdapter(Context context,int resource, String[] dataName, String[] checkComplete) {
        mString = dataName;
        mContext = context;
        mResource = resource;
        mCheckComplete = checkComplete;
    }

    @Override
    public int getCount() {
        return mString.length;
    }

    @Override
    public Object getItem(int i) {
        return mString[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String titleStr = getItem(i).toString();
        String checkOne = mCheckComplete[i];

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(mResource, viewGroup, false);

        TextView title = view.findViewById(R.id.title);
        TextView check = view.findViewById(R.id.checkComplete);

        title.setText(titleStr);
        if (checkOne.equals("true"))
            check.setText("完成");
        else
            check.setText("");
        return view;
    }
}