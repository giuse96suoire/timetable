package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.oneDay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.data.DayConstant;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.shared.SharedDB;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.subjects.AddSubjectActivity;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.EditItemOfDayDialog;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.TwoButtonDialog;
import com.dotvn.huynh.thoikhoabieu.outer.util.DayUtil;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class OneDayFragment extends Fragment implements OneDayContract.View, View.OnClickListener, EditItemOfDayDialog.OnFinishListener {
    public static final String TAG = OneDayFragment.class.getSimpleName();
    public static final String KEY_ONEDAY_ID = "KEY_ONEDAY_ID";
    private Unbinder mUnbinder;
    private String mDayOfWeek;
    private OneDayContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private Fragment mEditItemOfDayDialog;

    @BindView(R.id.tv_morning_add_data)
    TextView mTvMorningAddData;
    @BindView(R.id.tv_morning_add_data_top)
    TextView mTvMorningAddDataTop;

    @BindView(R.id.tv_afternoon_add_data)
    TextView mTvAfternoonAddData;
    @BindView(R.id.tv_afternoon_add_data_top)
    TextView mTvAfternoonAddDataTop;

    @BindView(R.id.tv_night_add_data)
    TextView mTvNightAddData;
    @BindView(R.id.tv_night_add_data_top)
    TextView mTvNightAddDataTop;

    @BindView(R.id.rv_list_subject_morning)
    RecyclerView mRvListMorningSubject;
    @BindView(R.id.rv_list_subject_afternoon)
    RecyclerView mRvListAfternoonSubject;
    @BindView(R.id.rv_list_subject_night)
    RecyclerView mRvListNightSubject;
    @BindView(R.id.fl_remove_area)
    FrameLayout mFlRemoveArea;
    @BindView(R.id.iv_remove_area)
    ImageView mIvRemoveArea;
    ProgressDialog mProgressDialog;

    private ItemOfDayAdapter mMorningAdapter, mAfternoonAdapter, mNightAdapter;
    private List<ItemOfDay> mListMorningData, mListAfternoonData, mListNightData;
    private String mCurrentInteractSession;
    private final int KEY_REQUEST_SUBJECT_ACTIVITY = 0;
    private String mOneDayId;
    public static OneDayFragment newInstance(String oneDayId) {
        OneDayFragment fragment = new OneDayFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ONEDAY_ID, oneDayId);
        fragment.setArguments(args);
        return fragment;
    }
    public OneDayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null){
            if(getArguments().getString(KEY_ONEDAY_ID) != null) {
                mOneDayId = getArguments().getString(KEY_ONEDAY_ID);
            }
            if(getArguments().getString(DayConstant.KEY) != null) {
                mDayOfWeek = getArguments().getString(DayConstant.KEY);
            }
        }
    }

    private void initView() {
        // init morning part
        // init morning listview
        mListMorningData = new ArrayList<>();
        mMorningAdapter = new ItemOfDayAdapter(getContext(), this, mListMorningData, false);
        mRvListMorningSubject.setNestedScrollingEnabled(false);
        mRvListMorningSubject.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvListMorningSubject.setAdapter(mMorningAdapter);
        // init morning view
        mTvMorningAddData.setOnClickListener(this);
        mTvMorningAddDataTop.setOnClickListener(this);


        // init afternoon part
        // init afternoon listview
        mListAfternoonData = new ArrayList<>();
        mAfternoonAdapter = new ItemOfDayAdapter(getContext(), this, mListAfternoonData, false);
        mRvListAfternoonSubject.setNestedScrollingEnabled(false);
        mRvListAfternoonSubject.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvListAfternoonSubject.setAdapter(mAfternoonAdapter);
        // init afternoon view
        mTvAfternoonAddData.setOnClickListener(this);
        mTvAfternoonAddDataTop.setOnClickListener(this);

        // init night part
        // init night listview
        mListNightData = new ArrayList<>();
        mNightAdapter = new ItemOfDayAdapter(getContext(), this, mListNightData, false);
        mRvListNightSubject.setNestedScrollingEnabled(false);
        mRvListNightSubject.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvListNightSubject.setAdapter(mNightAdapter);
        // init night view
        mTvNightAddData.setOnClickListener(this);
        mTvNightAddDataTop.setOnClickListener(this);

        // init remove area (drag to remove)
        mIvRemoveArea.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("DragDrop Example", "ACTION_DRAG_ENTERED");
                        ((ImageView) v).setColorFilter(R.color.colorPrimary);
                        v.invalidate();
                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d("DragDrop Example", "ACTION_DRAG_LOCATION");
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("DragDrop Example", "ACTION_DRAG_EXITED");
                        ((ImageView) v).clearColorFilter();

                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate();

                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.d("DragDrop Example", "ACTION_DROP");
                        ClipData.Item item = event.getClipData().getItemAt(0);
                        String data = item.getText().toString();
                        String id = data.substring(data.indexOf("@") + 1, data.length());
                        showDialogRemoveItemOfDay(id);
                        // Invalidates the view to force a redraw
                        v.invalidate();
                        // Returns true. DragEvent.getResult() will return true.
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        ((ImageView) v).clearColorFilter();
                        Log.d("DragDrop Example", "ACTION_DRAG_ENDED");
                        v.invalidate();
                        // returns true; the value is ignored.
                        return true;
                    // An unknown action type was received.
                    default:
                        Log.d("DragDrop Example", "Unknown action type received by OnDragListener.");
                        break;
                }
                return false;
            }
        });
    }

    public String getDayOfWeek() {
        return mDayOfWeek;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG + "-" + mDayOfWeek, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day_of_week, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG + "-" + mDayOfWeek, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        initView();
        mPresenter = new OneDayPresenter<>(this);
        loadData();
    }

    private void showTargetTap(View view) {
        if (!getUserVisibleHint()) {
            return;
        }
        TapTarget target = TapTarget.forView(view, "Thêm môn học vào thứ 2", "Nhấn [Thêm/ sửa] để thêm môn học vào thứ 2");
        TapTargetView.showFor(getActivity(),                 // `this` is an Activity
                target,                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        showToast("ok");
                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG + "-" + mDayOfWeek, "onDetach");
        mListener = null;
        mPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void loadData() {
        mPresenter.loadData(mOneDayId);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int messageId) {

    }
    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Đợi chút nhé");
            mProgressDialog.setIndeterminateDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void showData(List<ItemOfDay> listMorningData, List<ItemOfDay> listAfternoonData, List<ItemOfDay> listNightData) {
        mTvMorningAddData.setVisibility(
                listMorningData.size() > 0 ? View.GONE : View.VISIBLE
        );
        mTvAfternoonAddData.setVisibility(
                listAfternoonData.size() > 0 ? View.GONE : View.VISIBLE
        );
        mTvNightAddData.setVisibility(
                listNightData.size() > 0 ? View.GONE : View.VISIBLE
        );

        mListMorningData.clear();
        mListMorningData.addAll(listMorningData);
        mMorningAdapter.notifyDataSetChanged();

        mListAfternoonData.clear();
        mListAfternoonData.addAll(listAfternoonData);
        mAfternoonAdapter.notifyDataSetChanged();

        mListNightData.clear();
        mListNightData.addAll(listNightData);
        mNightAdapter.notifyDataSetChanged();
    }


    @Override
    public void showDialogEditItemOfDay(ItemOfDay itemOfDay) {
        if (mEditItemOfDayDialog == null) {
            mEditItemOfDayDialog = new EditItemOfDayDialog();
        }
        if (mEditItemOfDayDialog instanceof EditItemOfDayDialog) {
            ((EditItemOfDayDialog) mEditItemOfDayDialog).setItemOfDay(itemOfDay);
            ((EditItemOfDayDialog) mEditItemOfDayDialog).setOnFinishListener(this);
            ((EditItemOfDayDialog) mEditItemOfDayDialog).show(getFragmentManager(), EditItemOfDayDialog.TAG);
        }
    }

    @Override
    public void showDialogRemoveItemOfDay(String id) {
        final ItemOfDay itemOfDay = getItemFromListById(id);
        if (itemOfDay == null) {
            return;
        }
        TwoButtonDialog.create(
                getContext(),
                "Bạn có muốn xóa môn " + itemOfDay.getSubject().getName() + " không?",
                "Không",
                "Có",
                new TwoButtonDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeButtonClick() {

                    }

                    @Override
                    public void onPositiveButtonClick() {
                        mListMorningData.addAll(mListAfternoonData);
                        mListMorningData.addAll(mListNightData);
                        mListMorningData.remove(itemOfDay);
                        OneDay oneDay = new OneDay();
                        oneDay.setId(mOneDayId);
                        oneDay.setListItemOfDay(mListMorningData);
                        oneDay.setDayOfWeek(getDayOfWeek());
                        mPresenter.updateData(oneDay);
                    }
                }
        ).show();
    }

    private TimePickerDialog mTimePickerDialog;

    @Override
    public void showTimePicker(final ItemOfDay itemOfDay) {
        if (mTimePickerDialog == null) {
            mTimePickerDialog = new TimePickerDialog();
            mTimePickerDialog.setTabIndicators(getString(R.string.title_start), getString(R.string.title_end));
        }
        mTimePickerDialog.setTitle(itemOfDay.getSubject().getName());
        mTimePickerDialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
                if (hourOfDayEnd < hourOfDay || (hourOfDay == hourOfDayEnd && minuteEnd < minute)) {
                    showSnackbar(R.string.message_edit_error_cause_time, null, null);
                    return;
                }
                itemOfDay.setStartTime(DayUtil.getTimeStringFromHoursAndMin(hourOfDay, minute));
                itemOfDay.setEndTime(DayUtil.getTimeStringFromHoursAndMin(hourOfDayEnd, minuteEnd));
                onFinish(itemOfDay);
            }
        });

        int startHour = 12;
        int startMin = 12;
        int endHour;
        int endMin;
        if (itemOfDay.getStartTime() != null && itemOfDay.getStartTime().length() > 0) {
            startHour = DayUtil.getHoursFromTimeString(itemOfDay.getStartTime());
            startMin = DayUtil.getMinFromTimeString(itemOfDay.getStartTime());
        }
        if (itemOfDay.getEndTime() != null && itemOfDay.getEndTime().length() > 0) {
            endHour = DayUtil.getHoursFromTimeString(itemOfDay.getEndTime());
            endMin = DayUtil.getMinFromTimeString(itemOfDay.getEndTime());
        } else {
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
    public void showDialogInvertItem(String item1Id, String item2Id) {
        final ItemOfDay item1 = getItemFromListById(item1Id);
        final ItemOfDay item2 = getItemFromListById(item2Id);
        if (item1 == null || item2 == null) {
            return;
        }
        TwoButtonDialog.create(
                getContext(),
                "Bạn có muốn đổi vị trí giữa " + item1.getSubject().getName() + " và " + item2.getSubject().getName() + " ? Thời gian sẽ được giữ nguyên, chỉ hoán đổi môn học.",
                "Không",
                "Hoán đổi",
                new TwoButtonDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeButtonClick() {

                    }

                    @Override
                    public void onPositiveButtonClick() {
                        invertItem(item1, item2);
                    }
                }
        ).show();
    }

    private void invertItem(ItemOfDay itemOfDay1, ItemOfDay itemOfDay2) {
        ItemOfDay temp = new ItemOfDay(
                itemOfDay1.getId(),
                itemOfDay1.getStartTime(),
                itemOfDay1.getEndTime(),
                itemOfDay1.getSubject(),
                itemOfDay1.getOther(),
                itemOfDay1.getEvent()
        );
        itemOfDay1.setSubject(itemOfDay2.getSubject());
        itemOfDay1.setEvent(itemOfDay2.getEvent());
        itemOfDay1.setOther(itemOfDay2.getOther());

        itemOfDay2.setSubject(temp.getSubject());
        itemOfDay2.setEvent(temp.getEvent());
        itemOfDay2.setOther(temp.getOther());
        mListMorningData.addAll(mListAfternoonData);
        mListMorningData.addAll(mListNightData);
        OneDay oneDay = new OneDay();
        oneDay.setId(mOneDayId);
        oneDay.setListItemOfDay(mListMorningData);
        oneDay.setDayOfWeek(getDayOfWeek());
        mPresenter.updateData(oneDay);
//        mMorningAdapter.notifyDataSetChanged();
//        mAfternoonAdapter.notifyDataSetChanged();
//        mNightAdapter.notifyDataSetChanged();
    }

    private ItemOfDay getItemFromListById(String id) {
        List<ItemOfDay> list = new ArrayList<>();
        list.addAll(mListMorningData);
        list.addAll(mListAfternoonData);
        list.addAll(mListNightData);
        for (ItemOfDay itemOfDay : list) {
            if (itemOfDay != null && itemOfDay.getId() != null && itemOfDay.getId().equals(id)) {
                return itemOfDay;
            }
        }
        return null;
    }

    @Override
    public void showSnackbar(String message, String actionLabel, View.OnClickListener actionListener) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .setAction(actionLabel, actionListener)
                .show();
    }

    @Override
    public void showSnackbar(int messageId, String actionLabel, View.OnClickListener actionListener) {
        Snackbar.make(getView(), getString(messageId), Snackbar.LENGTH_LONG)
                .setAction(actionLabel, actionListener)
                .show();
    }

    @Override
    public void setRemoveAreaVisible(boolean isVisible) {
        if (isVisible) {
            mFlRemoveArea.setVisibility(View.VISIBLE);
            final Animation anim = AnimationUtils.loadAnimation(getContext(),
                    R.anim.scale_repeat_in_out);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Animation anim = AnimationUtils.loadAnimation(getContext(),
                            R.anim.scale_repeat_in_out);
                    anim.setAnimationListener(this);
                    mIvRemoveArea.startAnimation(anim);
                    Log.d(TAG, "onAnimationEnd");

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.d(TAG, "onAnimationRepeat");
                }
            });
            mIvRemoveArea.startAnimation(anim);

        } else {
            mIvRemoveArea.startAnimation(AnimationUtils.loadAnimation(getContext(),
                    R.anim.push_down_out));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFlRemoveArea.setVisibility(View.GONE);
                }
            }, 400);
        }

    }


    private void insertSubjectToThisDay(List<Subject> listSubject) {
        List<ItemOfDay> itemOfDays = new ArrayList<>();
        int time;
        switch (mCurrentInteractSession) {
            case DayConstant.MORNING:
                //// TODO: 19/09/2017 get time from user config
                time = DayConstant.MORNING_START_TIME * 60;
                for (Subject subject : listSubject) {
                    ItemOfDay itemOfDayIfExist = getItemOfDayBySubject(subject, mListMorningData);
                    if (itemOfDayIfExist != null) {
                        itemOfDays.add(itemOfDayIfExist);
                        continue;
                    }
                    ItemOfDay itemOfDay = new ItemOfDay();
                    itemOfDay.setSubject(subject);
                    itemOfDay.setId(UUID.randomUUID().toString());
                    itemOfDay.setStartTime(DayUtil.getTimeStringFromNumberOfMin(time));
                    itemOfDay.setEndTime(DayUtil.getTimeStringFromNumberOfMin(time + 45));
                    itemOfDays.add(itemOfDay);
                    time += 50;
                }
                itemOfDays.addAll(mListAfternoonData);
                itemOfDays.addAll(mListNightData);
                break;
            case DayConstant.AFTERNOON:
                time = DayConstant.AFTERNOON_START_TIME * 60;
                for (Subject subject : listSubject) {
                    ItemOfDay itemOfDayIfExist = getItemOfDayBySubject(subject, mListAfternoonData);
                    if (itemOfDayIfExist != null) {
                        itemOfDays.add(itemOfDayIfExist);
                        continue;
                    }
                    ItemOfDay itemOfDay = new ItemOfDay();
                    itemOfDay.setSubject(subject);
                    itemOfDay.setId(UUID.randomUUID().toString());
                    itemOfDay.setStartTime(DayUtil.getTimeStringFromNumberOfMin(time));
                    itemOfDay.setEndTime(DayUtil.getTimeStringFromNumberOfMin(time + 45));
                    itemOfDays.add(itemOfDay);
                    time += 50;
                }
                itemOfDays.addAll(mListMorningData);
                itemOfDays.addAll(mListNightData);
                break;
            case DayConstant.NIGHT:
                time = DayConstant.NIGHT_START_TIME * 60;
                for (Subject subject : listSubject) {
                    ItemOfDay itemOfDayIfExist = getItemOfDayBySubject(subject, mListNightData);
                    if (itemOfDayIfExist != null) {
                        itemOfDays.add(itemOfDayIfExist);
                        continue;
                    }
                    ItemOfDay itemOfDay = new ItemOfDay();
                    itemOfDay.setId(UUID.randomUUID().toString());
                    itemOfDay.setSubject(subject);
                    itemOfDay.setStartTime(DayUtil.getTimeStringFromNumberOfMin(time));
                    itemOfDay.setEndTime(DayUtil.getTimeStringFromNumberOfMin(time + 45));
                    itemOfDays.add(itemOfDay);
                    time += 50;
                }
                itemOfDays.addAll(mListMorningData);
                itemOfDays.addAll(mListAfternoonData);
                break;
        }
        List<ItemOfDay> removeList = null;
        switch (mCurrentInteractSession) {
            case DayConstant.MORNING:
                removeList  = getListItemOfDayUnused(mListMorningData, itemOfDays);
                break;
            case DayConstant.AFTERNOON:
                removeList  = getListItemOfDayUnused(mListAfternoonData, itemOfDays);
                break;
            case DayConstant.NIGHT:
                removeList  = getListItemOfDayUnused(mListNightData, itemOfDays);
                break;
        }

        OneDay oneDay = new OneDay();
        oneDay.setId(mOneDayId);
        oneDay.setListItemOfDay(itemOfDays);
        oneDay.setDayOfWeek(getDayOfWeek());
        mPresenter.addDataAndRemoveItemOfDayUnused(oneDay,removeList);
    }

    private List<ItemOfDay> getListItemOfDayUnused(List<ItemOfDay> oldList, List<ItemOfDay> newList) {
        List<ItemOfDay> result = new ArrayList<>();
        for (ItemOfDay item : oldList) {
            if (!newList.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }
    private ItemOfDay getItemOfDayBySubject(Subject subject, List<ItemOfDay> sessionData) {
        for (ItemOfDay itemOfDay : sessionData) {
            if (itemOfDay.getSubject().getName().equals(subject.getName())) {
                return itemOfDay;
            }
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_morning_add_data:
            case R.id.tv_morning_add_data_top:
                showAddSubjectActivity(DayConstant.MORNING);
                break;
            case R.id.tv_afternoon_add_data:
            case R.id.tv_afternoon_add_data_top:
                showAddSubjectActivity(DayConstant.AFTERNOON);
                break;
            case R.id.tv_night_add_data:
            case R.id.tv_night_add_data_top:
                showAddSubjectActivity(DayConstant.NIGHT);
                break;
        }
    }

    private void showAddSubjectActivity(String session) {
        mCurrentInteractSession = session;
        Intent intent = new Intent(getContext(), AddSubjectActivity.class);
        intent.putExtra(AddSubjectActivity.DAY_OF_WEEK_STRING_EXTRA_KEY, getDayOfWeek() + " - " + session);
        ArrayList<String> subjectIdArr = new ArrayList<>();
        switch (mCurrentInteractSession) {
            case DayConstant.MORNING:
                for (ItemOfDay itemOfDay : mListMorningData) {
                    subjectIdArr.add(itemOfDay.getSubject().getId());
                }
                break;
            case DayConstant.AFTERNOON:
                for (ItemOfDay itemOfDay : mListAfternoonData) {
                    subjectIdArr.add(itemOfDay.getSubject().getId());
                }
                break;
            case DayConstant.NIGHT:
                for (ItemOfDay itemOfDay : mListNightData) {
                    subjectIdArr.add(itemOfDay.getSubject().getId());
                }
                break;
        }
        intent.putExtra(AddSubjectActivity.SUBJECTS_ID_ARRAY_EXTRA_KEY, subjectIdArr);
        startActivityForResult(intent, KEY_REQUEST_SUBJECT_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KEY_REQUEST_SUBJECT_ACTIVITY && resultCode == Activity.RESULT_OK && data != null) {
            List<Subject> listSubject = data.getParcelableArrayListExtra(AddSubjectActivity.DATA_RESULT_KEY);
            insertSubjectToThisDay(listSubject);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG + "-" + mDayOfWeek, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG + "-" + mDayOfWeek, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG + "-" + mDayOfWeek, "onResume");
        Log.d(TAG + "-" + mDayOfWeek, "getUserVisibleHint(): " + getUserVisibleHint());
        if (SharedDB.getInstance(getContext()).isFirstTimeUse()) {
//            showTargetTap(mTvMorningAddDataTop);
            SharedDB.getInstance(getContext()).setFirstTimeUse(false);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG + "-" + mDayOfWeek, "onDestroyView");
    }

    /**
     * On dialog [edit item of day] close
     *
     * @param itemOfDay item after edit
     *                  todo: it seem be very bad way, try to change this behaviours
     */
    @Override
    public void onFinish(ItemOfDay itemOfDay) {
        List<ItemOfDay> combindList = new ArrayList<>();
        combindList.addAll(mListAfternoonData);
        combindList.addAll(mListMorningData);
        combindList.addAll(mListNightData);
        for (ItemOfDay item : combindList) {
            if (item.getId() != null && item.getId().equals(itemOfDay.getId())) {
                combindList.remove(item);
                combindList.add(itemOfDay);
                break;
            }
        }
        OneDay oneDay = new OneDay();
        oneDay.setId(mOneDayId);
        oneDay.setDayOfWeek(mDayOfWeek);
        oneDay.setListItemOfDay(combindList);
        mPresenter.updateData(oneDay);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
