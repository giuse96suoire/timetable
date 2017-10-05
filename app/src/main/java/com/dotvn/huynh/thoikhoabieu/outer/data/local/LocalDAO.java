package com.dotvn.huynh.thoikhoabieu.outer.data.local;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 */

import java.util.List;

/**
 * All method run as async, so need callback to get data
 *
 * @param <T> class to excute
 */
public abstract class LocalDAO<T> {
    public abstract void get(String id, final LocalDAOCallback<T> callback);

    public abstract void getByProperty(String compareWith, String valueToCompare, final LocalDAOCallback<T> callback);

    public abstract void getAll(LocalDAOCallback<T> callback);

    public abstract void insert(T item, final LocalDAOCallback<T> callback);

    public abstract void insertOrUpdate(T item, final LocalDAOCallback<T> callback);

    public abstract void update(T item, final LocalDAOCallback<T> callback);

    public abstract void delete(String id);

    public abstract void deleteList(List<T> listItem, final LocalDAOCallback<T> callback);

    public abstract void delete(T item);

    public abstract void deleteAll();

    // close connection
    public abstract void close();
}
