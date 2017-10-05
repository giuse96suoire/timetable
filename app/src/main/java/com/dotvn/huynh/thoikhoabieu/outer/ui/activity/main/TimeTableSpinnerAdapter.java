package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;

import java.util.List;

/**
 * Created by huynh.mh on 9/29/2017.
 */

public class TimeTableSpinnerAdapter extends BaseAdapter {
    private List<TimeTable> mListTimeTable;
    private Context mContext;

    public TimeTableSpinnerAdapter(@NonNull Context context, List<TimeTable> timeTables) {
        this.mContext = context;
        this.mListTimeTable = timeTables;
    }

    @Override
    public int getCount() {
        Log.d("xxx", mListTimeTable.size()+" --------------------------------");
        return mListTimeTable.size();
    }

    @Override
    public TimeTable getItem(int position) {
        return mListTimeTable.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.timetable_item, parent, false);
        TextView tvTimeTableName = (TextView)view.findViewById(R.id.tv_timetable_name);
        tvTimeTableName.setText(mListTimeTable.get(position).getName());
        return view;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.timetable_item, parent, false);
        if (position % 2 == 0) {
            view.setBackground(mContext.getResources().getDrawable(R.drawable.positive_backgroud_selector0));
        } else {
            view.setBackground(mContext.getResources().getDrawable(R.drawable.positive_backgroud_selector1));
        }
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.ib_drop_down);
        imageButton.setVisibility(View.GONE);
        TextView tvTimeTableName = (TextView)view.findViewById(R.id.tv_timetable_name);
        tvTimeTableName.setText(mListTimeTable.get(position).getName());
        return view;
    }
}
