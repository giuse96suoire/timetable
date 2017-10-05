package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.timeTable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.DayConstant;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmOneDay;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.oneDay.OneDayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TimeTableFragment extends Fragment implements TimeTableFragmentContract.View, View.OnClickListener {
    public static final String TAG = TimeTableFragment.class.getSimpleName();
    public static final String KEY_ARRAY_ONEDAYS_ID = "KEY_ARRAY_ONEDAYS_ID";
    private Unbinder mUnbinder;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.vp_day_of_week)
    ViewPager mVPDayOfWeek;
    @BindView(R.id.tl_header_day_of_week)
    TabLayout mTLHeaderDayOfWeek;
    private String[] mOneDayIdArray = new String[7];

    public static TimeTableFragment newInstance(String[] oneDayIdArray) {
        TimeTableFragment fragment = new TimeTableFragment();
        Bundle args = new Bundle();
        args.putStringArray(KEY_ARRAY_ONEDAYS_ID, oneDayIdArray);
        fragment.setArguments(args);
        return fragment;
    }
    public TimeTableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getStringArray(KEY_ARRAY_ONEDAYS_ID) != null) {
            mOneDayIdArray =  getArguments().getStringArray(KEY_ARRAY_ONEDAYS_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }
    private void initView(){
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mVPDayOfWeek.setAdapter(getWeekdaysAdapter());
        mTLHeaderDayOfWeek.setupWithViewPager(mVPDayOfWeek);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void displayOneDayData(RealmOneDay realmOneDay) {
        realmOneDay.getListItemOfDay();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showInfo() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showToast(int messageId) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private WeekDaysViewpagerAdapter getWeekdaysAdapter() {
        List<Fragment> listFragment = new ArrayList<>();
        listFragment.add(OneDayFragment.newInstance(mOneDayIdArray[0]));
        listFragment.add(OneDayFragment.newInstance(mOneDayIdArray[1]));
        listFragment.add(OneDayFragment.newInstance(mOneDayIdArray[2]));
        listFragment.add(OneDayFragment.newInstance(mOneDayIdArray[3]));
        listFragment.add(OneDayFragment.newInstance(mOneDayIdArray[4]));
        listFragment.add(OneDayFragment.newInstance(mOneDayIdArray[5]));
        listFragment.add(OneDayFragment.newInstance(mOneDayIdArray[6]));
        return new WeekDaysViewpagerAdapter(getFragmentManager(), listFragment);
    }

    public static class WeekDaysViewpagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mListFragment;
        private final String TAG = WeekDaysViewpagerAdapter.class.getSimpleName();

        public WeekDaysViewpagerAdapter(FragmentManager fm, List<Fragment> listFragment) {
            super(fm);
            this.mListFragment = listFragment;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "get fragment: " + position);
            String dayOfWeek = "";
            switch (position) {
                case 0:
                    dayOfWeek = DayConstant.monday;
                    break;
                case 1:
                    dayOfWeek = DayConstant.tuesday;
                    break;
                case 2:
                    dayOfWeek = DayConstant.wednesday;
                    break;
                case 3:
                    dayOfWeek = DayConstant.thursday;
                    break;
                case 4:
                    dayOfWeek = DayConstant.friday;
                    break;
                case 5:
                    dayOfWeek = DayConstant.saturday;
                    break;
                case 6:
                    dayOfWeek = DayConstant.sunday;
                    break;
                default:
                    dayOfWeek = DayConstant.monday;
                    break;
            }
            Fragment fragment = mListFragment.get(position);
            Bundle arg = fragment.getArguments();
            arg.putString(DayConstant.KEY, dayOfWeek);
            fragment.setArguments(arg);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getItem(position).getArguments().getString(DayConstant.KEY);
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

    }
}
