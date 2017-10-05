package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.oneDay;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 05/08/2017.
 */

public interface OneDayContract {
    interface View extends BaseConstract.View{
        void loadData();
        void showData(List<ItemOfDay> listMorningData,List<ItemOfDay> listAfternoonData, List<ItemOfDay> listNightData );
        void showDialogEditItemOfDay(ItemOfDay itemOfDay);
        void showDialogRemoveItemOfDay(String id);
        void showTimePicker(ItemOfDay itemOfDay);
        void showDialogInvertItem(String item1Id, String item2Id);
        void showSnackbar(String message, String actionLabel, android.view.View.OnClickListener actionListener);
        void showSnackbar(int messageId, String actionLabel, android.view.View.OnClickListener actionListener);
        void setRemoveAreaVisible(boolean isVisible);
    }

    interface Presenter {
        void loadData(String oneDayId);
        void addData(OneDay oneDay);
        void addDataAndRemoveItemOfDayUnused(OneDay oneDay, List<ItemOfDay> listItemOfDayToRemove);
        void updateData(OneDay oneDay);
        void onStop();
    }

}
