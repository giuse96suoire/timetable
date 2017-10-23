package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.oneDay;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
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
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects.OnSubjectCheckedChangeListener;
import com.dotvn.huynh.thoikhoabieu.outer.ui.support.DragShadowBuilder;
import com.dotvn.huynh.thoikhoabieu.outer.util.DayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh Hoang Huynh on 21/08/2017.
 */
public class ItemOfDayAdapter extends RecyclerView.Adapter<ItemOfDayAdapter.ViewHolder> {
    // list data
    private List<ItemOfDay> mListItemOfDay;
    // mIsSelectableMode: true if in SubjectActivity (can check to select subject)
    private boolean mIsSelectableMode;
    // mListSelectedSubjects: list selected subject in SubjectActivity
    private List<Subject> mListSelectedSubjects;

    // mLastestLoadedPosission: the last index of item that was loaded with animation
    private int mLastestLoadedPosission = -1;

    private Context mContext;
    private OneDayContract.View mView;
    private OnSubjectCheckedChangeListener mOnSubjectCheckedChange;

    public ItemOfDayAdapter(Context context, OneDayContract.View view, List<ItemOfDay> listSubject, boolean isSelectableMode) {
        this.mListItemOfDay = listSubject;
        this.mIsSelectableMode = isSelectableMode;
        if (isSelectableMode) {
            mListSelectedSubjects = new ArrayList<>();
        }
        this.mContext = context;
        this.mView = view;
    }

    public List<Subject> getListSelectedSubjects() {
        return mListSelectedSubjects;
    }

    public void removeSubjectFromSelectedList(Subject subject) {
        mListSelectedSubjects.remove(subject);
    }

    public void setOnSubjectCheckedChangeListener(OnSubjectCheckedChangeListener listener) {
        mOnSubjectCheckedChange = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_day_item, parent, false);
        return new ItemOfDayAdapter.ViewHolder(view, mIsSelectableMode);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemOfDay itemOfDay = mListItemOfDay.get(position);
        final Subject subject = itemOfDay.getSubject();
        holder.mTvSubjectName.setText(subject != null ? subject.getName() : "Trống");
        holder.mTvSubjectTeacher.setText(subject.getTeacher() != null ? subject.getTeacher().getName() : "Trống");
        holder.mTvSubjectStartTime.setText(
                (itemOfDay.getStartTime() == null || itemOfDay.getStartTime().length() == 0)
                        ? "00:00"
                        : itemOfDay.getStartTime());
        holder.mTvSubjectEndTime.setText(
                (itemOfDay.getEndTime() == null || itemOfDay.getEndTime().length() == 0)
                        ? "00:00"
                        : itemOfDay.getEndTime());
        holder.mRlSubjectTimePanel.setVisibility(mIsSelectableMode ? View.GONE : View.VISIBLE);
        holder.mRlSelectPanel.setVisibility(mIsSelectableMode ? View.VISIBLE : View.GONE);
        if (mIsSelectableMode) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.mCbSelectSubject.setChecked(!holder.mCbSelectSubject.isChecked());
                }
            });
            holder.mCbSelectSubject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mOnSubjectCheckedChange.onSubjectCheckedChange(isChecked, subject);
                    if (isChecked) {
                        addIfNotExist(subject);
                    } else {
                        removeIfExist(subject);
                    }
                }
            });
        }

        if (mIsSelectableMode) {
            if (getIndexOfSubjectIfExist(subject) != -1) {
                holder.mCbSelectSubject.setChecked(true);
            } else {
                holder.mCbSelectSubject.setChecked(false);
            }
        }
        setAnimation(holder.itemView, position);
        holder.itemView.setBackground(
                position % 2 == 0
                        ? mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector1)
                        : mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector0)
        );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.showDialogEditItemOfDay(itemOfDay);
            }
        });
        holder.mRlSubjectTimePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.showTimePicker(itemOfDay);
            }
        });

        if (itemOfDay.getEvent() == null || itemOfDay.getEvent().length() == 0) {
            holder.mIbSubjectEvent.setEnabled(false);
        } else {
            holder.mIbSubjectEvent.setEnabled(true);
        }
        holder.mIbSubjectEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.showSnackbar(itemOfDay.getEvent() == null || itemOfDay.getEvent().length() == 0 ?
                        "Không có sự kiện" :
                        itemOfDay.getSubject().getName() + ": " + itemOfDay.getEvent(), null, null);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item(itemOfDay.getStartTime()+"@"+itemOfDay.getId()); // 4:id
                ClipData dragData = new ClipData("Subject data",new String[0],item);
                // Starts the drag
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(dragData,new DragShadowBuilder(v),null,0);
                }

                return true;
            }
        });
        holder.itemView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        mView.setRemoveAreaVisible(true);
                        Log.d("DragDrop Example", "ACTION_DRAG_STARTED");

                        // Determines if this View can accept the dragged data
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            Log.d("DragDrop Example", "hasMimeType");

                            // As an example of what your application might do,
                            // applies a blue color tint to the View to indicate that it can accept
                            // data.
