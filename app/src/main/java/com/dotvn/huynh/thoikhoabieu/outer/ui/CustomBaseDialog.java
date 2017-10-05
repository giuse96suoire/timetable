package com.dotvn.huynh.thoikhoabieu.outer.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * Created by Manh Hoang Huynh on 16/08/2017.
 */

public class CustomBaseDialog extends Dialog {
    public CustomBaseDialog(@NonNull Context context) {
        super(context);
    }

    public CustomBaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomBaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
