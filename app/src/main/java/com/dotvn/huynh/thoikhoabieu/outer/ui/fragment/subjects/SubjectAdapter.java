package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh Hoang Huynh on 21/08/2017.
 */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    // list data
    private List<Subject> mListSubject;
    // mIsSelectableMode: true if in SubjectActivity (can check to select subject)
    // mIsViewTimeMode: true if in OneDayFragment (view start time, end time - no checkable)
    private boolean mIsSelectableMode, mIsViewTimeMode;

    // mListSelectedSubjects: list selected subject in SubjectActivity
    private List<Subject> mListSelectedSubjects;

    // mLastestLoadedPosission: the last index of item that was loaded with animation
    private int mLastestLoadedPosission = -1;
    private ArrayList<String> mListSelectedSubjectId;
    private Context mContext;
    private SubjectsFragmentContract.View mView;
    private SubjectFragment.OnSubjectCheckedChangeListener mOnSubjectCheckedChange;

    public SubjectAdapter(Context context, SubjectsFragmentContract.View view, List<Subject> listSubject, boolean isSelectableMode, boolean isViewTimeMode, ArrayList<String> listSelectedSubjectId) {
        this.mListSubject = listSubject;
        this.mIsViewTimeMode = isViewTimeMode;
        this.mIsSelectableMode = isSelectableMode;
        if (isSelectableMode) {
            mListSelectedSubjects = new ArrayList<>();
        }
        this.mContext = context;
        this.mView = view;
        this.mListSelectedSubjectId = listSelectedSubjectId;
    }

    public List<Subject> getListSelectedSubjects() {
        return mListSelectedSubjects;
    }

    public void setListSelectedSubjects(List<Subject> listSelectedSubject) {
        mListSelectedSubjects = listSelectedSubject;
        notifyDataSetChanged();
    }

    public void removeSubjectFromSelectedList(Subject subject) {
        mListSelectedSubjects.remove(subject);
    }

    public void setOnSubjectCheckedChangeListener(SubjectFragment.OnSubjectCheckedChangeListener listener) {
        mOnSubjectCheckedChange = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        return new SubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Subject subject = mListSubject.get(position);
        holder.mTvSubjectName.setText(subject != null ? subject.getName() : "Trống");
        holder.mTvSubjectTeacher.setText(subject.getTeacher() != null ? subject.getTeacher().getName() : "Trống");
        holder.mTvSubjectStartTime.setText(position + ":00");
        holder.mTvSubjectEndTime.setText(position + ":00");
        String standFor = "?";
        if (subject.getName() != null && subject.getName().length() >= 1) {
            standFor = subject.getName().substring(0, 1).toUpperCase();
        }
        holder.mTvSubjectStandFor.setText(standFor);
        holder.mTvSubjectEndTime.setVisibility(mIsViewTimeMode ? View.VISIBLE : View.GONE);
        holder.mTvSubjectStartTime.setVisibility(mIsViewTimeMode ? View.VISIBLE : View.GONE);
        holder.mTvSubjectStandFor.setVisibility(mIsViewTimeMode ? View.GONE : View.VISIBLE);
        holder.mRlSubjectTimePanel.setVisibility(mIsSelectableMode ? View.GONE : View.VISIBLE);
        holder.mCbSelectSubject.setVisibility(mIsSelectableMode ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsSelectableMode) {
                    holder.mCbSelectSubject.setChecked(!holder.mCbSelectSubject.isChecked());
                } else {
                    mView.showAddSubjectDialog(subject);
                }
            }
        });

        holder.mCbSelectSubject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOnSubjectCheckedChange.OnSubjectCheckedChange(isChecked, subject);
                if (isChecked) {
                    addIfNotExist(subject);
                } else {
                    removeIfExist(subject);
                }
            }
        });

        if (mIsSelectableMode) {
            if (getIndexOfSubjectIfExist(subject) != -1) {
                holder.mCbSelectSubject.setChecked(true);
            } else {
                holder.mCbSelectSubject.setChecked(false);
            }
        }
        setAnimation(holder.itemView, position);
        // remove button
        holder.mIbRemoveSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.showRemoveSubjectDialog(mListSubject.get(position));
            }
        });

        holder.itemView.setBackground(
                position % 2 == 0
                        ? mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector1)
                        : mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector0)
        );

        if (mListSelectedSubjectId != null && mListSelectedSubjectId.size() > 0 && mListSelectedSubjectId.contains(subject.getId())) {
            mListSelectedSubjects.add(subject);
            mListSelectedSubjectId.remove(subject.getId());
            holder.mCbSelectSubject.setChecked(true);
        }
    }

    private void addIfNotExist(Subject subject) {
        int index = getIndexOfSubjectIfExist(subject);
        if (index == -1)
            mListSelectedSubjects.add(subject);
    }

    private void removeIfExist(Subject subject) {
        int index = getIndexOfSubjectIfExist(subject);
        if (index != -1)
            mListSelectedSubjects.remove(index);
    }

    private int getIndexOfSubjectIfExist(Subject subject) {
        for (int i = 0; i < mListSelectedSubjects.size(); i++) {
            Subject s = mListSelectedSubjects.get(i);
            if (s.getName().equals(subject.getName())) {
                return i;
            }
        }
        return -1;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > mLastestLoadedPosission) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            mLastestLoadedPosission = position;
        }
    }

    @Override
    public int getItemCount() {
        return mListSubject.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "ViewHolderSubject";
        private TextView mTvSubjectName, mTvSubjectTeacher, mTvSubjectStartTime, mTvSubjectEndTime, mTvSubjectStandFor;
        private CheckBox mCbSelectSubject;
        private RelativeLayout mRlSubjectTimePanel;
        private ImageButton mIbRemoveSubject;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvSubjectName = (TextView) itemView.findViewById(R.id.tv_subject_name);
            mTvSubjectTeacher = (TextView) itemView.findViewById(R.id.tv_subject_teacher);
            mTvSubjectStartTime = (TextView) itemView.findViewById(R.id.tv_subject_start_time);
            mTvSubjectEndTime = (TextView) itemView.findViewById(R.id.tv_subject_end_time);
            mTvSubjectStandFor = (TextView) itemView.findViewById(R.id.tv_subject_stand_for);
            mRlSubjectTimePanel = (RelativeLayout) itemView.findViewById(R.id.rl_friend_name_stand_for_panel);
            mCbSelectSubject = (CheckBox) itemView.findViewById(R.id.cb_select_friend);
            mIbRemoveSubject = (ImageButton) itemView.findViewById(R.id.ib_remove_subject);
        }

    }

}
