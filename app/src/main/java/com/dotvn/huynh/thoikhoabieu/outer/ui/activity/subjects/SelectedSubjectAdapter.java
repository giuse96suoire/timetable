package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.subjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh Hoang Huynh on 05/09/2017.
 */

public class SelectedSubjectAdapter extends RecyclerView.Adapter<SelectedSubjectAdapter.ViewHolder> {
    private List<Subject> mListSelectedSubject;
    private int mLastestLoadedPosission = -1;
    private Context mContext;
    private SubjectsActivityContract.View mView;
    public SelectedSubjectAdapter(Context context, SubjectsActivityContract.View view, List<Subject> listSelectedSubject) {
        this.mListSelectedSubject = listSelectedSubject;
        this.mContext = context;
        this.mView = view;
    }

    public void setListSelectedSubject(List<Subject> listSelectedSubject) {
        mListSelectedSubject.clear();
        mListSelectedSubject.addAll(listSelectedSubject);
        notifyDataSetChanged();
    }

    public void addNewSubjectToEndAndNotify(Subject subject) {
        if (mListSelectedSubject == null) {
            mListSelectedSubject = new ArrayList<>();
        }
        mListSelectedSubject.add(subject);
        notifyDataSetChanged();

    }

    public void addNewSubjectToStartAndNotify(Subject subject) {
        if (mListSelectedSubject == null) {
            mListSelectedSubject = new ArrayList<>();
        }
        mListSelectedSubject.add(0, subject);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chips_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Subject subject = mListSelectedSubject.get(position);
        if (subject != null) {
            holder.mTvSubjectName.setText(subject.getName());
        }
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListSelectedSubject.remove(position);
                notifyDataSetChanged();
                mView.notifySelectedListChange(mListSelectedSubject);
            }
        });
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
        return mListSelectedSubject.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvSubjectName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvSubjectName = (TextView) itemView.findViewById(R.id.tv_selected_subject_name);
        }
    }
}
