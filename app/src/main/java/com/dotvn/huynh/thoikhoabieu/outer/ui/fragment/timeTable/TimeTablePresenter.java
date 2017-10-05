package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.timeTable;

import android.content.Context;

import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.RealmDbHelper;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public class TimeTablePresenter implements TimeTableFragmentContract.Presenter {
    private TimeTableFragmentContract.View mView;
    private Context mContext;
    private RealmDbHelper mDbHelper =  new RealmDbHelper();
    public TimeTablePresenter(Context context, TimeTableFragmentContract.View view) {

    }
    @Override
    public void loadData() {
    }
}
