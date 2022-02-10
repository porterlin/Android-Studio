package com.cornez.shippingcalculator;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class ListView_Class extends BaseAdapter {
    private Activity activity;
    private List mList;
    private List mListShow; // 這個用來記錄哪幾個 item 是被打勾的

    private static LayoutInflater inflater = null;

    public ListView_Class(Activity a, List list, List listShow) {
        activity = a;
        mList = list;
        mListShow = listShow;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.listview_item, null);
        }

        CheckedTextView chkBshow = (CheckedTextView) vi.findViewById(R.id.checkItem);

        chkBshow.setText(mList.get(position).toString());

        //設定是否打勾
        chkBshow.setChecked((Boolean) mListShow.get(position));

        Log.d("KK", "getView(" + position + ", convertView, ViewGroup parent)");

        return vi;
    }
}

