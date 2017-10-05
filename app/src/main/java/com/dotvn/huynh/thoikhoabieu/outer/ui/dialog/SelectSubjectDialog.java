package com.dotvn.huynh.thoikhoabieu.outer.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dotvn.huynh.thoikhoabieu.R;

/**
 * Created by Manh Hoang Huynh on 16/08/2017.
 */

public class SelectSubjectDialog {

    public static Dialog create(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.select_subject_dialog_layout, null);

        Button btnFinish = (Button) view.findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.setContentView(view);
        return dialog;
    }

}
