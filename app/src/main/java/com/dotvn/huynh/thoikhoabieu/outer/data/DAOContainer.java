package com.dotvn.huynh.thoikhoabieu.outer.data;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Teacher;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.FriendRealmDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.ItemOfDayRealmDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.OneDayRealmDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.SubjectRealmDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.TeacherRealmDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.TimeTableRealmDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.RemoteDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.DAO.TimeTableFirebaseDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.DAO.UserFirebaseDAO;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * This class provide DAO module for using, by this way we can replace module easy
 */

public class DAOContainer {

    public static RemoteDAO<TimeTable> getRemoteTimeTableDAO() {
        return new TimeTableFirebaseDAO();
    }
    public static LocalDAO<TimeTable> getLocalTimeTableDAO() {
        return new TimeTableRealmDAO();
    }
    public static RemoteDAO<User> getRemoteUserDAO() {
        return new UserFirebaseDAO();
    }

    public static LocalDAO<OneDay> getLocalOneDayDAO() {
        return new OneDayRealmDAO();
    }

    public static LocalDAO<ItemOfDay> getLocalItemOfDayDAO() {
        return new ItemOfDayRealmDAO();
    }

    public static LocalDAO<Subject> getLocalSubjectDAO() {
        return new SubjectRealmDAO();
    }

    public static LocalDAO<Teacher> getLocalTeacerDAO() {
        return new TeacherRealmDAO();
    }
    public static LocalDAO<Friend> getLocalFriendDAO() {
        return new FriendRealmDAO();
    }
}
