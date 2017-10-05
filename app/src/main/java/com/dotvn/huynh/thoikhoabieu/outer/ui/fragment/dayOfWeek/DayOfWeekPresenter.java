package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.dayOfWeek;

/**
 * Created by Manh Hoang Huynh on 12/08/2017.
 */

public class DayOfWeekPresenter<V extends DayOfWeekContract.View> implements DayOfWeekContract.Presenter {
    private DayOfWeekContract.View mView;
    public DayOfWeekPresenter(DayOfWeekContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadData() {

    }
}
