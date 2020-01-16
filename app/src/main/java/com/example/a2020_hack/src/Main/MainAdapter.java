package com.example.a2020_hack.src.Main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.a2020_hack.R;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {
    ArrayList<MainListItem> mMainListItems;
    LayoutInflater mInflater;

    public MainAdapter(ArrayList<MainListItem> items, Context context) {
        mMainListItems = items;
        mInflater = LayoutInflater.from(context);
    }

    //뷰홀더
    public class ViewHolder {
        TextView tvDeparture,tvDepartureDetail,tvArrival,tvArrivalDetail;
    }

    @Override
    public int getCount() {
        return mMainListItems.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final ViewHolder holder;

        //리스트뷰 출력
        final MainListItem historyItem = mMainListItems.get(pos);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.recent_history_item_data, parent, false);
            holder.tvDeparture = convertView.findViewById(R.id.tv_departure);
            holder.tvDepartureDetail = convertView.findViewById(R.id.tv_departure_detail);
            holder.tvArrival = convertView.findViewById(R.id.tv_arrival);
            holder.tvArrivalDetail = convertView.findViewById(R.id.tv_arrival_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        // 아이템 내 각 위젯에 데이터 반영 데이터 파싱
        holder.tvDeparture.setText(historyItem.getSrcName());
        holder.tvDepartureDetail.setText(historyItem.getSrcDetail());
        holder.tvArrival.setText(historyItem.getDestName());
        holder.tvArrivalDetail.setText(historyItem.getDestDetail());




        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return mMainListItems.get(position);
    }

    public ArrayList<MainListItem> getItemList() {
        return mMainListItems;
    }
}
