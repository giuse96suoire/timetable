package com.dotvn.huynh.thoikhoabieu.outer.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dotvn.huynh.thoikhoabieu.R;

/**
 * Created by Manh Hoang Huynh on 05/09/2017.
 */

public class ProgressDialog extends DialogFragment{
    public static final String TAG = ProgressDialog.class.getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_dialog_layout, container);
        return view;
    }
}
