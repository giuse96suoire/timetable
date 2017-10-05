package com.dotvn.huynh.thoikhoabieu.outer.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dotvn.huynh.thoikhoabieu.R;

/**
 * Created by Manh Hoang Huynh on 10/09/2017.
 */

public class TwoButtonDialog {

    public static Dialog create(Context context, String message, String nevigateButtonTitle, String positiveButtonTitle, final OnButtonClickListener onButtonClickListener) {
        final Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.two_button_dialog, null);
        Button btnNegative, btnPositive;
        btnNegative = (Button) view.findViewById(R.id.btn_negative_button);
        btnPositive = (Button) view.findViewById(R.id.btn_positive_button);
        if (nevigateButtonTitle != null) {
            btnNegative.setText(nevigateButtonTitle);
        }
        if (positiveButtonTitle != null) {
            btnPositive.setText(positiveButtonTitle);
        }
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_dialog_message);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onNegativeButtonClick();
                }
                dialog.dismiss();
            }
        });
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onPositiveButtonClick();
                }
                dialog.dismiss();
            }
        });
        tvMessage.setText(message);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(view);
        return dialog;
    }

    public interface OnButtonClickListener {
        void onNegativeButtonClick();
        void onPositiveButtonClick();
    }
}
