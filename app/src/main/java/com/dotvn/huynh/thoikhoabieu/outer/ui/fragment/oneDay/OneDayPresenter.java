package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.oneDay;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.ItemOfDayInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.OneDayInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.DayConstant;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.util.DayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh Hoang Huynh on 12/08/2017.
 */

public class OneDayPresenter<V extends OneDayContract.View> implements OneDayContract.Presenter {
    private OneDayContract.View mView;
    private Interactors.OneDayInteractor mOneDayInteractor;
    private Interactors.ItemOfDayInteractor mItemOfDayInteractor;
    private boolean mIsNeedToCallReload = false;

    public OneDayPresenter(OneDayContract.View view) {
        this.mView = view;
        this.mOneDayInteractor = new OneDayInteractor();
        this.mItemOfDayInteractor = new ItemOfDayInteractor();
    }


    @Override
    public void loadData(String oneDayId) {
        mView.showProgressDialog();
        mOneDayInteractor.readOneDayDataFromLocal(oneDayId, new LocalDAOCallback<OneDay>() {
            @Override
            public void onSuccess(OneDay item) {
                mView.dismissProgressDialog();
                mView.showData(
                        sortDataByTime(splitData(item, DayConstant.MORNING)),
                        sortDataByTime(splitData(item, DayConstant.AFTERNOON)),
                        sortDataByTime(splitData(item, DayConstant.NIGHT))
                );
            }

            @Override
            public void onSuccess(List<OneDay> items) {
                mView.dismissProgressDialog();
            }

            @Override
            public void onSuccess() {
                mView.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable error) {
                mView.dismissProgressDialog();
                mIsNeedToCallReload = true;
                // no data error
                if (error == null) {
//                    mView.showSnackbar("Trống", null, null);
                } else {
                    mView.showToast("Lỗi " + error.getMessage());
                }
            }
        });
    }


    @Override
    public void addData(OneDay oneDay) {
        mOneDayInteractor.writeOneDayDataToLocal(oneDay, new LocalDAOCallback<OneDay>() {
            @Override
            public void onSuccess(OneDay item) {

            }

            @Override
            public void onSuccess(List<OneDay> items) {

            }

            @Override
            public void onSuccess() {
                mView.showSnackbar(R.string.message_insert_ok, null, null);
                if (mIsNeedToCallReload) {
                    mView.loadData();
                    mIsNeedToCallReload = false;
                }
            }

            @Override
            public void onError(Throwable error) {
                mView.showToast(error.getMessage());
            }
        });
    }

    @Override
    public void addDataAndRemoveItemOfDayUnused(final OneDay oneDay,final List<ItemOfDay> listItemOfDayToRemove) {
        mItemOfDayInteractor.removeListFromLocal(listItemOfDayToRemove, new LocalDAOCallback<ItemOfDay>() {
            @Override
            public void onSuccess(ItemOfDay item) {

            }

            @Override
            public void onSuccess(List<ItemOfDay> items) {

            }

            @Override
            public void onSuccess() {
                addData(oneDay);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    @Override
    public void updateData(OneDay oneDay) {
        mOneDayInteractor.updateOneDayDataToLocal(oneDay, new LocalDAOCallback<OneDay>() {
            @Override
            public void onSuccess(OneDay item) {

            }

            @Override
            public void onSuccess(List<OneDay> items) {

            }

            @Override
            public void onSuccess() {
                mView.showSnackbar(R.string.message_edit_ok, null, null);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    @Override
    public void onStop() {
        mOneDayInteractor.closeConnection();
        mItemOfDayInteractor.closeConnection();
    }

    /**
     * This method split data to morning, afternoon, night part. If name of subject in item of day
     * is empty (null or length == 0 in case subject was removed) -> don't display it
     * => After save, db will update
     * @param oneDay
     * @param session morning, afternoon, night -> get from DayConstant
     */
    private List<ItemOfDay> splitData(OneDay oneDay, String session) {
        List<ItemOfDay> result = new ArrayList<>();
        for (ItemOfDay itemOfDay : oneDay.getListItemOfDay()) {
            if (itemOfDay.getSubject().getName() == null || itemOfDay.getSubject().getName().length() == 0) {
                continue;
            }
            String sessionOfItem = DayUtil.getSessionFromTime(
                    DayUtil.getHoursFromTimeString(itemOfDay.getStartTime())
            );
            if (sessionOfItem.equals(session)) {
                result.add(itemOfDay);
            }
        }
        return result;
    }

    private List<ItemOfDay> sortDataByTime(List<ItemOfDay> listItemOfDay) {
        for (int i = 0; i < listItemOfDay.size() - 1; i++) {
            for (int j = i + 1; j < listItemOfDay.size(); j++) {
                ItemOfDay prev = listItemOfDay.get(i);
                ItemOfDay next = listItemOfDay.get(j);
                if (isTimeOfPreThanNex(prev, next)) {
                    listItemOfDay.add(i, next);
                    listItemOfDay.remove(i + 1);
                    listItemOfDay.add(j, prev);
                    listItemOfDay.remove(j + 1);
                }
            }
        }
        return listItemOfDay;
    }

    private boolean isTimeOfPreThanNex(ItemOfDay prev, ItemOfDay next) {
        int prevHours = DayUtil.getHoursFromTimeString(prev.getStartTime());
        int prevMin = DayUtil.getMinFromTimeString(prev.getStartTime());
        int nextHours = DayUtil.getHoursFromTimeString(next.getStartTime());
        int nextMin = DayUtil.getMinFromTimeString(next.getStartTime());
        if (prevHours > nextHours) {
            return true;
        } else if (prevHours == nextHours && prevMin > nextMin) {
            return true;
        }
        return false;
    }
}
