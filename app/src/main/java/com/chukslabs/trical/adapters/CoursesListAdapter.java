package com.chukslabs.trical.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.ui.courses.CoursesListView;
import com.chukslabs.trical.util.GradeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by educhuks on 10/25/17.
 */

public class CoursesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Course> mCourses;
    private CoursesListView mView;

    public CoursesListAdapter(CoursesListView view, List<Course> items) {
        this.mCourses = items;
        this.mView = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.element_course, parent, false);
        return new CoursesViewHolder(itemView, mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CoursesViewHolder) holder).setViews(mCourses.get(position));
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.courseCode) public TextView mCourseCode;
        @BindView(R.id.courseName) public TextView mCourseName;
        @BindView(R.id.courseFinalGrade) public TextView mCourseFinalGrade;

        private CoursesListView mListener;

        public CoursesViewHolder(View view, CoursesListView listener) {
            super(view);
            ButterKnife.bind(this, view);
            mListener = listener;
        }

        public void setViews(final Course course) {
            mCourseCode.setText(course.getCode() == null ? " - " : course.getCode());
            mCourseName.setText(course.getName());
            mCourseFinalGrade.setText(GradeUtils.getCourseSubtitle(course));
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.goToDetailScreen(course);
                }
            });
            this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mView.showRemoveDialog(course);
                    return false;
                }
            });
        }

    }

}
