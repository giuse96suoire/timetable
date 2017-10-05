package com.dotvn.huynh.thoikhoabieu.inner.data;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Teacher;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by Manh Hoang Huynh on 22/08/2017.
 */

public interface Interactors {
    interface TimeTableInteractor {
        void readAllFromLocal(LocalDAOCallback<TimeTable> callback);

        void readFromLocal(String timeTableId, LocalDAOCallback<TimeTable> callback);

        void readAllFromRemote(TwoCallback<TimeTable> callback);

        void writeToLocal(final TimeTable timeTable, final LocalDAOCallback<TimeTable> callback);

        void writeToRemote(final TimeTable timeTable, final ThreeCallback<String> callback);

        void updateToRemote(final TimeTable timeTable, final ThreeCallback<Void> callback);

        void removeFromLocal(TimeTable timeTable);

        void removeFromRemote(final String id, final ThreeCallback<TimeTable> callback);

        void removeAllFromLocal();

        void removeAllFromRemote(final ThreeCallback<TimeTable> callback);

        void closeConnection();
    }

    interface UserInteractor {
        void readFromLocal(LocalDAOCallback<User> callback);

        void readFromRemote(String id, TwoCallback<User> callback);

        void searchFromRemote(String id, TwoCallback<User> callback);
        void searchToAddFromRemote(String id, TwoCallback<User> callback);

        void findFromLocal(LocalDAOCallback<User> callback);

        void addFriendToRemote(String userId, Friend friend, final ThreeCallback<Void> callback);

        void removeFriendFromRemote(String userId, final ThreeCallback<Void> callback);

        void addFriendToLocal(final Friend friend, final LocalDAOCallback<Friend> callback);

        void addTimeTableKeyToOwnListToRemote(String userId, Map<String, String> timetableMap, final ThreeCallback<Void> callback);

        void updateDisplayName(final String name, final String id, final ThreeCallback<Void> callback);

        void writeToLocal(final User user, final LocalDAOCallback<User> callback);

        void writeToRemote(final String id, final User user, final ThreeCallback<Void> callback);

        void writeToRemoteIfNotExist(final String id, final User user, final ThreeCallback<Void> callback);

        void closeConnection();
    }

    interface SubjectInteractor {
        void readAllFromLocal(LocalDAOCallback<Subject> callback);

        void writeToLocal(Subject subject, LocalDAOCallback<Subject> callback);

        void removeFromLocal(Subject subject);

        void removeAllFromLocal();

        void closeConnection();
    }

    interface TeacherInteractor {
        void getAllTeacherFromLocal(LocalDAOCallback<Teacher> callback);
    }

    interface OneDayInteractor {
        void readOneDayDataFromLocal(String dayOfWeek, LocalDAOCallback<OneDay> callback);

        void writeOneDayDataToLocal(OneDay oneDay, LocalDAOCallback<OneDay> callback);

        void updateOneDayDataToLocal(OneDay oneDay, LocalDAOCallback<OneDay> callback);

        void closeConnection();
    }

    interface ItemOfDayInteractor {
        void readAllFromLocal(LocalDAOCallback<ItemOfDay> callback);
        void readItemOfDayBySubject(Subject subject, LocalDAOCallback<ItemOfDay> callback);
        void writeToLocal(ItemOfDay itemOfDay, LocalDAOCallback<ItemOfDay> callback);

        void updateToLocal(ItemOfDay itemOfDay, LocalDAOCallback<ItemOfDay> callback);

        void removeFromLocal(ItemOfDay itemOfDay);
        void removeListFromLocal(List<ItemOfDay> listItemOfDay, LocalDAOCallback<ItemOfDay> callback);

        void removeAllFromLocal();

        void closeConnection();
    }
}
