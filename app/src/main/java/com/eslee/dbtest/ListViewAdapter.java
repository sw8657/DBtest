package com.eslee.dbtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-02-20.
 */

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleView = (TextView) view.findViewById(R.id.listitem_title);
        TextView datetimeView = (TextView) view.findViewById(R.id.listitem_datetime);
        TextView pointView = (TextView) view.findViewById(R.id.listitem_point);
        TextView pointDetailView = (TextView) view.findViewById(R.id.listitem_pointdetail);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(pos);

        // 아이템 내 각 위젯에 데이터 반영
        titleView.setText(listViewItem.getTitle());
        datetimeView.setText(listViewItem.getDateTime());
        pointView.setText(String.valueOf(listViewItem.getPoint()));
        pointDetailView.setText(listViewItem.getPointStr());

        return view;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String datetime, int point, String point_detail) {
        ListViewItem item = new ListViewItem();

        item.setTitle(title);
        item.setDateTime(datetime);
        item.setPoint(point);
        item.setPointStr(point_detail);

        listViewItemList.add(item);
    }

    public void Clear(){
        listViewItemList.clear();
    }
}