//                            v.setColorFilter(Color.BLUE);
                            v.setBackgroundColor(Color.BLUE);

                            // Invalidate the view to force a redraw in the new tint
                            v.invalidate();

                            // returns true to indicate that the View can accept the dragged data.
                            return true;

                        }

                        // Returns false. During the current drag and drop operation, this View will
                        // not receive events again until ACTION_DRAG_ENDED is sent.
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("DragDrop Example", "ACTION_DRAG_ENTERED");

                        // Applies a green tint to the View. Return true; the return value is ignored.

                        v.setBackgroundColor(Color.LTGRAY);

                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate();

                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d("DragDrop Example", "ACTION_DRAG_LOCATION");

                        // Ignore the event
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("DragDrop Example", "ACTION_DRAG_EXITED");

                        // Re-sets the color tint to blue. Returns true; the return value is ignored.
//                        v.setColorFilter(Color.BLUE);
                        v.setBackground( position % 2 == 0
                                ? mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector1)
                                : mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector0)
                        );

                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate();

                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.d("DragDrop Example", "ACTION_DROP");
                        v.setBackground( position % 2 == 0
                                ? mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector1)
                                : mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector0)
                        );

                        ClipData.Item item = event.getClipData().getItemAt(0);
                        String data = item.getText().toString();
                        String hoursString = data.substring(0, data.indexOf("@"));
                        String id = data.substring(data.indexOf("@") +1, data.length());
                        try {
                            String session1 = DayUtil.getSessionFromTime(DayUtil.getHoursFromTimeString(hoursString));
                            String session2 = DayUtil.getSessionFromTime(DayUtil.getHoursFromTimeString(itemOfDay.getStartTime()));
                            if (id.equals(itemOfDay.getId())) {
                                return true;
                            }
                            if (session1.equals(session2)) {
                                mView.showDialogInvertItem(id, itemOfDay.getId());
                            } else {
                                mView.showToast("Chỉ có thể đổi vị trí môn cùng buổi");
                            }
                        } catch (Exception error) {

                        }

                        // Invalidates the view to force a redraw
                        v.invalidate();

                        // Returns true. DragEvent.getResult() will return true.
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("DragDrop Example", "ACTION_DRAG_ENDED");
                        mView.setRemoveAreaVisible(false);
                        // Turns off any color tinting
//                        v.clearColorFilter();

                        // Invalidates the view to force a redraw
                        v.invalidate();

                        // Does a getResult(), and displays what happened.
                        if (event.getResult()) {
                            Log.d("DragDrop Example", "The drop was handled.");

                        } else {
                            Log.d("DragDrop Example", "The drop didn't work.");
                        }

                        // returns true; the value is ignored.
                        return true;

                    // An unknown action type was received.
                    default:
                        Log.d("DragDrop Example","Unknown action type received by OnDragListener.");
                        break;
                }
                return false;
            }
        });
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
        return mListItemOfDay.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvSubjectName, mTvSubjectTeacher, mTvSubjectStartTime, mTvSubjectEndTime, mTvSubjectStandFor;
        private CheckBox mCbSelectSubject;
        private RelativeLayout mRlSubjectTimePanel;
        private ImageButton mIbSubjectEvent;
        private RelativeLayout mRlSelectPanel;
        public CheckBox getCbSelectSubject() {
            return mCbSelectSubject;
        }

        public ViewHolder(View itemView, boolean isSelectableMode) {
            super(itemView);
            mTvSubjectName = (TextView) itemView.findViewById(R.id.tv_subject_name);
            mTvSubjectTeacher = (TextView) itemView.findViewById(R.id.tv_subject_teacher);
            mTvSubjectStartTime = (TextView) itemView.findViewById(R.id.tv_subject_start_time);
            mTvSubjectEndTime = (TextView) itemView.findViewById(R.id.tv_subject_end_time);
            mCbSelectSubject = (CheckBox) itemView.findViewById(R.id.cb_select_friend);
            mIbSubjectEvent = (ImageButton) itemView.findViewById(R.id.ib_subject_event);
            mRlSubjectTimePanel = (RelativeLayout) itemView.findViewById(R.id.rl_subject_start_panel);
            mRlSelectPanel = (RelativeLayout) itemView.findViewById(R.id.rl_select_panel);
        }

    }

}
