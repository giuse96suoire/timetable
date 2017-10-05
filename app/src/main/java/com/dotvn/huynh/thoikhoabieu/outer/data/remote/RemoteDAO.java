package com.dotvn.huynh.thoikhoabieu.outer.data.remote;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 */

import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;

/**
 * All method run as async, so need callback to get data
 *
 * @param <T> class to excute
 */
public abstract class RemoteDAO<T> {
    public abstract void read(final String id, final TwoCallback<T> callback);

    public abstract void readOnce(final String id, final TwoCallback<T> callback);

    public abstract void write(final String id, final T value, final ThreeCallback<Void> callback);

    public abstract void updateProperty(final String reference, final Object value, final ThreeCallback<Void> callback);

    public abstract void writeWithRandomId(final T value, final ThreeCallback<String> callback);

    public abstract void writeIfNotExist(final String id, final T value, final ThreeCallback<Void> callback);

    public abstract void remove(final String id, final ThreeCallback<T> callback);

}
