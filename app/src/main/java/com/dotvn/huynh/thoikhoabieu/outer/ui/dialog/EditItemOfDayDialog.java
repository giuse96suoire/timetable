package com.dotvn.huynh.thoikhoabieu.outer.ui.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Manh Hoang Huynh on 16/09/2017.
 */

public class EditItemOfDayDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    public static final String TAG = EditItemOfDayDialog.class.getSimpleName();
    private Unbinder mUnbinder;
    @BindView(R.id.tv_subject_name)
    TextView mTvSubjectName;
    @BindView(R.id.tv_subject_start_time)
    TextView mTvSubjectStartTime;
    @BindView(R.id.tv_subject_end_time)
    TextView mTvSubjectEndTime;
    @BindView(R.id.et_subject_event)
    EditText mEtSubjectEvent;
    @BindView(R.id.btn_dismiss)
    TextView mBtnClose;
    @BindView(R.id.btn_finish)
    TextView mBtnSave;
    private ItemOfDay mItemOfDay;
    private TimePickerDialog mTimePickerDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setItemOfDay(ItemOfDay itemOfDay) {
        mItemOfDay = itemOfDay;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_item_of_day_dialog, container);
        mUnbinder = ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        clearData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clearData();
        if (mItemOfDay != null) {
            mTvSubjectName.setText(mItemOfDay.getSubject().getName());
            mTvSubjectStartTime.setText(mItemOfDay.getStartTime());
            mTvSubjectEndTime.setText(mItemOfDay.getEndTime());
            mEtSubjectEvent.setText(mItemOfDay.getEvent());
        }
    }

    private void clearData() {
        mTvSubjectName.setText("");
        mTvSubjectStartTime.setText("");
        mTvSubjectEndTime.setText("");
        mEtSubjectEvent.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTvSubjectStartTime.setOnClickListener(this);
        mTvSubjectEndTime.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_subject_start_time:
                showTimePickerDialog();
                break;
            case R.id.tv_subject_end_time:
                showTimePickerDialog();
                break;
            case R.id.btn_dismiss:
                dismiss();
                break;
            case R.id.btn_finish:
                //save data
                mItemOfDay.setEvent(mEtSubjectEvent.getText().toString());
                mCallback.onFinish(mItemOfDay);
                dismiss();
                break;
        }
    }

    private void showTimePickerDialog() {
        if (mTimePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            mTimePickerDialog = TimePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );

            mTimePickerDialog.setTabIndicators("Bắt đầu", "Kết thúc");
            mTimePickerDialog.setOnTimeSetListener(this);
        }
        int startHour, startMin, endHour, endMin;
        try {
            startHour = Integer.parseInt(mItemOfDay.getStartTime().substring(0, 2));
            startMin = Integer.parseInt(mItemOfDay.getStartTime().substring(3, 5));
        } catch (Exception error) {
            startHour = 7;
            startMin = 0;
        }
        try {
            endHour = Integer.parseInt(mItemOfDay.getEndTime().substring(0, 2));
            endMin = Integer.parseInt(mItemOfDay.getEndTime().substring(3, 5));
        } catch (Exception error) {
            endHour = startHour;
            endMin = startMin + 45;
            if (endMin > 60) {
                endMin -= 60;
                endHour++;
            }

        }
        mTimePickerDialog.setStartTime(startHour, startMin);
        mTimePickerDialog.setEndTime(endHour, endMin);
        mTimePickerDialog.show(getActivity().getFragmentManager(), TAG);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String startHoursString;
        String startMinString;
        String endHoursString;
        String endMinString;
        if (hourOfDay >= 0 && hourOfDay <= 9) {
            startHoursString = "0" + hourOfDay;
        } else {
            startHoursString = hourOfDay + "";
        }
        if (minute >= 0 && minute <= 9) {
            startMinString = "0" + minute;
        } else {
            startMinString = minute + "";
        }

        if (hourOfDayEnd >= 0 && hourOfDayEnd <= 9) {
            endHoursString = "0" + hourOfDayEnd;
        } else {
            endHoursString = hourOfDay + "";
        }
        if (minuteEnd >= 0 && minuteEnd <= 9) {
            endMinString = "0" + minuteEnd;
        } else {
            endMinString = minuteEnd + "";
        }
        String startTime = startHoursString + ":" + startMinString;
        String endTime = endHoursString + ":" + endMinString;
        mTvSubjectStartTime.setText(startTime);
        mItemOfDay.setStartTime(startTime);
        mTvSubjectEndTime.setText(endTime);
        mItemOfDay.setEndTime(endTime);
    }

    private OnFinishListener mCallback;

    public void setOnFinishListener(OnFinishListener callback) {
        mCallback = callback;
    }

    public interface OnFinishListener {
        void onFinish(ItemOfDay itemOfDay);
    }
}
